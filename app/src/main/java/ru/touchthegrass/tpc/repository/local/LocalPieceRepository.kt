package ru.touchthegrass.tpc.repository.local

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.touchthegrass.tpc.data.GameSession
import ru.touchthegrass.tpc.data.Piece
import ru.touchthegrass.tpc.data.local.LocalMovesProvider
import ru.touchthegrass.tpc.data.local.LocalPiecesProvider
import ru.touchthegrass.tpc.repository.PieceRepository

class LocalPieceRepository: PieceRepository {

    private val allPieces: MutableList<Piece> = mutableListOf()

    override fun getAllPieces(): Flow<List<Piece>> = flow {
        while (true) {
            emit(allPieces)
            delay(100)
        }
    }

    fun initPieces(gameSession: GameSession) {
        allPieces.clear()
        allPieces.addAll(LocalPiecesProvider.getWhitePieces(gameSession))
        LocalMovesProvider.updateLocalPieces(allPieces)
    }

    fun movePiece(pieceId: Int, position: String) {
        allPieces.find { it.id == pieceId }
            ?.let {
                it.position = position
            }
        LocalMovesProvider.updateLocalPieces(allPieces)
    }
}
