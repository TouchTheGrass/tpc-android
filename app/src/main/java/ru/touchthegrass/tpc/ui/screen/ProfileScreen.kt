package ru.touchthegrass.tpc.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.data.*
import ru.touchthegrass.tpc.ui.component.ProfileHistoryListItem
import ru.touchthegrass.tpc.ui.component.SearchPlayerField
import ru.touchthegrass.tpc.model.TpcFilterState
import ru.touchthegrass.tpc.model.TpcPlayerState

@Composable
fun TpcProfileScreen(
    tpcPlayerState: TpcPlayerState,
    tpcFilterState: TpcFilterState,
    navigateToFilter: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit
) {
    val profileHistoryLazyState = rememberLazyListState()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            state = profileHistoryLazyState
        ) {
            val player = tpcPlayerState.currentPlayer!!

            item {
                ProfileHeader(
                    player = player,
                    playerHistory = tpcPlayerState.currentPlayerHistory
                )
            }

            item {
                SearchPlayerField(
                    value = tpcFilterState.playerFilter,
                    onValueChange = onPlayerFilterChanged,
                    navigateToFilter = navigateToFilter
                )
            }

            items(
                items = tpcPlayerState.currentPlayerHistory.filter { lobby ->
                    lobby.playerInfos.any { playerInfo ->
                        playerInfo.player.name.lowercase().contains(tpcFilterState.playerFilter.lowercase())
                    }
                },
                key = { it.gameSession.id }
            ) { lobby ->
                ProfileHistoryListItem(
                    currentPlayer = player,
                    lobby = lobby
                )
            }
        }
    }
}

@Composable
fun ProfileHeader(
    player: Player,
    playerHistory: List<Lobby>
) {
    Card(
        modifier = Modifier
            .padding(top = 24.dp, bottom = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 40.dp, end = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val name = player.name
            val avatarImageId = player.avatarImageId

            Image(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                painter = painterResource(avatarImageId),
                contentDescription = "$name avatar"
            )
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = name,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val rating = player.rating
                val wins = playerHistory.count { it.playerInfos.first { it.player.id == player.id }.isWinner!! }
                val loses = playerHistory.size - wins

                ProfileStatItem(
                    modifier = Modifier.weight(1f),
                    topText = "$rating",
                    bottomText = "Rating"
                )
                ProfileStatItem(
                    modifier = Modifier.weight(1f),
                    topText = "$wins",
                    bottomText = "Wins",
                )
                ProfileStatItem(
                    modifier = Modifier.weight(1f),
                    topText = "$loses",
                    bottomText = "Loses",
                )
            }
        }
    }
}

@Composable
fun ProfileStatItem(
    modifier: Modifier,
    topText: String,
    bottomText: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = topText,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = bottomText,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleSmall
        )
    }
}