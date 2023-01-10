package ru.touchthegrass.tpc.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.R
import ru.touchthegrass.tpc.data.*
import ru.touchthegrass.tpc.ui.component.*

@Composable
fun TpcLobbyScreen(
    currentPlayer: Player,
    playerGameSessionInfos: List<PlayerGameSession>,
    closeLobbyScreen: () -> Unit,
    onPieceColorChanged: (PieceColor) -> Unit,
    onReadinessChanged: (Boolean) -> Unit,
    onDrawerPressed: () -> Unit
) {

    val isReady = playerGameSessionInfos
        .find { it.player.id == currentPlayer.id }
        ?.status == PlayerStatus.READY

    BackHandler {
        closeLobbyScreen()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TpcLobbyAppBar(
            playerInfos = playerGameSessionInfos,
            onBackPressed = closeLobbyScreen,
            onDrawerPressed = onDrawerPressed
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(0.7f)
            ) {
                for (playerInfo in playerGameSessionInfos) {
                    LobbyPlayerListItem(playerInfo)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (color in PieceColor.values()) {
                    val colorOwner = playerGameSessionInfos.find { it.color == color }
                    PieceColorVariantItem(
                        color = color,
                        status = if (colorOwner != null) {
                            if (colorOwner.player.id == currentPlayer.id) PieceColorStatus.SELECTED
                            else PieceColorStatus.DISABLED
                        } else PieceColorStatus.ENABLED,
                        onPieceColorChanged = onPieceColorChanged
                    )
                }
            }
        }

        FooterButton(
            text = if (isReady) stringResource(R.string.cancel_readiness) else stringResource(R.string.confirm_readiness),
        ) {
            onReadinessChanged(!isReady)
        }
    }
}
