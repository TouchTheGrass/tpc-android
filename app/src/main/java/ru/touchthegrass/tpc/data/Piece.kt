package ru.touchthegrass.tpc.data

enum class PieceType {
    KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN
}

enum class PieceColor(val title: String) {
    WHITE("White"),
    BLACK("Black"),
    RED("Red")
}

data class Piece(
    var id: Int,
    var type: PieceType,
    var color: PieceColor,
    var position: String,
    var gameSession: GameSession
)
