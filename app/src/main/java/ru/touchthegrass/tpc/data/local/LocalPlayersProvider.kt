package ru.touchthegrass.tpc.data.local

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Square
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.touchthegrass.tpc.data.GameRule
import ru.touchthegrass.tpc.data.GameRuleType
import ru.touchthegrass.tpc.data.Player
import kotlin.random.Random

object LocalPlayersProvider {

    val allPlayers = listOf(
        Player(
            id = 1,
            name = "Player 1",
            rating = 123
        ),
        Player(
            id = 2,
            name = "Player 2",
            rating = 321
        ),
        Player(
            id = 3,
            name = "Player 3",
            rating = 312
        ),
        Player(
            id = 4,
            name = "Player 4",
            rating = 543
        ),
        Player(
            id = 5,
            name = "Player 5",
            rating = 652
        ),
        Player(
            id = 6,
            name = "Player 6",
            rating = 423
        ),
        Player(
            id = 7,
            name = "Player 7",
            rating = 246
        ),
        Player(
            id = 8,
            name = "Player 8",
            rating = 434
        ),
        Player(
            id = 9,
            name = "Player 9",
            rating = 542
        ),
        Player(
            id = 10,
            name = "Player 10",
            rating = 744
        ),
        Player(
            id = 11,
            name = "Player 11",
            rating = 712
        ),
        Player(
            id = 12,
            name = "Player 12",
            rating = 413
        )
    )

    fun getRandomGroup(): List<Player> {
        val number = Random.nextInt(1, 4)
        return allPlayers.shuffled().subList(0, number)
    }
}