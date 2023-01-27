package ru.touchthegrass.tpc.repository

import kotlinx.coroutines.flow.Flow
import ru.touchthegrass.tpc.data.Lobby

interface LobbyRepository {

    fun getActiveLobbies(): Flow<List<Lobby>>

    fun getPlayerHistory(playerId: Int): List<Lobby>

    fun getLobbyByGameSessionId(gameSessionId: Int): Lobby

    fun createLobby(lobby: Lobby): Lobby
}
