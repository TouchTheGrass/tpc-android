package ru.touchthegrass.tpc.ui.drawable.paths

import android.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.vector.PathParser

const val BASE_FRAME_SCALE_X = 1.58125f
const val BASE_FRAME_SCALE_Y = 1.58125f

val framePathStr = "M 335.913 0 l 134.3656 0 c 23.51384 0 38.62988 8.7273 50.3868 29.091 l 134.3648 232.728 c 11.75692 20.3637 11.75692 37.8183 0 58.182 l -134.3648 232.728 c -11.75692 20.3637 -26.87296 29.091 -50.3868 29.091 l -268.7312 0 c -23.51398 0 -38.62988 -8.7273 -50.3868 -29.091 l -134.3648 -232.728 c -11.75692 -20.3637 -11.75692 -37.8183 0 -58.182 l 134.3648 -232.728 c 11.75692 -20.3637 26.87296 -29.091 50.3868 -29.091 Z"
val framePath = PathParser().parsePathString(framePathStr)

fun getFramePath(): Path {
    val path = framePath.toPath()
    val scaleMatrix = Matrix()
    scaleMatrix.setScale(BASE_FRAME_SCALE_X, BASE_FRAME_SCALE_Y, 0f, 0f)
    val androidPath = path.asAndroidPath()
    androidPath.transform(scaleMatrix)
    return androidPath.asComposePath()
}
