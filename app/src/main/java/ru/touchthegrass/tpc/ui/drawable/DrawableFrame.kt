package ru.touchthegrass.tpc.ui.drawable

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ru.touchthegrass.tpc.ui.drawable.paths.getFramePath

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawFrame(textMeasure: TextMeasurer, frameColor: Color) {
    val framePath = getFramePath()
    val frameSize = framePath.getBounds().size
    withTransform({
        translate(
            left = center.x - framePath.getBounds().center.x,
            top = center.y - framePath.getBounds().center.y
        )
    }) {
        drawPath(
            path = framePath,
            color = frameColor,
            style = Fill
        )

        drawText(
            textMeasurer = textMeasure,
            text = "  8   7   6   5   9  10  11  12 ",
            topLeft = Offset(frameSize.width * 0.275f, frameSize.height * 0.01f),
            style = TextStyle(
                color = Color.White,
                fontSize = 12.sp,
                letterSpacing = 1.5.sp,
                textAlign = TextAlign.Center
            )
        )
        drawText(
            textMeasurer = textMeasure,
            text = "  A   B   C   D   E   F   G   H ",
            topLeft = Offset(frameSize.width * 0.275f, frameSize.height * 0.945f),
            style = TextStyle(
                color = Color.White,
                fontSize = 12.sp,
                letterSpacing = 1.5.sp,
                textAlign = TextAlign.Center
            )
        )
        rotate(60f, frameSize.center) {
            drawText(
                textMeasurer = textMeasure,
                text = "  L   K   J   I   E   F   G   H ",
                topLeft = Offset(frameSize.width * 0.275f, frameSize.height * 0f),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp,
                    letterSpacing = 1.6.sp,
                    textAlign = TextAlign.Center
                )
            )
            drawText(
                textMeasurer = textMeasure,
                text = "  8   7   6   5   4   3   2   1 ",
                topLeft = Offset(frameSize.width * 0.275f, frameSize.height * 0.94f),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp,
                    letterSpacing = 1.6.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
        rotate(-60f, frameSize.center) {
            drawText(
                textMeasurer = textMeasure,
                text = "  A   B   C   D   I   J   K   L ",
                topLeft = Offset(frameSize.width * 0.275f, frameSize.height * 0.01f),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp,
                    letterSpacing = 1.6.sp,
                    textAlign = TextAlign.Center
                )
            )
            drawText(
                textMeasurer = textMeasure,
                text = "  1   2   3   4   9  10  11  12 ",
                topLeft = Offset(frameSize.width * 0.275f, frameSize.height * 0.95f),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp,
                    letterSpacing = 1.6.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}
