package ru.touchthegrass.tpc.data

enum class GameSessionStatus {
    WAIT, GAME, COMPLETED
}

data class GameSession(
    var id: Int,
    var status: GameSessionStatus,
    var rules: List<GameRule>
)
