package ru.touchthegrass.tpc.state

data class TpcUIState(
    val openedRegister: Boolean = false,
    val openedFilter: Boolean = false,
    val openedLobby: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)
