package ru.touchthegrass.tpc.data.local

import ru.touchthegrass.tpc.data.GameSession
import ru.touchthegrass.tpc.data.GameSessionStatus

object LocalGameSessionProvider: LocalProvider() {

    val activeGameSessions = (1..10).map {
        GameSession(
            id = getId(),
            status = GameSessionStatus.WAIT,
            rules = LocalGameRulesProvider.standartRules
        )
    }.toMutableList()

    val completedGameSessions = (1..100).map {
        GameSession(
            id = getId(),
            status = GameSessionStatus.COMPLETED,
            rules = LocalGameRulesProvider.standartRules
        )
    }.toMutableList()

    fun createGameSession(): GameSession {
        val gameSession = GameSession(
            id = getId(),
            status = GameSessionStatus.WAIT,
            rules = LocalGameRulesProvider.standartRules
        )
        activeGameSessions.add(gameSession)
        return gameSession
    }
}
