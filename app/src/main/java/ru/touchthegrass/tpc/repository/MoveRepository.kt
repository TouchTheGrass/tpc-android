package ru.touchthegrass.tpc.repository

import kotlinx.coroutines.flow.Flow
import ru.touchthegrass.tpc.data.Move

interface MoveRepository {
    fun getPossibleMoves(): Flow<List<Move>>
}
