package ru.touchthegrass.tpc.data

enum class PlayerStatus {
    NOT_READY, READY, WAIT_TURN, CURRENT_TURN, DONE, DISCONNECTED
}

data class PlayerGameSession(
    var player: Player,
    var gameSession: GameSession,
    var status: PlayerStatus = PlayerStatus.NOT_READY,
    var color: PieceColor? = null,
    var isWinner: Boolean? = null,
    var scores: Int? = null
)
