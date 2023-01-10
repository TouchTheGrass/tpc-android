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
import ru.touchthegrass.tpc.data.Piece
import ru.touchthegrass.tpc.data.PieceColor
import ru.touchthegrass.tpc.ui.util.getPieceImageId

@Composable
fun PositionVariantItem(
    position: String,
    selected: Boolean,
    onItemPressed: (Boolean) -> Unit = {}
) {
    val borderColor = if (selected) colorResource(R.color.green0) else MaterialTheme.colorScheme.primary

    Card(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clickable { onItemPressed },
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
            Text(
                text = position.uppercase(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
