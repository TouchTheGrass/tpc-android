package ru.touchthegrass.tpc.data

data class Lobby(
    var gameSession: GameSession,
    var playerInfos: MutableList<PlayerGameSession> = mutableListOf()
)
