package ru.touchthegrass.tpc.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.R
import ru.touchthegrass.tpc.data.PieceColor

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingChip(
    rating: Int
) {
    AssistChip(
        modifier = Modifier
            .padding(end = 4.dp),
        label = {
            Text(
                text = "$rating",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            labelColor = MaterialTheme.colorScheme.onSecondaryContainer,
            leadingIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        border = AssistChipDefaults.assistChipBorder(
            borderColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(
                modifier = Modifier.size(AssistChipDefaults.IconSize),
                imageVector = Icons.Default.Star,
                contentDescription = stringResource(R.string.rating_icon),
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        onClick = { },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameRuleChip(
    icon: ImageVector,
    title: String
) {
    AssistChip(
        label = { Text(title) },
        colors = AssistChipDefaults.assistChipColors(
            labelColor = MaterialTheme.colorScheme.onSurface,
            leadingIconContentColor = MaterialTheme.colorScheme.secondary
        ),
        border = AssistChipDefaults.assistChipBorder(
            borderColor = MaterialTheme.colorScheme.secondary
        ),
        leadingIcon = {
            Icon(
                modifier = Modifier.size(AssistChipDefaults.IconSize),
                imageVector = icon,
                contentDescription = stringResource(R.string.chip_icon),
                tint = MaterialTheme.colorScheme.secondary
            )

        },
        onClick = {  },
    )
}
