package ru.touchthegrass.tpc.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.R
import ru.touchthegrass.tpc.data.Cell
import ru.touchthegrass.tpc.data.Piece
import ru.touchthegrass.tpc.data.PieceColor
import ru.touchthegrass.tpc.data.PieceType
import ru.touchthegrass.tpc.data.local.LocalCellsProvider
import ru.touchthegrass.tpc.ui.models.CellModel
import ru.touchthegrass.tpc.ui.util.getPieceImageId
import ru.touchthegrass.tpc.ui.util.getPieceOffset

@Composable
fun QuadrangleBoard(
    cells: List<CellModel>,
    pieces: List<Piece>,
    positions: List<String>,
    selectedPiece: Piece?,
    selectedPosition: String?
) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {

        Canvas(modifier = Modifier.fillMaxWidth()) {
            CellView(cells)
        }

        for (piece in pieces) {
            Icon(
                contentDescription = "${piece.color.name} ${piece.type.name}",
                painter = painterResource(
                    when (piece.type) {
                        PieceType.PAWN -> R.drawable.pawn
                        PieceType.ROOK -> R.drawable.rook
                        PieceType.KNIGHT -> R.drawable.knight
                        PieceType.BISHOP -> R.drawable.bishop
                        PieceType.QUEEN -> R.drawable.queen
                        PieceType.KING -> R.drawable.king
                    }
                ),
                tint = colorResource(
                    when (piece.color) {
                        PieceColor.WHITE -> R.color.white
                        PieceColor.BLACK -> R.color.black
                        PieceColor.RED -> R.color.crimson
                    }
                )
            )
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
        for (position in positions) {
            Icon(
                modifier = Modifier
                    .size(14.dp)
                    .offset {
                        getPieceOffset(position)
                    },
                imageVector = Icons.Default.Circle,
                contentDescription = "Position",
                tint = if (position == selectedPosition) colorResource(R.color.green1)
                else MaterialTheme.colorScheme.outline
            )
        }
    }
}

fun DrawScope.CellView(cells: List<CellModel>) {
    cells.forEachIndexed { index, cell ->
        withTransform({
            scale(cell.scale, cell.scale)
        }) {

        }
    }
}
