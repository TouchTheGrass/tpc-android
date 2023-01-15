package ru.touchthegrass.tpc.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.touchthegrass.tpc.data.*
import ru.touchthegrass.tpc.manager.LobbyManager
import ru.touchthegrass.tpc.manager.UserManager
import ru.touchthegrass.tpc.manager.local.LocalLobbyManager
import ru.touchthegrass.tpc.manager.local.LocalUserManager
import ru.touchthegrass.tpc.repository.LobbyRepository
import ru.touchthegrass.tpc.repository.PlayerRepository
import ru.touchthegrass.tpc.repository.local.LocalLobbyRepository
import ru.touchthegrass.tpc.repository.local.LocalPlayerRepository

class TpcHomeViewModel(
    private val lobbyRepository: LobbyRepository = LocalLobbyRepository(),
    private val playerRepository: PlayerRepository = LocalPlayerRepository()
) : ViewModel() {

    private val lobbyManager: LobbyManager = LocalLobbyManager(lobbyRepository)
    private val userManager: UserManager = LocalUserManager(playerRepository)

    private val _uiState = MutableStateFlow(TpcHomeUIState(loading = true))
    val uiState: StateFlow<TpcHomeUIState> = _uiState

    private val _playerState = MutableStateFlow(TpcPlayerState())
    val playerState: StateFlow<TpcPlayerState> = _playerState

    private val _filterState = MutableStateFlow(TpcFilterState())
    val filterState: StateFlow<TpcFilterState> = _filterState

    private val _lobbyState = MutableStateFlow(TpcLobbyState())
    val lobbyState: StateFlow<TpcLobbyState> = _lobbyState

    init {
        viewModelScope.launch {

            playerRepository.getAllPlayers()
                .catch { ex ->
                    _uiState.value = TpcHomeUIState(
                        error = ex.message
                    )
                }
                .collect { players ->
                    _playerState.value = _playerState.value.copy(
                        players = players
                    )
                }

            lobbyRepository.getActiveLobbies()
                .catch { ex ->
                    _uiState.value = TpcHomeUIState(
                        error = ex.message
                    )
                }
                .collect { lobbies ->
                    _lobbyState.value = _lobbyState.value.copy(
                        lobbies = lobbies
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

            viewModelScope.launch {
                lobbyRepository.getPlayerHistory(_playerState.value.currentPlayer!!.id)
                    .catch { ex ->
                        _uiState.value = TpcHomeUIState(
                            error = ex.message
                        )
                    }
                    .takeWhile {
                        _playerState.value.currentPlayer != null
                    }
                    .collect { currentPlayerInfos ->
                        _playerState.value = _playerState.value.copy(
                            currentPlayerHistory = currentPlayerInfos
                        )
                    }
            }
        }
    }

    fun registerUser(email: String, name: String, password: String) {
        val registeredPlayer = userManager.registerUser(email, name, password)

        _playerState.value = _playerState.value.copy(
            currentPlayer = registeredPlayer
        )

        _uiState.value = _uiState.value.copy(
            openedRegister = false
        )

        viewModelScope.launch {
            lobbyRepository.getPlayerHistory(_playerState.value.currentPlayer!!.id)
                .catch { ex ->
                    _uiState.value = TpcHomeUIState(
                        error = ex.message
                    )
                }
                .takeWhile {
                    _playerState.value.currentPlayer != null
                }
                .collect { currentPlayerInfos ->
                    _playerState.value = _playerState.value.copy(
                        currentPlayerHistory = currentPlayerInfos
                    )
                }
        }
    }

    fun logoutUser() {
        _playerState.value = _playerState.value.copy(
            currentPlayer = null
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

    fun setCurrentLobby(gameSessionId: Int) {
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
        _filterState.value = _filterState.value.copy(
            playerFilter = newValue
        )
    }

    fun setPieceColor(pieceColor: PieceColor) {
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
        val updatedInfos = lobbyManager.setPlayerStatus(
            gameSessionId = lobbyState.value.gameSession!!.id,
            playerId = playerState.value.currentPlayer!!.id,
            status = if (isReady) PlayerStatus.READY else PlayerStatus.NOT_READY
        )
        _lobbyState.value = _lobbyState.value.copy(
            playerGameSessionInfos = updatedInfos
        )
    }

    fun movePiece() {
        // TODO
    }
}

data class TpcHomeUIState(
    val openedRegister: Boolean = false,
    val openedFilter: Boolean = false,
    val openedLobby: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)

data class TpcPlayerState(
    val players: List<Player> = emptyList(),
    val currentPlayer: Player? = null,
    val currentPlayerHistory: List<Lobby> = emptyList()
)

data class TpcFilterState(
    val playerFilter: String = "",
)

data class TpcLobbyState(
    val lobbies: List<Lobby> = emptyList(),
    val gameSession: GameSession? = null,
    val playerGameSessionInfos: List<PlayerGameSession> = emptyList(),
    val cells: List<Cell> = emptyList(),
    val pieces: List<Piece> = emptyList(),
    val positions: List<String> = emptyList(),
    val selectedPiece: Piece? = null,
    val selectedPosition: String? = null
)
