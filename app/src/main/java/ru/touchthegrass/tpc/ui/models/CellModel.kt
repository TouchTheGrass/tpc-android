package ru.touchthegrass.tpc.ui.models

import androidx.compose.ui.graphics.Path
import ru.touchthegrass.tpc.ui.paths.getCellPath

data class CellModel(
    val scale: Float = 1f,
    val angle: Float = 0f,
    val xPos: Int = 0,
    val yPos: Int = 0,
    var position: String,
    val cellType: Int
) {
    var path: Path = getCellPath(cellType)
}
