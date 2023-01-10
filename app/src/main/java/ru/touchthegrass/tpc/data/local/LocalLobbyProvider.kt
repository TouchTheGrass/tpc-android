package ru.touchthegrass.tpc.data.local

import ru.touchthegrass.tpc.data.Lobby
import ru.touchthegrass.tpc.data.PlayerGameSession
import ru.touchthegrass.tpc.data.PlayerStatus

object LocalLobbyProvider {

    val allLobbies = LocalGameSessionProvider.allGameSessions
        .map { gameSession ->
            Lobby(
                gameSession = gameSession,
                playerInfos = LocalPlayerProvider.getRandomGroup()
                    .map { player ->
                        PlayerGameSession(
                            player = player,
                            gameSession = gameSession,
                            isActive = true,
                            status = PlayerStatus.NOT_READY
                        )
                    }
                    .toMutableList()
            )
        }
        .toMutableList()

    fun getLobbyByGameSessionId(gameSessionId: Int): Lobby {
        return allLobbies.first { it.gameSession.id == gameSessionId }
    }

    fun createLobby(): Lobby {
        val lobby = Lobby(gameSession = LocalGameSessionProvider.createGameSession())
        allLobbies.add(lobby)
        return lobby
    }
}
