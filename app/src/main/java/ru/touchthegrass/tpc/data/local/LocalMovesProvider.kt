package ru.touchthegrass.tpc.data.local

import ru.touchthegrass.tpc.data.Move
import ru.touchthegrass.tpc.data.Piece
import ru.touchthegrass.tpc.data.PieceType

object LocalMovesProvider {

    private var localPieces: List<Piece> = emptyList()

    fun getPossibleMoves(): List<Move> {
        return localPieces.map { piece ->
            Move(
                piece = piece,
                possibleMoves = when (piece.type) {
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

                    PieceType.KNIGHT -> {
                        when (piece.position) {
                            "b1" -> listOf("a3", "c3")
                            "g1" -> listOf("f3", "h3")
                            else -> emptyList()
                        }
                    }

                    else -> emptyList()
                }
            )
        }
    }

    fun updateLocalPieces(pieces: List<Piece>) {
        localPieces = pieces
    }
}
