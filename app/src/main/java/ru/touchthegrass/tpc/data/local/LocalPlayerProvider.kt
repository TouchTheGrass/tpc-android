package ru.touchthegrass.tpc.data.local

import ru.touchthegrass.tpc.data.Player
import java.lang.Integer.min
import kotlin.random.Random

object LocalPlayerProvider : LocalProvider() {

    val allPlayers = (1..21).map {
        val id = getId()
        Player(
            id = id,
            name = "Player $id",
            rating = Random.nextInt(1, 10000)
        )
    }

    val currentPlayer = allPlayers.first()

    private val usedPlayers = mutableListOf(currentPlayer)

    fun getRandomGroup(
        min: Int,
        max: Int,
        excluding: Boolean = false
    ): List<Player> {
        val availablePlayers = if (excluding) allPlayers.filterNot { usedPlayers.contains(it) } else allPlayers
        val number = min(availablePlayers.size, Random.nextInt(min, max + 1))
        val group = availablePlayers.shuffled()
            .subList(0, number)
        if (excluding) {
            usedPlayers.addAll(group)
        }
        return group
    }

    fun getPlayerById(playerId: Int): Player {
        return allPlayers.first { it.id == playerId }
    }
}