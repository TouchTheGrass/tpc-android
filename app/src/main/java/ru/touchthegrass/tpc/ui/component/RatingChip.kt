package ru.touchthegrass.tpc.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.R

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
