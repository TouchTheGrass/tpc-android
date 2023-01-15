package ru.touchthegrass.tpc.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FirstPage
import androidx.compose.material.icons.filled.Square
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.R
import ru.touchthegrass.tpc.ui.component.BottomButtonBar
import ru.touchthegrass.tpc.ui.component.SwitchListItem

@Preview
@Composable
fun TpcFilterScreen(
    closeFilterScreen: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            item {
                Text(
                    text = stringResource(id = R.string.rules),
                    modifier = Modifier.padding(top = 24.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.board_form),
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 32.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            item {
                SwitchListItem(
                    icon = Icons.Default.Square,
                    text = stringResource(id = R.string.quadrangle),
                    checked = true
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.victory_condition),
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 32.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            item {
                SwitchListItem(
                    icon = Icons.Default.FirstPage,
                    text = stringResource(id = R.string.first_checkmate),
                    checked = true
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.timer),
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 32.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            item {
                SwitchListItem(
                    icon = Icons.Default.Timer,
                    text = stringResource(id = R.string.timer),
                    checked = false
                )
            }
        }
        BottomButtonBar(
            text = stringResource(id = R.string.apply_filters),
            onClick = closeFilterScreen
        )
    }
}