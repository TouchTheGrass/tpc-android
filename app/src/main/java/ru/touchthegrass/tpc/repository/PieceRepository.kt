package ru.touchthegrass.tpc.repository

import kotlinx.coroutines.flow.Flow
import ru.touchthegrass.tpc.data.Piece

interface PieceRepository {
    fun getAllPieces(): Flow<List<Piece>>
}
