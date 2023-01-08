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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.R
import ru.touchthegrass.tpc.data.Lobby
import ru.touchthegrass.tpc.viewmodel.TpcHomeUIState
import ru.touchthegrass.tpc.ui.component.LobbyListItem
import ru.touchthegrass.tpc.ui.component.TpcSearchPlayerBar
import ru.touchthegrass.tpc.viewmodel.TpcFilterState

@Composable
fun TpcLobbiesScreen(
    tpcHomeUIState: TpcHomeUIState,
    tpcFilterState: TpcFilterState,
    closeFilterScreen: () -> Unit = {},
    navigateToFilter: () -> Unit = {},
    onPlayerFilterChanged: (String) -> Unit = {}
) {
    if (tpcHomeUIState.openedFilter) {
        BackHandler {
            closeFilterScreen()
        }
        TpcFilterScreen(closeFilterScreen)
    } else {
        val lobbies = tpcHomeUIState.lobbies
            .filter { lobby ->
                lobby.players.any { player ->
                    player.name.contains(tpcFilterState.playerFilter)
                }
            }
        TpcLobbyList(
            playerFilter = tpcFilterState.playerFilter,
            lobbies = lobbies,
            navigateToFilter = navigateToFilter,
            onPlayerFilterChanged = onPlayerFilterChanged
        )
    }
}

@Composable
fun TpcLobbyList(
    playerFilter: String,
    lobbies: List<Lobby>,
    navigateToFilter: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit
) {
    val lobbyLazyListState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        state = lobbyLazyListState
    ) {

        item {
            TpcSearchPlayerBar(
                value = playerFilter,
                onValueChange = onPlayerFilterChanged,
                navigateToFilter = navigateToFilter
            )
        }

        if (lobbies.isEmpty()) {
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
            items(
                items = lobbies,
                key = { it.id }
            ) { lobby ->
                LobbyListItem(
                    lobby = lobby
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTpcLobbiesScreen() {
    TpcLobbiesScreen(
        tpcHomeUIState = TpcHomeUIState(),
        tpcFilterState = TpcFilterState()
    )
}
