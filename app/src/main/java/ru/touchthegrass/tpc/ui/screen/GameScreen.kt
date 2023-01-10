package ru.touchthegrass.tpc.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.ui.component.FooterButton
import ru.touchthegrass.tpc.ui.component.QuadrangleBoard
import ru.touchthegrass.tpc.viewmodel.TpcLobbyState
import ru.touchthegrass.tpc.R
import ru.touchthegrass.tpc.data.PlayerStatus
import ru.touchthegrass.tpc.ui.component.PieceVariantItem
import ru.touchthegrass.tpc.ui.component.PositionVariantItem
import ru.touchthegrass.tpc.viewmodel.TpcPlayerState

@Composable
fun GameScreen(
    tpcPlayerState: TpcPlayerState,
    tpcLobbyState: TpcLobbyState
) {
    val cells = tpcLobbyState.cells
    val pieces = tpcLobbyState.pieces
    val positions = tpcLobbyState.positions

    val pieceListState = rememberLazyListState()
    val positionListState = rememberLazyListState()

    val whoseMove = tpcLobbyState.playerGameSessionInfos.firstOrNull() { it.status == PlayerStatus.CURRENT }
    val bottomButtonText = if (whoseMove?.player?.id == tpcPlayerState.currentPlayer!!.id) {
        if (tpcLobbyState.selectedPiece == null) stringResource(R.string.select_piece)
        else if (tpcLobbyState.selectedPosition == null) stringResource(R.string.select_position)
        else stringResource(R.string.confirm_turn)
    } else "${whoseMove?.color!!.title} move"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuadrangleBoard(
            cells = cells,
            pieces = pieces
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            state = pieceListState,
            horizontalArrangement = Arrangement.Center
        ) {
            items(
                items = pieces,
                key = { it.id }
            ) {piece ->
                PieceVariantItem(
                    piece = piece,
                    selected = tpcLobbyState.selectedPiece?.id == piece.id,
                    onItemPressed = {}
                )
            }

        }

        if (tpcLobbyState.selectedPiece != null) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                state = positionListState,
                horizontalArrangement = Arrangement.Center
            ) {
                items(
                    items = positions
                ) { position ->
                    PositionVariantItem(
                        position = position,
                        selected = tpcLobbyState.selectedPosition == position,
                        onItemPressed = {}
                    )
                }

            }
        }

        FooterButton(
            text = bottomButtonText
        ) {

        }
    }
}