package ru.touchthegrass.tpc.ui.util

import androidx.compose.ui.unit.IntOffset
import ru.touchthegrass.tpc.data.PieceColor
import ru.touchthegrass.tpc.data.PieceType
import ru.touchthegrass.tpc.R
import ru.touchthegrass.tpc.data.Piece

private val pieceImageIdMap = mapOf(
    (PieceType.KING to PieceColor.WHITE) to R.drawable.king_white_512,
    (PieceType.KING to PieceColor.BLACK) to R.drawable.king_black_512,
    (PieceType.KING to PieceColor.RED) to R.drawable.king_red_512,

    (PieceType.QUEEN to PieceColor.WHITE) to R.drawable.queen_white_512,
    (PieceType.QUEEN to PieceColor.BLACK) to R.drawable.queen_black_512,
    (PieceType.QUEEN to PieceColor.RED) to R.drawable.queen_red_512,

    (PieceType.BISHOP to PieceColor.WHITE) to R.drawable.bishop_white_512,
    (PieceType.BISHOP to PieceColor.BLACK) to R.drawable.bishop_black_512,
    (PieceType.BISHOP to PieceColor.RED) to R.drawable.bishop_red_512,

    (PieceType.ROOK to PieceColor.WHITE) to R.drawable.rook_white_512,
    (PieceType.ROOK to PieceColor.BLACK) to R.drawable.rook_black_512,
    (PieceType.ROOK to PieceColor.RED) to R.drawable.rook_red_512,

    (PieceType.KNIGHT to PieceColor.WHITE) to R.drawable.knight_white_512,
    (PieceType.KNIGHT to PieceColor.BLACK) to R.drawable.knight_black_512,
    (PieceType.KNIGHT to PieceColor.RED) to R.drawable.knight_red_512,

    (PieceType.PAWN to PieceColor.WHITE) to R.drawable.pawn_white_512,
    (PieceType.PAWN to PieceColor.BLACK) to R.drawable.pawn_black_512,
    (PieceType.PAWN to PieceColor.RED) to R.drawable.pawn_red_512,
)

private val positionOffsetMap = mapOf(
    "a1" to IntOffset(258, 873),
    "a2" to IntOffset(242, 802),

    "b1" to IntOffset(336, 873),
    "b2" to IntOffset(320, 775),

    "c1" to IntOffset(412, 873),
    "c2" to IntOffset(400, 760),

    "d1" to IntOffset(486, 873),
    "d2" to IntOffset(480, 740),

    "e1" to IntOffset(554, 873),
    "e2" to IntOffset(554, 740),

    "f1" to IntOffset(622, 873),
    "f2" to IntOffset(630, 760),

    "g1" to IntOffset(696, 873),
    "g2" to IntOffset(702, 775),

    "h1" to IntOffset(770, 873),
    "h2" to IntOffset(780, 802),
)

fun getPieceImageId(piece: Piece): Int {
    return pieceImageIdMap[piece.type to piece.color]!!
}

fun getPieceOffset(position: String): IntOffset {
    return positionOffsetMap.getOrDefault(position, IntOffset(0, 0))
}
