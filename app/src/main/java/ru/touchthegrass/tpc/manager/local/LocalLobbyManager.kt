package ru.touchthegrass.tpc.manager.local

import kotlinx.coroutines.CoroutineScope
import ru.touchthegrass.tpc.data.Lobby
import ru.touchthegrass.tpc.data.PieceColor
import ru.touchthegrass.tpc.data.PlayerGameSession
import ru.touchthegrass.tpc.data.PlayerStatus
import ru.touchthegrass.tpc.data.local.LocalGameSessionProvider
import ru.touchthegrass.tpc.data.local.LocalPlayerProvider
import ru.touchthegrass.tpc.manager.LobbyManager
import ru.touchthegrass.tpc.repository.LobbyRepository

class LocalLobbyManager(
    val coroutineScope: CoroutineScope,
    override val lobbyRepository: LobbyRepository
) : LobbyManager {

    override fun createLobby(): Lobby {
        return lobbyRepository.createLobby(Lobby(gameSession = LocalGameSessionProvider.createGameSession()))
    }

    override fun connectPlayer(gameSessionId: Int, playerId: Int): Lobby {
        val lobby = lobbyRepository.getLobbyByGameSessionId(gameSessionId)
        val player = LocalPlayerProvider.getPlayerById(playerId)
        lobby.playerInfos.add(
            PlayerGameSession(
                gameSession = lobby.gameSession,
                player = player
            )
        )
        return lobby
    }

    override fun disconnectPlayer(gameSessionId: Int, playerId: Int) {
        val lobby = lobbyRepository.getLobbyByGameSessionId(gameSessionId)
        lobby.playerInfos.removeIf { it.player.id == playerId }
    }

    override fun setPlayerColor(gameSessionId: Int, playerId: Int, pieceColor: PieceColor): List<PlayerGameSession> {
        val lobby = lobbyRepository.getLobbyByGameSessionId(gameSessionId)
        val updatedInfos = lobby.playerInfos
            .map { if (it.player.id == playerId) it.copy(color = pieceColor) else it }
            .toMutableList()
        lobby.playerInfos = updatedInfos
        return updatedInfos
    }

    override fun setPlayerStatus(
        gameSessionId: Int,
        playerId: Int,
        status: PlayerStatus
    ): List<PlayerGameSession> {
        val lobby = lobbyRepository.getLobbyByGameSessionId(gameSessionId)
        val updatedInfos = lobby.playerInfos
            .map { if (it.player.id == playerId) it.copy(status = status) else it }
            .toMutableList()
        lobby.playerInfos = updatedInfos
        return updatedInfos
    }
}