package ru.touchthegrass.tpc.repository.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.touchthegrass.tpc.data.Player
import ru.touchthegrass.tpc.data.local.LocalPlayerProvider
import ru.touchthegrass.tpc.repository.PlayerRepository

class LocalPlayerRepository: PlayerRepository {

    override fun getCurrentPlayer(): Flow<Player> = flow {
        emit(LocalPlayerProvider.currentPlayer)
    }

    override fun getAllPlayers(): Flow<List<Player>> = flow {
        emit(LocalPlayerProvider.allPlayers)
    }

    override fun getPlayerByUid(id: Int): Flow<Player> = flow {
        emit(LocalPlayerProvider.getPlayerById(id))
    }

}