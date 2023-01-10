package ru.touchthegrass.tpc.manager

import ru.touchthegrass.tpc.data.Lobby
import ru.touchthegrass.tpc.data.PieceColor
import ru.touchthegrass.tpc.data.PlayerGameSession
import ru.touchthegrass.tpc.data.PlayerStatus

interface LobbyManager {

    fun createLobby(): Lobby

    fun connectPlayer(gameSessionId: Int, playerId: Int): Lobby

    fun disconnectPlayer(gameSessionId: Int, playerId: Int)

    fun setPlayerColor(gameSessionId: Int, playerId: Int, pieceColor: PieceColor): List<PlayerGameSession>

    fun setPlayerStatus(gameSessionId: Int, playerId: Int, status: PlayerStatus): List<PlayerGameSession>
}