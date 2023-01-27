package ru.touchthegrass.tpc.state

import ru.touchthegrass.tpc.data.Lobby
import ru.touchthegrass.tpc.data.Player

data class TpcUserState(
    val currentPlayer: Player? = null,
    val currentPlayerHistory: List<Lobby> = emptyList()
)
