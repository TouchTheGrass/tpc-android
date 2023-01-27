package ru.touchthegrass.tpc.data.local

import ru.touchthegrass.tpc.data.GameSession
import ru.touchthegrass.tpc.data.Piece
import ru.touchthegrass.tpc.data.PieceColor
import ru.touchthegrass.tpc.data.PieceType

object LocalPiecesProvider : LocalProvider() {

    fun getWhitePieces(gameSession: GameSession): List<Piece> {
        val pieces = listOf("a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2").map { position ->
            Piece(
                id = getId(),
                type = PieceType.PAWN,
                color = PieceColor.WHITE,
                position = position,
                gameSession = gameSession
            )
        } + listOf("a1", "h1").map { position ->
            Piece(
                id = getId(),
                type = PieceType.ROOK,
                color = PieceColor.WHITE,
                position = position,
                gameSession = gameSession
            )
        } + listOf("b1", "g1").map { position ->
            Piece(
                id = getId(),
                type = PieceType.KNIGHT,
                color = PieceColor.WHITE,
                position = position,
                gameSession = gameSession
            )
        } + listOf("c1", "f1").map { position ->
            Piece(
                id = getId(),
                type = PieceType.BISHOP,
                color = PieceColor.WHITE,
                position = position,
                gameSession = gameSession
            )
        } + listOf(
            Piece(
                id = getId(),
                type = PieceType.KING,
                color = PieceColor.WHITE,
                position = "d1",
                gameSession = gameSession
            ),
            Piece(
                id = getId(),
                type = PieceType.QUEEN,
                color = PieceColor.WHITE,
                position = "e1",
                gameSession = gameSession
            )
        )
        return pieces
    }
}
