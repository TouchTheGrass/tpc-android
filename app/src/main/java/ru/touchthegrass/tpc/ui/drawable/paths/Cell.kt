package ru.touchthegrass.tpc.ui.drawable.paths

import android.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.vector.PathParser

const val BASE_CELL_SCALE_X = 1.4f
const val BASE_CELL_SCALE_Y = 1.4f

val bbb = listOf(
    "M 5 0 l 58 27 l 0 73 l -63 -36 Z",
    "M 10 0 l 58 27 l -5 64 l -63 -36 Z",
    "M 16 0 l 58 27 l -10.5 55 l -63 -36 Z",
    "M 21 0 l 58 27 l -16 45 l -63 -36 Z",
    "M 5 0 l 53 18 l 0 73 l -58 -27 Z",
    "M 10 0 l 53 18 l -5 64 l -58 -27 Z",
    "M 16 0 l 53 18 l -11 55 l -58 -27 Z",
    "M 21 0 l 53 18 l -16 46 l -58 -27 Z",
    "M 6 0 l 47 9 l 0 73 l -53 -18 Z",
    "M 11 0 l 47 9 l -5 64 l -53 -18 Z",
    "M 17 0 l 47 9 l -11 55 l -53 -18 Z",
    "M 23 0 l 47 9 l -17 46 l -53 -18",
    "M 5 0 l 42 0 l 0 73 l -47 -9 Z",
    "M 10 0 l 42 0 l -5 64 l -47 -9 Z",
    "M 16 0 l 42 0 l -11 55 l -47 -9 Z",
    "M 21 0 l 42 0 l -16 46 l -47 -9 Z"
)
val cellPathStrList = listOf(
    "M 5.249 0 l 57.735 27.273 l 0 72.727 l -62.984 -36.364 Z",
    "M 10.498 0 l 57.735 27.273 l -5.249 63.636 l -62.984 -36.364 Z",
    "M 15.746 0 l 57.735 27.273 l -10.498 54.546 l -62.984 -36.364 Z",
    "M 20.995 0 l 57.735 27.273 l -15.746 45.454 l -62.984 -36.364 Z",
    "M 5.248 0 l 52.487 18.182 l 0 72.727 l -57.735 -27.273 Z",

    "M 10.497 0 l 52.487 18.182 l -5.248 63.636 l -57.735 -27.273 Z",

    "M 15.645 0 l 52.487 18.182 l -10.497 54.545 l -57.735 -27.273 Z",
    "M 20.994 0 l 52.487 18.182 l -15.645 45.455 l -57.735 -27.273 Z",
    "M 5.249 0 l 47.238 9.091 l 0 72.728 l -52.487 -18.182 Z",
    "M 10.497 0 l 47.238 9.091 l -5.249 63.637 l -52.486 -18.182 Z",
    "M 15.746 0 l 47.238 9.091 l -10.497 54.546 l -52.486 -18.182 Z",
    "M 20.995 0 l 47.238 9.091 l -15.746 45.454 l -52.486 -18.182 Z",
    "M 5.249 0 l 41.989 0 l 0 72.727 l -47.238 -9.091 Z",
    "M 10.5 0 l 41.989 0 l -5.249 63.636 l -47.238 -9.091 Z",
    "M 15.746 0 l 41.989 0 l -10.5 54.545 l -47.238 -9.091 Z",
    "M 20.994 0 l 41.989 0 l -15.746 45.455 l -47.238 -9.091 Z"
)
val cellPaths = cellPathStrList.map { PathParser().parsePathString(it) }

fun getCellPath(pathIndex: Int): Path {
    val path = cellPaths[pathIndex].toPath()
    val scaleMatrix = Matrix()
    scaleMatrix.setScale(BASE_CELL_SCALE_X, BASE_CELL_SCALE_Y, 0f, 0f)
    val androidPath = path.asAndroidPath()
    androidPath.transform(scaleMatrix)
    return androidPath.asComposePath()
}
