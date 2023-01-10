package ru.touchthegrass.tpc.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.data.PlayerGameSession
import ru.touchthegrass.tpc.data.PlayerStatus
import ru.touchthegrass.tpc.R

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
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
