package ru.touchthegrass.tpc.ui.drawable.paths

import android.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.vector.PathParser
import ru.touchthegrass.tpc.data.PieceType

val BASE_PIECE_SCALE_X = 0.5f
val BASE_PIECE_SCALE_Y = 0.5f

val kingPathStr = "M 50 54 l 12 -21 c 8 -12 -4 -18 -10.5 -18 v -8 h 4 v -3 h -4 v -4 h -3 v 4 h -4 v 3 h 4 v 8 c -6.5 0 -18.5 6 -10.5 18 Z M 19 56 l 26 0 q -2 -11 -14 -24 c -10 -10 -20 -8 -23 5 c -1 10 4 14 11 19 Z M 81 56 l -26 0 q 2 -11 14 -24 c 10 -10 20 -8 23 5 c 1 10 -4 14 -11 19 Z M 20 64 l 60 0 l 0 -5 l -60 0 Z M 26 89 l 48 0 l -3 -22 l -42 0 Z M 20 99.5 l 60 0 l 0 -7 l -60 0 Z"
val kingPath = PathParser().parsePathString(kingPathStr)

val queenPathStr = "M 50 12 c -2.5 0 -4 -1.5 -4 -4 c 0 -2.5 1.5 -4 4 -4 c 2.5 0 4 1.5 4 4 c 0 2.5 -1.5 4 -4 4 Z M 36.5 42 l 4 -10 l -10 -11 l -3 13 Z M 63.5 42 l -4 -10 l 10 -11 l 3 13 Z M 25 56 l 50 0 l 8 -28 l -20 18 l -13 -28 l -13 28 l -20 -18 Z M 25 64 l 50 0 l 0 -5 l -50 0 Z M 26 89 l 48 0 l -3 -22 l -42 0 Z M 20 99.5 l 60 0 l 0 -7 l -60 0 Z"
val queenPath = PathParser().parsePathString(queenPathStr)

val bishopPathStr = "M 50 10 c -2.5 0 -4 -1.5 -4 -4 c 0 -2.5 1.5 -4 4 -4 c 2.5 0 4 1.5 4 4 c 0 2.5 -1.5 4 -4 4 Z M 29 64 l 42 0 c 8 -10 8 -20 -21 -50 c -29 30 -29 40 -21 50 Z M 26 89 l 48 0 l -3 -22 l -42 0 Z M 20 99.5 l 60 0 l 0 -7 l -60 0 Z"
val bishopPath = PathParser().parsePathString(bishopPathStr)

val knightPathStr = "M 29 89 l 42 0 l 4 -6 l 0 -50 c -28 -26 -26 -26 -36 -20 l -5 -5 l -1.5 1.5 l -3.5 -2.1 l -2 2 l 8 8 l -20 22 l 8 9 l 10 -10 q 15 2 14 -14 a 1 1 0 1 1 3 -1 q 2 4 -1.2 12 l 4 4 l -35 35 Z M 78 70 l 5 -3 v -40 c -32 -29 -34 -31 -44 -21 q -2 2 0 4 q 6 -7 15 0 l 24 21 Z M 29 99.5 l 46 0 l 0 -7 l -46 0 Z"
val knightPath = PathParser().parsePathString(knightPathStr)

val rookPathStr = "M 20 30 l 60 0 l 0 -29.5 l -10 0 l 0 20 l -15 0 l 0 -20 l -10 0 l 0 20 l -15 0 l 0 -20 l -10 0 Z M 24 41 l 52 0 l 0 -8 l -52 0 Z M 26 89 l 48 0 l -5 -45 l -38 0 Z M 20 99.5 l 60 0 l 0 -7 l -60 0 Z"
val rookPath = PathParser().parsePathString(rookPathStr)

val pawnPathStr = "M 36 30 a 18 18 0 1 1 28 0 Z M 32 40 l 36 0 l 0 -6 l -36 0 Z M 30 89 l 40 0 q -8 -15 -10 -45 l -20 0 q -2 30 -10 45 Z M 20 99.5 l 60 0 l 0 -7 l -60 0 Z"
val pawnPath = PathParser().parsePathString(pawnPathStr)

fun getPiecePath(type: PieceType): Path {
    val path = when(type) {
        PieceType.KING -> kingPath
        PieceType.QUEEN -> queenPath
        PieceType.BISHOP -> bishopPath
        PieceType.KNIGHT -> knightPath
        PieceType.ROOK -> rookPath
        PieceType.PAWN -> pawnPath
    }.toPath()
    val scaleMatrix = Matrix()
    scaleMatrix.setScale(BASE_PIECE_SCALE_X, BASE_PIECE_SCALE_Y, 0f, 0f)
    val androidPath = path.asAndroidPath()
    androidPath.transform(scaleMatrix)
    return androidPath.asComposePath()
}
