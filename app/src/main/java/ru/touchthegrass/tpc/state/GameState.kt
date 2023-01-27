package ru.touchthegrass.tpc.state

import ru.touchthegrass.tpc.ui.drawable.DrawablePiece
import ru.touchthegrass.tpc.ui.drawable.DrawablePosition

data class TpcGameState(
    val pieces: List<DrawablePiece> = emptyList(),
    val positions: List<DrawablePosition> = emptyList(),
    val selectedPieceId: Int? = null,
    val selectedPosition: String? = null
)
