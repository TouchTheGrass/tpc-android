package ru.touchthegrass.tpc.manager

import ru.touchthegrass.tpc.repository.PieceRepository

interface GameManager {

    val pieceRepository: PieceRepository

    fun movePiece(pieceId: Int, position: String)
}
