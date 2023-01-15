package ru.touchthegrass.tpc.repository

import kotlinx.coroutines.flow.Flow
import ru.touchthegrass.tpc.data.Lobby

interface LobbyRepository {

    fun getActiveLobbies(): Flow<List<Lobby>>

    fun getCurrentPlayerHistory(): Flow<List<Lobby>>

    fun getLobbyByGameSessionId(id: Int): Lobby

    fun createLobby(lobby: Lobby): Lobby
}