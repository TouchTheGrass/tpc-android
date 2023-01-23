package ru.touchthegrass.tpc.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import ru.touchthegrass.tpc.R
import ru.touchthegrass.tpc.data.*
import ru.touchthegrass.tpc.ui.navigation.TpcTopLevelDestination
import ru.touchthegrass.tpc.ui.util.getPieceImageId
import kotlin.math.abs

enum class PieceColorStatus {
    ENABLED, DISABLED, SELECTED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyListItem(
    lobby: Lobby,
    navigateOnLobbyScreen: (Int) -> Unit
) {
    val gameSession = lobby.gameSession
    val players = lobby.playerInfos.map { it.player }
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clickable { navigateOnLobbyScreen(gameSession.id) },
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
                    text = "${players.size}/3",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = players.joinToString { it.name },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                FlowRow(
                    modifier = Modifier.padding(top = 4.dp),
                    crossAxisSpacing = (-8).dp,
                    mainAxisSpacing = 8.dp
                ) {
                    gameSession.rules.forEach { rule ->
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

@Composable
fun RatingPlayerListItem(
    index: Int,
    player: Player,
) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp),
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(end = 16.dp),
                    text = "#$index",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                text = player.rating.toString(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun ProfileHistoryListItem(
    currentPlayer: Player,
    lobby: Lobby
) {
    val gameSession = lobby.gameSession
    val players = lobby.playerInfos.map { it.player }
    val currentPlayerInfo = lobby.playerInfos.find { it.player.id == currentPlayer.id }!!
    val pointsDiff = "${if (currentPlayerInfo.scores!! >= 0) '+' else '-'} ${abs(currentPlayerInfo.scores!!)}"
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp),
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
                    text = players.joinToString { it.name },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = pointsDiff,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (currentPlayerInfo.isWinner == true) colorResource(R.color.green0)
                    else colorResource(R.color.crimson)
                )
                FlowRow(
                    modifier = Modifier.padding(top = 4.dp),
                    crossAxisSpacing = (-8).dp,
                    mainAxisSpacing = 8.dp
                ) {
                    gameSession.rules.forEach { rule ->
                        GameRuleChip(
                            icon = rule.icon,
                            title = rule.value
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerItem(
    selectedDestination: String,
    tpcDestination: TpcTopLevelDestination,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        selected = selectedDestination == tpcDestination.route,
        label = {
            Text(
                text = stringResource(tpcDestination.iconTextId),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        },
        icon = {
            Icon(
                imageVector = tpcDestination.selectedIcon,
                contentDescription = stringResource(tpcDestination.iconTextId)
            )
        },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent
        ),
        onClick = onClick
    )
}

@Composable
fun SwitchListItem(
    icon: ImageVector,
    text: String,
    checked: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.padding(start = 16.dp),
            tint = MaterialTheme.colorScheme.outline
        )
        Text(
            text = text,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            style = MaterialTheme.typography.bodyMedium,
        )
        Switch(
            checked = checked,
            onCheckedChange = { },
            enabled = false
        )
    }
}

@Composable
fun LobbyPlayerListItem(
    playerGameSession: PlayerGameSession
) {
    val player = playerGameSession.player
    val statusColorId: Int
    val statusTextId: Int

    when (playerGameSession.status) {
        PlayerStatus.NOT_READY -> {
            statusColorId = R.color.gray4
            statusTextId = R.string.not_ready
        }

        PlayerStatus.READY -> {
            statusColorId = R.color.green0
            statusTextId = R.string.ready
        }

        else -> error("Illegal player status")
    }

    Card(
        modifier = Modifier
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.weight(0.7f)
            ) {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingChip(player.rating)
                    playerGameSession.color?.let {
                        PieceColorChip(it)
                    }
                }
            }
            Row(
                modifier = Modifier.weight(0.3f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(statusTextId),
                    textAlign = TextAlign.End,
                    color = colorResource(statusColorId),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun PieceColorVariantItem(
    modifier: Modifier = Modifier,
    color: PieceColor,
    status: PieceColorStatus,
    onPieceColorChanged: (PieceColor) -> Unit
) {
    val borderColor = when (status) {
        PieceColorStatus.ENABLED -> MaterialTheme.colorScheme.primary
        PieceColorStatus.DISABLED -> MaterialTheme.colorScheme.outline
        PieceColorStatus.SELECTED -> colorResource(R.color.green0)
    }
    Card(
        modifier = modifier
            .clickable { onPieceColorChanged(color) },
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(5.dp, borderColor),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                modifier = Modifier
                    .size(80.dp)
                    .padding(bottom = 8.dp),
                painter = painterResource(id = R.drawable.king),
                contentDescription = color.title,
                tint = colorResource(
                    when (color) {
                        PieceColor.WHITE -> R.color.white
                        PieceColor.BLACK -> R.color.black
                        PieceColor.RED -> R.color.crimson
                    }
                )
            )

            Text(
                text = color.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun PieceVariantItem(
    piece: Piece,
    selected: Boolean,
    onItemPressed: (Boolean) -> Unit = {}
) {
    val borderColor = if (selected) colorResource(R.color.green0) else MaterialTheme.colorScheme.primary

    Card(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clickable { onItemPressed },
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(5.dp, borderColor),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier
                    .size(80.dp)
                    .padding(bottom = 8.dp),
                painter = painterResource(getPieceImageId(piece)),
                contentDescription = "Piece"
            )
            Text(
                text = piece.position.uppercase(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun PositionVariantItem(
    position: String,
    selected: Boolean,
    onItemPressed: (Boolean) -> Unit = {}
) {
    val borderColor = if (selected) colorResource(R.color.green0) else MaterialTheme.colorScheme.primary

    Card(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clickable { onItemPressed(selected) },
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(5.dp, borderColor),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = position.uppercase(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}


