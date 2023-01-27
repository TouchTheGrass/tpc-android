package ru.touchthegrass.tpc.manager.local

import ru.touchthegrass.tpc.data.*
import ru.touchthegrass.tpc.data.local.LocalGameSessionProvider
import ru.touchthegrass.tpc.data.local.LocalPlayerProvider
import ru.touchthegrass.tpc.manager.LobbyManager
import ru.touchthegrass.tpc.repository.LobbyRepository
import ru.touchthegrass.tpc.repository.local.LocalPieceRepository

class LocalLobbyManager(
    override val lobbyRepository: LobbyRepository,
    override val pieceRepository: LocalPieceRepository
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
    ): Lobby {
        val lobby = lobbyRepository.getLobbyByGameSessionId(gameSessionId)
        val updatedInfos = lobby.playerInfos
            .map { if (it.player.id == playerId) it.copy(status = status) else it }
            .toMutableList()
        lobby.playerInfos = updatedInfos
        updateLobbyStatus(lobby)
        return lobby
    }

    override fun considerGame(gameSessionId: Int) {
        val lobby = lobbyRepository.getLobbyByGameSessionId(gameSessionId)
        lobby.gameSession.status = GameSessionStatus.COMPLETED
        lobby.playerInfos.forEach { it.status = PlayerStatus.DONE }
    }

    private fun updateLobbyStatus(lobby: Lobby) {
        if (lobby.playerInfos.all { it.status == PlayerStatus.READY }) {
            val takenColors = lobby.playerInfos.mapNotNull { it.color }.toMutableList()
            lobby.playerInfos.forEach {playerInfo ->
                if (playerInfo.color == null) {
                    val color = PieceColor.values().first { !takenColors.contains(it) }
                    takenColors.add(color)
                    playerInfo.color = color
                }
            }
            lobby.gameSession.status = GameSessionStatus.GAME
            pieceRepository.initPieces(lobby.gameSession)
            lobby.playerInfos
                .find { it.color == PieceColor.WHITE }
                ?.status = PlayerStatus.CURRENT_TURN
        }
    }
}
