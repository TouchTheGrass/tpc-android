package ru.touchthegrass.tpc.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.R
import ru.touchthegrass.tpc.data.PlayerStatus
import ru.touchthegrass.tpc.state.TpcGameState
import ru.touchthegrass.tpc.state.TpcLobbyState
import ru.touchthegrass.tpc.state.TpcUserState
import ru.touchthegrass.tpc.ui.component.*

@Composable
fun GameScreen(
    userState: TpcUserState,
    lobbyState: TpcLobbyState,
    gameState: TpcGameState,
    onPieceSelected: (Int?) -> Unit,
    onPositionSelected: (String?) -> Unit,
    onConfirmTurnPressed: (Int, String) -> Unit,
    onConsiderPressed: () -> Unit
) {
    val userColor = lobbyState.playerGameSessionInfos
        .first { it.player.id == userState.currentPlayer!!.id }
        .color

    val openConsiderDialog = remember { mutableStateOf(false) }
    val pieceListState = rememberLazyListState()
    val positionListState = rememberLazyListState()

    if (openConsiderDialog.value) {
        ConsiderDialog(
            openDialog = openConsiderDialog,
            onConsiderPressed = onConsiderPressed
        )
    }

    BackHandler {
        openConsiderDialog.value = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuadrangleBoard(
            modifier = Modifier
                .weight(11f),
            pieces = gameState.pieces,
            positions = gameState.positions
        )

        Column(
            modifier = Modifier
                .weight(6f)
                .padding(vertical = 10.dp),
            verticalArrangement = Arrangement.Top
        ) {

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                state = pieceListState,
                horizontalArrangement = Arrangement.Center
            ) {
                items(
                    items = gameState.pieces.filter { it.info.color == userColor },
                    key = { it.info.id }
                ) { piece ->
                    val pieceInfo = piece.info
                    PieceVariantItem(
                        piece = pieceInfo,
                        selected = gameState.selectedPieceId == pieceInfo.id,
                        onItemPressed = { isSelected ->
                            onPieceSelected(if (isSelected) null else pieceInfo.id)
                        }
                    )
                }

            }

            if (gameState.selectedPieceId != null) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    state = positionListState,
                    horizontalArrangement = Arrangement.Center
                ) {
                    items(
                        items = gameState.positions,
                        key = { it.str }
                    ) { position ->
                        val positionStr = position.str
                        PositionVariantItem(
                            position = positionStr,
                            selected = gameState.selectedPosition == positionStr,
                            onItemPressed = { isSelected ->
                                onPositionSelected(if (isSelected) null else positionStr)
                            }
                        )
                    }
                }
            }
        }

        val whoseMove = lobbyState.playerGameSessionInfos.firstOrNull { it.status == PlayerStatus.CURRENT_TURN }
        BottomButtonBar(
            text = if (whoseMove?.player?.id == userState.currentPlayer?.id) {
                if (gameState.selectedPieceId == null) stringResource(R.string.select_piece)
                else if (gameState.selectedPosition == null) stringResource(R.string.select_position)
                else stringResource(R.string.confirm_turn)
            } else "${whoseMove?.color?.title} move",
            onClick = {
                if (gameState.selectedPieceId == null) return@BottomButtonBar
                if (gameState.selectedPosition == null) return@BottomButtonBar
                onConfirmTurnPressed(gameState.selectedPieceId, gameState.selectedPosition)
            }
        )
    }
}
