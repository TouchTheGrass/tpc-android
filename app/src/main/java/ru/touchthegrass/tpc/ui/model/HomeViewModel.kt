package ru.touchthegrass.tpc.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.touchthegrass.tpc.data.*
import ru.touchthegrass.tpc.manager.LobbyManager
import ru.touchthegrass.tpc.manager.GameManager
import ru.touchthegrass.tpc.manager.UserManager
import ru.touchthegrass.tpc.manager.local.LocalLobbyManager
import ru.touchthegrass.tpc.manager.local.LocalGameManager
import ru.touchthegrass.tpc.manager.local.LocalUserManager
import ru.touchthegrass.tpc.repository.LobbyRepository
import ru.touchthegrass.tpc.repository.MoveRepository
import ru.touchthegrass.tpc.repository.PieceRepository
import ru.touchthegrass.tpc.repository.PlayerRepository
import ru.touchthegrass.tpc.repository.local.LocalLobbyRepository
import ru.touchthegrass.tpc.repository.local.LocalMoveRepository
import ru.touchthegrass.tpc.repository.local.LocalPieceRepository
import ru.touchthegrass.tpc.repository.local.LocalPlayerRepository
import ru.touchthegrass.tpc.state.*
import ru.touchthegrass.tpc.ui.drawable.DrawablePiece
import ru.touchthegrass.tpc.ui.drawable.DrawablePosition
import ru.touchthegrass.tpc.ui.drawable.PieceHighlightType
import ru.touchthegrass.tpc.ui.drawable.paths.*
import kotlin.math.PI

class TpcHomeViewModel(
    private val lobbyRepository: LobbyRepository = LocalLobbyRepository(),
    private val playerRepository: PlayerRepository = LocalPlayerRepository(),
    private val pieceRepository: PieceRepository = LocalPieceRepository(),
    private val moveRepository: MoveRepository = LocalMoveRepository()
) : ViewModel() {

    private val userManager: UserManager = LocalUserManager(playerRepository)
    private val gameManager: GameManager = LocalGameManager(pieceRepository as LocalPieceRepository)
    private val lobbyManager: LobbyManager = LocalLobbyManager(lobbyRepository, pieceRepository as LocalPieceRepository)

    private val _uiState = MutableStateFlow(TpcUIState())
    val uiState: StateFlow<TpcUIState> = _uiState

    private val _dataState = MutableStateFlow(TpcDataState())
    val dataState: StateFlow<TpcDataState> = _dataState

    private val _playerState = MutableStateFlow(TpcUserState())
    val playerState: StateFlow<TpcUserState> = _playerState

    private val _lobbyState = MutableStateFlow(TpcLobbyState())
    val lobbyState: StateFlow<TpcLobbyState> = _lobbyState

    private val _gameState = MutableStateFlow(TpcGameState())
    val gameState: StateFlow<TpcGameState> = _gameState

    init {
        viewModelScope.launch {
            lobbyRepository.getActiveLobbies()
                .collect { lobbies ->
                    _dataState.value = _dataState.value.copy(
                        lobbies = lobbies
                    )
                }
        }
        viewModelScope.launch {
            pieceRepository.getAllPieces()
                .collect { pieces ->
                    _gameState.value = _gameState.value.copy(
                        pieces = prepareDrawablePieces(pieces)
                    )
                }
        }
        viewModelScope.launch {
            moveRepository.getPossibleMoves()
                .collect { moves ->
                    _gameState.value = _gameState.value.copy(
                        positions = prepareDrawablePositions(moves)
                    )
                }
        }
    }

    fun loginUser(email: String, password: String) {
        val authenticatedPlayer = userManager.loginUser(email, password)
        if (authenticatedPlayer == null) {
            _uiState.value = _uiState.value.copy(
                error = "User not found"
            )
        } else {
            _playerState.value = _playerState.value.copy(
                currentPlayer = authenticatedPlayer
            )
            updateRatingInfo()
            updateUserInfo()
        }
    }

    fun updateRatingInfo() {
        _dataState.value = _dataState.value.copy(
            players = playerRepository.getAllPlayers()
        )
    }

    fun updateUserInfo() {
        _playerState.value.currentPlayer
            ?.id
            ?.let { userId ->
                _playerState.value = _playerState.value.copy(
                    currentPlayerHistory = lobbyRepository.getPlayerHistory(userId)
                )
            }
    }

    fun registerUser(email: String, name: String, password: String) {
        val registeredPlayer = userManager.registerUser(email, name, password)
        loginUser(registeredPlayer.email, registeredPlayer.name)
        _uiState.value = _uiState.value.copy(
            openedRegister = false
        )
    }

    fun logoutUser() {
        _playerState.value = _playerState.value.copy(
            currentPlayer = null,
            currentPlayerHistory = emptyList()
        )
        _playerState.value = _playerState.value.copy(
            currentPlayerHistory = emptyList()
        )
    }

    fun openLoginScreen() {
        _uiState.value = _uiState.value.copy(
            openedRegister = false
        )
    }

    fun openRegisterScreen() {
        _uiState.value = _uiState.value.copy(
            openedRegister = true
        )
    }

    fun closeFilterScreen() {
        _uiState.value = _uiState.value.copy(
            openedFilter = false
        )
    }

    fun openFilterScreen() {
        _uiState.value = _uiState.value.copy(
            openedFilter = true
        )
    }

    fun closeLobbyScreen() {
        lobbyManager.disconnectPlayer(
            gameSessionId = lobbyState.value.gameSession!!.id,
            playerId = playerState.value.currentPlayer!!.id
        )
        _uiState.value = _uiState.value.copy(
            openedLobby = false
        )
    }

    fun connectLobby(gameSessionId: Int) {
        val lobby = lobbyManager.connectPlayer(
            gameSessionId = gameSessionId,
            playerId = playerState.value.currentPlayer!!.id
        )
        _uiState.value = _uiState.value.copy(
            openedLobby = true
        )
        _lobbyState.value = _lobbyState.value.copy(
            gameSession = lobby.gameSession,
            playerGameSessionInfos = lobby.playerInfos
        )
    }

    fun createLobby() {
        val lobby = lobbyManager.connectPlayer(
            gameSessionId = lobbyManager.createLobby().gameSession.id,
            playerId = playerState.value.currentPlayer!!.id
        )
        _uiState.value = _uiState.value.copy(
            openedLobby = true
        )
        _lobbyState.value = _lobbyState.value.copy(
            gameSession = lobby.gameSession,
            playerGameSessionInfos = lobby.playerInfos
        )
    }

    fun changeSearchPlayerFilter(newValue: String) {
        _dataState.value = _dataState.value.copy(
            playerFilter = newValue
        )
    }

    fun changePieceColor(pieceColor: PieceColor) {
        val updatedInfos = lobbyManager.setPlayerColor(
            gameSessionId = lobbyState.value.gameSession!!.id,
            playerId = playerState.value.currentPlayer!!.id,
            pieceColor = pieceColor
        )
        _lobbyState.value = _lobbyState.value.copy(
            playerGameSessionInfos = updatedInfos
        )
    }

    fun changeReadiness(isReady: Boolean) {
        val updatedLobby = lobbyManager.setPlayerStatus(
            gameSessionId = lobbyState.value.gameSession!!.id,
            playerId = playerState.value.currentPlayer!!.id,
            status = if (isReady) PlayerStatus.READY else PlayerStatus.NOT_READY
        )
        _lobbyState.value = _lobbyState.value.copy(
            playerGameSessionInfos = updatedLobby.playerInfos,
            gameSession = updatedLobby.gameSession,
        )
    }

    fun selectPiece(pieceId: Int?) {
        _gameState.value = _gameState.value.copy(
            selectedPieceId = pieceId,
            selectedPosition = null
        )
    }

    fun selectPosition(position: String?) {
        _gameState.value = _gameState.value.copy(
            selectedPosition = position
        )
    }

    fun movePiece(pieceId: Int, position: String) {
        gameManager.movePiece(pieceId, position)
        _gameState.value = _gameState.value.copy(
            selectedPieceId = null,
            selectedPosition = null
        )
    }

    fun considerGame() {
        val gameSession = _lobbyState.value.gameSession ?: return
        lobbyManager.considerGame(gameSession.id)

        _uiState.value = _uiState.value.copy(
            openedLobby = false
        )
        _lobbyState.value = _lobbyState.value.copy(
            gameSession = null,
            playerGameSessionInfos = emptyList()
        )
    }

    private fun prepareDrawablePieces(pieces: List<Piece>): List<DrawablePiece> {
        val drawablePieces = mutableListOf<DrawablePiece>()
        for (piece in pieces) {
            val i = coordinates.indexOfFirst { it.contains(piece.position) }
            val j = coordinates[i].indexOf(piece.position)
            val cellCenter = cellCenters[j]

            drawablePieces.add(
                DrawablePiece(
                    xOffset = BASE_CELL_SCALE_X * cellCenter.first,
                    yOffset = BASE_CELL_SCALE_Y * cellCenter.second,
                    angle = i * PI.toFloat() / 3,
                    info = piece,
                    path = getPiecePath(piece.type),
                    highlightType = when {
                        piece.id == _gameState.value.selectedPieceId -> PieceHighlightType.SELECTED
                        _gameState.value.positions.any { it.str == piece.position } -> {
                            if (piece.position == _gameState.value.selectedPosition) PieceHighlightType.SELECTED_TO_ATTACK
                            else PieceHighlightType.UNDER_ATTACK
                        }
                        else -> PieceHighlightType.COMMON
                    }
                )
            )
        }

        return drawablePieces
    }

    private fun prepareDrawablePositions(moves: List<Move>): List<DrawablePosition> {
        val drawablePositions = mutableListOf<DrawablePosition>()
        val positions = moves.find { it.piece.id == _gameState.value.selectedPieceId }
            ?.possibleMoves
            ?.filter { position ->
                _gameState.value.pieces.all { position != it.info.position }
            }
            ?: return emptyList()

        for (position in positions) {
            val i = coordinates.indexOfFirst { it.contains(position) }
            val j = coordinates[i].indexOf(position)
            val cellCenter = cellCenters[j]

            drawablePositions.add(
                DrawablePosition(
                    xOffset = BASE_CELL_SCALE_X * cellCenter.first,
                    yOffset = BASE_CELL_SCALE_Y * cellCenter.second,
                    angle = i * PI.toFloat() / 3,
                    str = position,
                    isSelected = position == _gameState.value.selectedPosition
                )
            )
        }

        return drawablePositions
    }
}
