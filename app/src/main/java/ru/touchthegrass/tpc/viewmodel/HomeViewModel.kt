package ru.touchthegrass.tpc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.touchthegrass.tpc.data.Lobby
import ru.touchthegrass.tpc.repository.LobbyRepository
import ru.touchthegrass.tpc.repository.local.LocalLobbyRepository

class TpcHomeViewModel(
    private val lobbyRepository: LobbyRepository = LocalLobbyRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(TpcHomeUIState(loading = true))
    val uiState: StateFlow<TpcHomeUIState> = _uiState

    private val _filterState = MutableStateFlow(TpcFilterState())
    val filterState: StateFlow<TpcFilterState> = _filterState

    init {
        viewModelScope.launch {
            lobbyRepository.getAllLobbies()
                .catch { ex ->
                    _uiState.value = TpcHomeUIState(
                        error = ex.message
                    )
                }
                .collect { lobbies ->
                    _uiState.value = TpcHomeUIState(
                        lobbies = lobbies
                    )
                }
        }
    }

    fun openFilterScreen() {
        _uiState.value = _uiState.value.copy(
            openedFilter = true
        )
    }

    fun closeFilterScreen() {
        _uiState.value = _uiState.value.copy(
            openedFilter = false
        )
    }

    fun changeSearchPlayerFilter(newValue: String) {
        _filterState.value = _filterState.value.copy(
            playerFilter = newValue
        )
    }
}

data class TpcHomeUIState(
    val lobbies: List<Lobby> = emptyList(),
    val openedFilter: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)

data class TpcFilterState(
    val playerFilter: String = "",
    val error: String? = null
)
