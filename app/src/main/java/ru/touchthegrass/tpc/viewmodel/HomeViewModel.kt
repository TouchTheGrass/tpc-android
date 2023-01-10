package ru.touchthegrass.tpc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.touchthegrass.tpc.data.*
import ru.touchthegrass.tpc.manager.LobbyManager
import ru.touchthegrass.tpc.manager.local.LocalLobbyManager
import ru.touchthegrass.tpc.repository.LobbyRepository
import ru.touchthegrass.tpc.repository.PlayerRepository
import ru.touchthegrass.tpc.repository.local.LocalLobbyRepository
import ru.touchthegrass.tpc.repository.local.LocalPlayerRepository

class TpcHomeViewModel(
    private val lobbyRepository: LobbyRepository = LocalLobbyRepository(),
    private val playerRepository: PlayerRepository = LocalPlayerRepository(),
    private val lobbyManager: LobbyManager = LocalLobbyManager(),
) : ViewModel() {

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

            playerRepository.getCurrentPlayer()
                .catch { ex ->
                    _uiState.value = TpcHomeUIState(
                        error = ex.message
                    )
                }.collect { currentPlayer ->
                    _playerState.value = _playerState.value.copy(
                        currentPlayer = currentPlayer
                    )
                }

            lobbyRepository.getAllLobbies()
                .catch { ex ->
                    _uiState.value = TpcHomeUIState(
                        error = ex.message
                    )
                }.collect { lobbies ->
                    _lobbyState.value = _lobbyState.value.copy(
                        lobbies = lobbies
                    )
                }
        }
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
}

data class TpcHomeUIState(
    val openedFilter: Boolean = false,
    val openedLobby: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)

data class TpcPlayerState(
    val currentPlayer: Player? = null,
)

data class TpcFilterState(
    val playerFilter: String = "",
)

data class TpcLobbyState(
    val gameSession: GameSession? = null,
    val playerGameSessionInfos: List<PlayerGameSession> = emptyList(),
    val lobbies: List<Lobby> = emptyList()
)
