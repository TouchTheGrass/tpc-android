package ru.touchthegrass.tpc.data.local

import ru.touchthegrass.tpc.data.Player
import java.lang.Integer.min
import kotlin.random.Random

object LocalPlayerProvider : LocalProvider() {

    val allPlayers = (1..21)
        .map {
            val id = getId()
            Player(
                id = id,
                name = "Player $id",
                email = "player$id@example.com",
                rating = 0
            )
        }
        .toMutableList()

    private val usedPlayers = mutableListOf(allPlayers.first())

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

    fun createPlayer(email: String, name: String, password: String): Player {
        val id = getId()
        val player = Player(
            id = id,
            name = name,
            email = email,
            password = password,
            rating = 0
        )
        allPlayers.add(player)
        return player
    }
}