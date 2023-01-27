package ru.touchthegrass.tpc.state

import ru.touchthegrass.tpc.data.Lobby
import ru.touchthegrass.tpc.data.Player

data class TpcDataState(
    val playerFilter: String = "",
    val lobbies: List<Lobby> = emptyList(),
    val players: List<Player> = emptyList(),
)
