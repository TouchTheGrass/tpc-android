package ru.touchthegrass.tpc.repository.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.touchthegrass.tpc.data.Lobby
import ru.touchthegrass.tpc.data.PieceColor
import ru.touchthegrass.tpc.data.PlayerGameSession
import ru.touchthegrass.tpc.data.PlayerStatus
import ru.touchthegrass.tpc.data.local.LocalGameSessionProvider
import ru.touchthegrass.tpc.data.local.LocalPlayerProvider
import ru.touchthegrass.tpc.repository.LobbyRepository
import kotlin.random.Random

class LocalLobbyRepository : LobbyRepository {

    val activeLobbies = LocalGameSessionProvider.activeGameSessions
        .map { gameSession ->
            val group = LocalPlayerProvider.getRandomGroup(
                min = 1,
                max = 2,
                excluding = true
            )
            Lobby(
                gameSession = gameSession,
                playerInfos = group
                    .map { player ->
                        PlayerGameSession(
                            player = player,
                            gameSession = gameSession,
                            status = PlayerStatus.NOT_READY
                        )
                    }
                    .toMutableList()
            )
        }
        .toMutableList()

    val completedLobbies = LocalGameSessionProvider.completedGameSessions
        .map { gameSession ->
            println(gameSession.id)
            val group = LocalPlayerProvider.getRandomGroup(min = 3, max = 3)
            println(group)
            val winner = group.random()
            Lobby(
                gameSession = gameSession,
                playerInfos = group
                    .mapIndexed { index, player ->
                        val isWinner = player.id == winner.id
                        PlayerGameSession(
                            player = player,
                            gameSession = gameSession,
                            status = PlayerStatus.DISCONNECTED,
                            color = PieceColor.values()[index],
                            isWinner = isWinner,
                            scores = if (isWinner) Random.nextInt(10, 200) else Random.nextInt(-200, -10)
                        )
                    }
                    .toMutableList()
            )
        }
        .toMutableList()

    override fun getActiveLobbies(): Flow<List<Lobby>> = flow {
        emit(activeLobbies)
    }

    override fun getCurrentPlayerHistory(): Flow<List<Lobby>> = flow {
        val currentPlayer = LocalPlayerProvider.currentPlayer
        emit(
            completedLobbies.filter { lobby ->
                val playerInfo = lobby.playerInfos
                    .find { it.player.id == currentPlayer.id }
                    ?: return@filter false
                playerInfo.status == PlayerStatus.DONE || playerInfo.status == PlayerStatus.DISCONNECTED
            }
        )
    }

    override fun getLobbyByGameSessionId(id: Int): Lobby {
        return (activeLobbies + completedLobbies).first { it.gameSession.id == id }
    }

    override fun createLobby(lobby: Lobby): Lobby {
        activeLobbies.add(lobby)
        return lobby
    }
}