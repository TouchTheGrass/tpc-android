package ru.touchthegrass.tpc.data

data class Move(
    val piece: Piece,
    val possibleMoves: List<String> = emptyList()
)
