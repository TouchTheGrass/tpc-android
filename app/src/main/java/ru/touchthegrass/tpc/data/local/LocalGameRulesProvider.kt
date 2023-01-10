package ru.touchthegrass.tpc.data.local

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import ru.touchthegrass.tpc.data.GameRule
import ru.touchthegrass.tpc.data.GameRuleType

object LocalGameRulesProvider {

    val standartRules = listOf(
        GameRule(
            type = GameRuleType.BOARD_FORM,
            icon = Icons.Default.Square,
            value = "Quadrangle"
        ),
        GameRule(
            type = GameRuleType.VICTORY_CONDITION,
            icon = Icons.Default.FirstPage,
            value = "First checkmate"
        ),
        GameRule(
            type = GameRuleType.TIMER,
            icon = Icons.Default.TimerOff,
            value = "No timer"
        )
    )
}