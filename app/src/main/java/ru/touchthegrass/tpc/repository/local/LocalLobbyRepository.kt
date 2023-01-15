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
import java.lang.Integer.max
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
            val group = LocalPlayerProvider.getRandomGroup(min = 3, max = 3)
            val winner = group.random()
            Lobby(
                gameSession = gameSession,
                playerInfos = group
                    .mapIndexed { index, player ->
                        val isWinner = player.id == winner.id
                        val scores = if (isWinner) Random.nextInt(10, 200)
                        else Random.nextInt(-200, -10)
                        player.rating = max(0, player.rating + scores)
                        PlayerGameSession(
                            player = player,
                            gameSession = gameSession,
                            status = PlayerStatus.DISCONNECTED,
                            color = PieceColor.values()[index],
                            isWinner = isWinner,
                            scores = scores
                        )
                    }
                    .toMutableList()
            )
        }
        .toMutableList()

    override fun getActiveLobbies(): Flow<List<Lobby>> = flow {
        emit(activeLobbies)
    }

    override fun getPlayerHistory(playerId: Int): Flow<List<Lobby>> = flow {
        emit(
            completedLobbies.filter { lobby ->
                val playerInfo = lobby.playerInfos
                    .find { it.player.id == playerId }
                    ?: return@filter false
                playerInfo.status == PlayerStatus.DONE || playerInfo.status == PlayerStatus.DISCONNECTED
            }
        )
    }

    override fun getLobbyByGameSessionId(gameSessionId: Int): Lobby {
        return (activeLobbies + completedLobbies).first { it.gameSession.id == gameSessionId }
    }

    override fun createLobby(lobby: Lobby): Lobby {
        activeLobbies.add(lobby)
        return lobby
    }
}