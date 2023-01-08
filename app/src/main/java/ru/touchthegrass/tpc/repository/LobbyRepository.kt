package ru.touchthegrass.tpc.repository

import kotlinx.coroutines.flow.Flow
import ru.touchthegrass.tpc.data.Lobby

interface LobbyRepository {

    fun getAllLobbies(): Flow<List<Lobby>>

    fun getLobbyById(id: Long): Flow<Lobby>
}