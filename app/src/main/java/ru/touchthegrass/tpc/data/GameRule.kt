package ru.touchthegrass.tpc.data

import androidx.compose.ui.graphics.vector.ImageVector

enum class GameRuleType {
    BOARD_FORM, VICTORY_CONDITION, TIMER
}

data class GameRule(
    val type: GameRuleType,
    val icon: ImageVector,
    val value: String
)
