package ru.touchthegrass.tpc.data

data class Lobby(
    val id: Long,
    val players: List<Player>,
    val rules: List<GameRule>
)
