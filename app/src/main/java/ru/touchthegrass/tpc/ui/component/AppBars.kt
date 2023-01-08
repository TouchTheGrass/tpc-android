package ru.touchthegrass.tpc.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TpcSearchPlayerBar(
    value: String,
    onValueChange: (String) -> Unit,
    navigateToFilter: () -> Unit
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 16.dp),
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = stringResource(id = R.string.search_player),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search),
                modifier = Modifier.padding(start = 16.dp),
                tint = MaterialTheme.colorScheme.outline
            )
        },
        trailingIcon = {
            IconButton(onClick = navigateToFilter) {
                Icon(
                    imageVector = Icons.Filled.FilterAlt,
                    contentDescription = stringResource(R.string.filter),
                    modifier = Modifier.padding(end = 16.dp),
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        singleLine = true,
        shape = CircleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
