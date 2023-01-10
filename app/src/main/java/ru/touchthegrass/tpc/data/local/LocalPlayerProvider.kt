package ru.touchthegrass.tpc.data.local

import ru.touchthegrass.tpc.data.Player
import java.lang.Integer.min
import kotlin.random.Random

object LocalPlayerProvider {

    val allPlayers = (0..20).map { id ->
        Player(
            id = id,
            name = "Player $id",
            rating = Random.nextInt(1, 10000)
        )
    }

    val currentPlayer = allPlayers.first()

    private val usedPlayers = mutableListOf(currentPlayer)

    fun getRandomGroup(): List<Player> {
        val number = min(allPlayers.size - usedPlayers.size, Random.nextInt(1, 3))
        val playerList = allPlayers
            .filterNot { usedPlayers.contains(it) }
            .shuffled()
            .subList(0, number)
        usedPlayers.addAll(playerList)
        return playerList
    }

    fun getPlayerById(playerId: Int): Player {
        return allPlayers.first { it.id == playerId }
    }
}