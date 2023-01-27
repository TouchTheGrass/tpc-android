package ru.touchthegrass.tpc.repository.local

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.touchthegrass.tpc.data.Move
import ru.touchthegrass.tpc.data.local.LocalMovesProvider
import ru.touchthegrass.tpc.repository.MoveRepository

class LocalMoveRepository: MoveRepository {

    override fun getPossibleMoves(): Flow<List<Move>> = flow {
        while (true) {
            emit(LocalMovesProvider.getPossibleMoves())
            delay(100)
        }
    }
}
