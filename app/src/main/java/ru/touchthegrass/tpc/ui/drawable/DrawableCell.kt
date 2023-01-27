package ru.touchthegrass.tpc.ui.drawable

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.*
import ru.touchthegrass.tpc.ui.drawable.paths.BASE_CELL_SCALE_X
import ru.touchthegrass.tpc.ui.drawable.paths.BASE_CELL_SCALE_Y
import ru.touchthegrass.tpc.ui.drawable.paths.cellOffsets
import ru.touchthegrass.tpc.ui.drawable.paths.getCellPath

fun DrawScope.drawCells( whiteColor: Color, blackColor: Color) {
    var isBlack = true
    for (i in 0..5) {
        val angle = i * 60f
        for (j in 0..15) {
            if (j % 4 != 0) isBlack = !isBlack
            val cellOffset = cellOffsets[j]
            withTransform({
                rotate(angle)
                translate(
                    left = center.x - BASE_CELL_SCALE_X * cellOffset.first,
                    top = center.y - BASE_CELL_SCALE_Y * cellOffset.second
                )
            }) {
                drawPath(
                    path = getCellPath(j),
                    color = if (isBlack) blackColor else whiteColor,
                    style = Fill
                )
            }
        }
        isBlack = !isBlack
    }
}
