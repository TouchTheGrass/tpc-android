package ru.touchthegrass.tpc.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.R
import ru.touchthegrass.tpc.data.PieceColor

enum class PieceColorStatus {
    ENABLED, DISABLED, SELECTED
}

@Composable
fun PieceColorVariantItem(
    modifier: Modifier = Modifier,
    color: PieceColor,
    status: PieceColorStatus,
    onPieceColorChanged: (PieceColor) -> Unit
) {
    val borderColor = when (status) {
        PieceColorStatus.ENABLED -> MaterialTheme.colorScheme.primary
        PieceColorStatus.DISABLED -> MaterialTheme.colorScheme.outline
        PieceColorStatus.SELECTED -> colorResource(R.color.green0)
    }
    Card(
        modifier = modifier
            .clickable { onPieceColorChanged(color) },
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(5.dp, borderColor),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var imageResourceId: Int? = null
            var imageDescription: String? = null

            when (color) {
                PieceColor.WHITE -> {
                    imageResourceId = R.drawable.king_white_512
                    imageDescription = "White pieces"
                }

                PieceColor.BLACK -> {
                    imageResourceId = R.drawable.king_black_512
                    imageDescription = "Black pieces"
                }

                PieceColor.RED -> {
                    imageResourceId = R.drawable.king_red_512
                    imageDescription = "Red pieces"
                }
            }

            Image(
                modifier = Modifier
                    .size(80.dp)
                    .padding(bottom = 8.dp),
                painter = painterResource(imageResourceId),
                contentDescription = imageDescription
            )
            Text(
                text = color.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
