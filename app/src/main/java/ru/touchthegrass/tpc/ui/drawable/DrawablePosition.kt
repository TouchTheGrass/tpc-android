package ru.touchthegrass.tpc.ui.drawable

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import kotlin.math.cos
import kotlin.math.sin

val POSITION_ARC_RADIUS = 35f

data class DrawablePosition(
    val xOffset: Float,
    val yOffset: Float,
    val angle: Float,
    val str: String,
    val isSelected: Boolean
)

fun DrawScope.drawPositions(
    positions: List<DrawablePosition>,
    commonColor: Color,
    selectColor: Color
) {
    for (position in positions) {
        val angle = position.angle

        val x1 = -position.xOffset
        val y1 = -position.yOffset

        val x2 = x1 * cos(angle) - y1 * sin(angle)
        val y2 = x1 * sin(angle) + y1 * cos(angle)

        val x3 = x2 + center.x - POSITION_ARC_RADIUS / 2
        val y3 = y2 + center.y - POSITION_ARC_RADIUS / 2

        withTransform({
            translate(
                left = x3,
                top = y3
            )
        }) {
            drawArc(
                color = if (position.isSelected) selectColor else commonColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = true,
                size = Size(POSITION_ARC_RADIUS, POSITION_ARC_RADIUS)
            )
        }
    }
}
