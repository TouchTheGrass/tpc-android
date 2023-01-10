package ru.touchthegrass.tpc.ui.component

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import ru.touchthegrass.tpc.data.PieceColor
import ru.touchthegrass.tpc.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PieceColorChip(
    color: PieceColor
) {
    val containerColorId: Int
    val labelColorId: Int
    when (color) {
        PieceColor.WHITE -> {
            containerColorId = R.color.white
            labelColorId = R.color.black
        }
        PieceColor.BLACK -> {
            containerColorId = R.color.black
            labelColorId = R.color.white
        }
        PieceColor.RED -> {
            containerColorId = R.color.crimson
            labelColorId = R.color.white
        }
    }

    AssistChip(
        label = {
            Text(
                text = color.title,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = colorResource(containerColorId),
            labelColor = colorResource(labelColorId)
        ),
        border = AssistChipDefaults.assistChipBorder(
            borderColor = Color.Transparent
        ),
        onClick = { },
    )
}