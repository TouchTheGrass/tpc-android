package ru.touchthegrass.tpc.manager.local

import ru.touchthegrass.tpc.manager.GameManager
import ru.touchthegrass.tpc.repository.local.LocalPieceRepository

class LocalGameManager(
    override val pieceRepository: LocalPieceRepository
): GameManager {
    override fun movePiece(pieceId: Int, position: String) {
        pieceRepository.movePiece(pieceId, position)
    }
}
