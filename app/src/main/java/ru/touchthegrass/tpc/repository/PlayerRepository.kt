package ru.touchthegrass.tpc.repository

import kotlinx.coroutines.flow.Flow
import ru.touchthegrass.tpc.data.Player

interface PlayerRepository {

    fun getAllPlayers(): Flow<List<Player>>

    fun getPlayerById(id: Int): Player

    fun getPlayerByCredential(email: String, password: String): Player?

    fun createPlayer(email: String, name: String, password: String): Player
}