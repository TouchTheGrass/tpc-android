package ru.touchthegrass.tpc.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.R
import ru.touchthegrass.tpc.data.Lobby
import ru.touchthegrass.tpc.state.TpcDataState
import ru.touchthegrass.tpc.ui.component.LobbyListItem
import ru.touchthegrass.tpc.ui.component.SearchPlayerField
import ru.touchthegrass.tpc.state.TpcUIState

@Composable
fun TpcLobbiesScreen(
    uiState: TpcUIState,
    dataState: TpcDataState,
    closeFilterScreen: () -> Unit,
    navigateToFilter: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit,
    navigateOnLobbyScreen: (Int) -> Unit,
    createLobby: () -> Unit
) {
    if (uiState.openedFilter) {
        BackHandler {
            closeFilterScreen()
        }
        TpcFilterScreen(closeFilterScreen)
    } else {
        val lobbies = dataState.lobbies
            .filter { lobby ->
                lobby.playerInfos.any { playerInfo ->
                    playerInfo.player.name.lowercase().contains(dataState.playerFilter.lowercase())
                }
            }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            TpcLobbyList(
                playerFilter = dataState.playerFilter,
                lobbies = lobbies,
                navigateToFilter = navigateToFilter,
                onPlayerFilterChanged = onPlayerFilterChanged,
                navigateOnLobbyScreen = navigateOnLobbyScreen
            )

            LargeFloatingActionButton(
                onClick = createLobby,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.create_lobby),
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun TpcLobbyList(
    playerFilter: String,
    lobbies: List<Lobby>,
    navigateToFilter: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit,
    navigateOnLobbyScreen: (Int) -> Unit
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
                key = { it.gameSession.id }
            ) { lobby ->
                LobbyListItem(
                    lobby = lobby,
                    navigateOnLobbyScreen = navigateOnLobbyScreen
                )
            }
        }
    }
}
