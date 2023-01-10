package ru.touchthegrass.tpc.data.local

import ru.touchthegrass.tpc.data.GameSession
import ru.touchthegrass.tpc.data.Piece
import ru.touchthegrass.tpc.data.PieceColor
import ru.touchthegrass.tpc.data.PieceType

object LocalPiecesProvider {

    private var id = 1
    private fun getId() = id++

    fun getTestPieces(gameSession: GameSession) = LocalCellsProvider.coordinateFirstParts.flatMap { first ->
        LocalCellsProvider.coordinateSecondParts.map { second ->
            Piece(
                id = getId(),
                type = PieceType.PAWN,
                color = PieceColor.WHITE,
                position = "$first$second",
                gameSession = gameSession
            )
        }
    }

    fun getWhitePieces(gameSession: GameSession) = listOf("a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2").map{ position ->
        Piece(
            id = getId(),
            type = PieceType.PAWN,
            color = PieceColor.WHITE,
            position = position,
            gameSession = gameSession
        )
    } + listOf(
        Piece(
            id = getId(),
            type = PieceType.KNIGHT,
            color = PieceColor.WHITE,
            position = "a1",
            gameSession = gameSession
        ),
        Piece(
            id = getId(),
            type = PieceType.KNIGHT,
            color = PieceColor.WHITE,
            position = "h1",
            gameSession = gameSession
        ),
        Piece(
            id = getId(),
            type = PieceType.ROOK,
            color = PieceColor.WHITE,
            position = "b1",
            gameSession = gameSession
        ),
        Piece(
            id = getId(),
            type = PieceType.ROOK,
            color = PieceColor.WHITE,
            position = "g1",
            gameSession = gameSession
        ),
        Piece(
            id = getId(),
            type = PieceType.BISHOP,
            color = PieceColor.WHITE,
            position = "c1",
            gameSession = gameSession
        ),
        Piece(
            id = getId(),
            type = PieceType.BISHOP,
            color = PieceColor.WHITE,
            position = "f1",
            gameSession = gameSession
        ),
        Piece(
            id = getId(),
            type = PieceType.QUEEN,
            color = PieceColor.WHITE,
            position = "e1",
            gameSession = gameSession
        ),
        Piece(
            id = getId(),
            type = PieceType.KING,
            color = PieceColor.WHITE,
            position = "d1",
            gameSession = gameSession
        )
    )
}