package ru.touchthegrass.tpc.manager.local

import ru.touchthegrass.tpc.data.PieceType
import ru.touchthegrass.tpc.data.local.LocalPiecesProvider
import ru.touchthegrass.tpc.manager.PositionManager

class LocalPositionManager: PositionManager {
    override fun getPossibleMoves(pieceId: Int): List<String> {
        val piece = LocalPiecesProvider.allPieces.find { it.id == pieceId } ?: return emptyList()
        return when (piece.type) {
            PieceType.PAWN -> {
                when (piece.position) {
                    "a2" -> listOf("a3", "a4")
                    "b2" -> listOf("b3", "b4")
                    "c2" -> listOf("c3", "c4")
                    "d2" -> listOf("d3", "d4")
                    "e2" -> listOf("e3", "e4")
                    "f2" -> listOf("f3", "f4")
                    "g2" -> listOf("g3", "g4")
                    "h2" -> listOf("h3", "h4")
                    else -> emptyList()
                }
            }
            PieceType.ROOK -> {
                when (piece.position) {
                    "g1" -> listOf("f3", "h4")
                    "b1" -> listOf("a3", "c3")
                    else -> emptyList()
                }
            }
            else -> emptyList()
        }
    }
}