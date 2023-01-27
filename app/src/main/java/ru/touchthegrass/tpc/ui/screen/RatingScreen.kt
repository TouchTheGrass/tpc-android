package ru.touchthegrass.tpc.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.R
import ru.touchthegrass.tpc.data.Player
import ru.touchthegrass.tpc.state.TpcDataState
import ru.touchthegrass.tpc.state.TpcUIState
import ru.touchthegrass.tpc.state.TpcUserState
import ru.touchthegrass.tpc.ui.component.RatingPlayerListItem
import ru.touchthegrass.tpc.ui.component.SearchPlayerField

@Composable
fun TpcRatingScreen(
    uiState: TpcUIState,
    userState: TpcUserState,
    dataState: TpcDataState,
    closeFilterScreen: () -> Unit,
    navigateToFilter: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit
) {
    if (uiState.openedFilter) {
        BackHandler {
            closeFilterScreen()
        }
        TpcFilterScreen(closeFilterScreen)
    } else {
        val players = dataState.players
            .filter { player ->
                player.name.lowercase().contains(dataState.playerFilter.lowercase())
            }
            .sortedByDescending { it.rating }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            TpcRatingList(
                playerFilter = dataState.playerFilter,
                players = players,
                navigateToFilter = navigateToFilter,
                onPlayerFilterChanged = onPlayerFilterChanged,
            )
        }
    }
}

@Composable
fun TpcRatingList(
    playerFilter: String,
    players: List<Player>,
    navigateToFilter: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit
) {
    val lobbyLazyListState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        state = lobbyLazyListState
    ) {
        item {
            SearchPlayerField(
                value = playerFilter,
                onValueChange = onPlayerFilterChanged,
                navigateToFilter = navigateToFilter
            )
        }

        if (players.isEmpty()) {
            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(id = R.string.nothing_found),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        } else {
            var index = 1
            items(
                items = players,
                key = { it.id }
            ) { player ->
                RatingPlayerListItem(
                    index = index++,
                    player = player
                )
            }
        }
    }
}
