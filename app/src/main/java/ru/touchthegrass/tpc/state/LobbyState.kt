package ru.touchthegrass.tpc.state

import ru.touchthegrass.tpc.data.GameSession
import ru.touchthegrass.tpc.data.PlayerGameSession

data class TpcLobbyState(
    val gameSession: GameSession? = null,
    val playerGameSessionInfos: List<PlayerGameSession> = emptyList()
)
