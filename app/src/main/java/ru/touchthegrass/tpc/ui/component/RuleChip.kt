package ru.touchthegrass.tpc.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import ru.touchthegrass.tpc.R

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