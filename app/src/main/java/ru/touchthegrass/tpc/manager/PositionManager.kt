package ru.touchthegrass.tpc.manager

interface PositionManager {

    fun getPossibleMoves(pieceId: Int): List<String>
}