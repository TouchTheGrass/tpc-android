package ru.touchthegrass.tpc.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.data.Cell
import ru.touchthegrass.tpc.data.Piece
import ru.touchthegrass.tpc.ui.util.getPieceImageId
import ru.touchthegrass.tpc.ui.util.getPieceOffset

@Composable
fun QuadrangleBoard(
    cells: List<Cell>,
    pieces: List<Piece>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        for (cell in cells) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painterResource(cell.imageId),
                colorFilter = ColorFilter.tint(colorResource(cell.colorId)),
                contentDescription = cell.position.toString(),
            )
        }
        for (piece in pieces) {
            Image(
                modifier = Modifier
                    .size(20.dp)
                    .offset {
                        getPieceOffset(piece.position)
                    },
                painter = painterResource(getPieceImageId(piece)),
                contentDescription = "Piece",
            )
        }
    }
}