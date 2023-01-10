package ru.touchthegrass.tpc.data.local

import ru.touchthegrass.tpc.data.GameSession
import ru.touchthegrass.tpc.data.GameSessionStatus

object LocalGameSessionProvider {

    val allGameSessions = (1..10).map { id ->
        GameSession(
            id = id,
            status = GameSessionStatus.WAIT,
            rules = LocalGameRulesProvider.standartRules
        )
    }.toMutableList()

    fun createGameSession(): GameSession {
        val gameSession = GameSession(
            id = allGameSessions.maxOf { it.id } + 1,
            status = GameSessionStatus.WAIT,
            rules = LocalGameRulesProvider.standartRules
        )
        allGameSessions.add(gameSession)
        return gameSession
    }
}
