package ru.touchthegrass.tpc.ui.drawable

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import ru.touchthegrass.tpc.data.Piece
import ru.touchthegrass.tpc.data.PieceColor
import kotlin.math.cos
import kotlin.math.sin

enum class PieceHighlightType {
    COMMON, SELECTED, UNDER_ATTACK, SELECTED_TO_ATTACK
}

data class DrawablePiece(
    val xOffset: Float,
    val yOffset: Float,
    val angle: Float,
    val info: Piece,
    val path: Path,
    val highlightType: PieceHighlightType = PieceHighlightType.COMMON
)

fun DrawScope.drawPieces(
    pieces: List<DrawablePiece>,
    whiteColor: Color,
    blackColor: Color,
    redColor: Color,
    selectColor: Color,
    warningColor: Color,
    dangerColor: Color
) {
    for (piece in pieces) {
        val pieceCenter = piece.path.getBounds().center
        val angle = piece.angle

        val x1 = -piece.xOffset
        val y1 = -piece.yOffset

        val x2 = x1 * cos(angle) - y1 * sin(angle)
        val y2 = x1 * sin(angle) + y1 * cos(angle)

        val x3 = x2 + center.x - pieceCenter.x
        val y3 = y2 + center.y - pieceCenter.y


        withTransform({
            translate(
                left = x3,
                top = y3
            )

        }) {
            drawPath(
                path = piece.path,
                color = when (piece.highlightType) {
                    PieceHighlightType.COMMON -> {
                        when (piece.info.color) {
                            PieceColor.WHITE -> whiteColor
                            PieceColor.BLACK -> blackColor
                            PieceColor.RED -> redColor
                        }
                    }
                    PieceHighlightType.SELECTED -> selectColor
                    PieceHighlightType.UNDER_ATTACK -> warningColor
                    PieceHighlightType.SELECTED_TO_ATTACK -> dangerColor
                },
                style = Fill
            )
            drawPath(
                path = piece.path,
                color = Color.Black,
                style = Stroke(1f)
            )
        }
    }
}
