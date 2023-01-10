package ru.touchthegrass.tpc.data

enum class PlayerStatus {
    NOT_READY, READY, WAIT, CURRENT, ELIMINATED, DISCONNECTED
}
enum class PieceColor(val title: String) {
    WHITE("White"),
    BLACK("Black"),
    RED("Red")
}

data class PlayerGameSession(
    var player: Player,
    var gameSession: GameSession,
    var status: PlayerStatus = PlayerStatus.NOT_READY,
    var isActive: Boolean = true,
    var color: PieceColor? = null
)
