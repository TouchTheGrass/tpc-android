package ru.touchthegrass.tpc.ui.paths

import android.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.vector.PathParser

const val BASE_SCALE_X = 1f
const val BASE_SCALE_Y = 0.866f
val cellPathStrList = listOf(
    "M 0.2887, 0 L 0.433, 0 L 0.3789, 0.1563 L 0.2165, 0.125 Z",
    "M 0.3789, 0.1563 L 0.5413, 0.1875 L 0.5052, 0.375 L 0.3248, 0.3125 Z",
    "M 0.5052, 0.375 L 0.6856, 0.4375 L 0.6676, 0.6563 L 0.4691, 0.5625 Z",
    "M 0.6676, 0.6563 L 0.866, 0.75 L 0.866, 1 L 0.6495, 0.875 Z",
    "M 0.433, 0 L 0.5774, 0 L 0.5413, 0.1875 L 0.3789, 0.1563 Z",
    "M 0.5413, 0.1875 L 0.7036, 0.2188 L 0.6856, 0.4375 L 0.5052, 0.375 Z",
    "M 0.6856, 0.4375 L 0.866, 0.5 L 0.866, 0.75 L 0.6676, 0.6563 Z",
    "M 0.5774, 0 L 0.7217, 0 L 0.7036, 0.2188 L 0.5413, 0.1875 Z",
    "M 0.7036, 0.2188 L 0.866, 0.25 L 0.866, 0.5 L 0.6856, 0.4375 Z",
    "M 0.7217, 0 L 0.866, 0 L 0.866, 0.25 L 0.7036, 0.2188 Z",
)
val pathParser = PathParser()
val cellPaths = cellPathStrList.map { pathParser.parsePathString(it) }

fun getCellPath(cellIndex: Int): Path {
    val path = cellPaths[cellIndex % cellPaths.size].toPath()
    val scaleMatrix = Matrix()
    scaleMatrix.setScale(BASE_SCALE_X, BASE_SCALE_Y, 0f, 0f)
    val androidPath = path.asAndroidPath()
    androidPath.transform(scaleMatrix)
    return androidPath.asComposePath()
}
