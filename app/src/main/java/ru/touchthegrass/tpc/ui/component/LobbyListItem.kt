package ru.touchthegrass.tpc.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import ru.touchthegrass.tpc.R
import ru.touchthegrass.tpc.data.Lobby

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyListItem(
    lobby: Lobby,
    navigateToLobby: (Long) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clickable { navigateToLobby(lobby.id) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .background(MaterialTheme.colorScheme.surface),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${lobby.players.size}/3",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = lobby.players.joinToString { it.name },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                FlowRow(
                    modifier = Modifier.padding(top = 4.dp),
                    crossAxisSpacing = (-8).dp,
                    mainAxisSpacing = 8.dp
                ) {
                    lobby.rules.forEach { rule ->
                        GameRuleChip(
                            icon = rule.icon,
                            title = rule.value
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.width(32.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(AssistChipDefaults.IconSize),
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = stringResource(R.string.join_lobby),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
