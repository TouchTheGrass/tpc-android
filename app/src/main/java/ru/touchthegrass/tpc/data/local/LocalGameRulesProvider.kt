package ru.touchthegrass.tpc.data.local

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import ru.touchthegrass.tpc.data.GameRule
import ru.touchthegrass.tpc.data.GameRuleType

object LocalGameRulesProvider {

    val boardFormRules = listOf(
        GameRule(
            type = GameRuleType.BOARD_FORM,
            icon = Icons.Default.ChangeHistory,
            value = "Triangle"
        ),
        GameRule(
            type = GameRuleType.BOARD_FORM,
            icon = Icons.Default.Square,
            value = "Quadrangle"
        ),GameRule(
            type = GameRuleType.BOARD_FORM,
            icon = Icons.Default.Hexagon,
            value = "Hexagonal"
        )
    )

    val victoryConditionRules = listOf(
        GameRule(
            type = GameRuleType.VICTORY_CONDITION,
            icon = Icons.Default.FirstPage,
            value = "First checkmate"
        ),
        GameRule(
            type = GameRuleType.VICTORY_CONDITION,
            icon = Icons.Default.LastPage,
            value = "Last checkmate"
        ),
    )

    val timerRules = listOf(
        GameRule(
            type = GameRuleType.TIMER,
            icon = Icons.Default.TimerOff,
            value = "No timer"
        ),
        GameRule(
            type = GameRuleType.TIMER,
            icon = Icons.Default.Timer,
            value = "Timer"
        )
    )

    fun getRandomRules(): List<GameRule> = listOf(
        boardFormRules.random(),
        victoryConditionRules.random(),
        timerRules.random()
    )
}