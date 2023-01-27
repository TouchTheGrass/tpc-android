package ru.touchthegrass.tpc.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.R
import ru.touchthegrass.tpc.ui.drawable.*

@OptIn(ExperimentalTextApi::class)
@Composable
fun QuadrangleBoard(
    modifier: Modifier,
    pieces: List<DrawablePiece>,
    positions: List<DrawablePosition>
) {

    val textMeasure = rememberTextMeasurer()
    val primaryColor = MaterialTheme.colorScheme.primary
    val whiteColor = colorResource(R.color.white)
    val grayColor = colorResource(R.color.gray3)
    val darkGrayColor = colorResource(R.color.gray1)
    val blackColor = colorResource(R.color.black)
    val redColor = colorResource(R.color.crimson)
    val selectColor = colorResource(R.color.green0)
    val warningColor = colorResource(R.color.yellow)
    val dangerColor = colorResource(R.color.orange)

    Column(
        modifier = modifier
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawFrame(
                textMeasure = textMeasure,
                frameColor = primaryColor
            )
            drawCells(
                whiteColor = whiteColor,
                blackColor = darkGrayColor,
            )
            drawPieces(
                pieces = pieces,
                whiteColor = whiteColor,
                blackColor = blackColor,
                redColor = redColor,
                selectColor = selectColor,
                warningColor = warningColor,
                dangerColor = dangerColor
            )
            drawPositions(
                positions = positions,
                commonColor = grayColor,
                selectColor = selectColor
            )
        }
    }
}
