package ru.touchthegrass.tpc.data

import androidx.compose.ui.graphics.vector.ImageVector

enum class GameRuleType {
    BOARD_FORM, VICTORY_CONDITION, TIMER
}

data class GameRule(
    var type: GameRuleType,
    var icon: ImageVector,
    var value: String
)
