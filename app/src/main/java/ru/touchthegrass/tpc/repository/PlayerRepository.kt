package ru.touchthegrass.tpc.repository

import kotlinx.coroutines.flow.Flow
import ru.touchthegrass.tpc.data.Player

interface PlayerRepository {

    fun getCurrentPlayer(): Flow<Player>

    fun getAllPlayers(): Flow<List<Player>>

    fun getPlayerByUid(id: Int): Flow<Player>
}