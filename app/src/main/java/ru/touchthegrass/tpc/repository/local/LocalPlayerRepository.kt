package ru.touchthegrass.tpc.repository.local

import ru.touchthegrass.tpc.data.Player
import ru.touchthegrass.tpc.data.local.LocalPlayerProvider
import ru.touchthegrass.tpc.repository.PlayerRepository

class LocalPlayerRepository : PlayerRepository {

    override fun getAllPlayers(): List<Player> {
        return LocalPlayerProvider.allPlayers
    }

    override fun getPlayerById(id: Int): Player {
        return LocalPlayerProvider.getPlayerById(id)
    }

    override fun getPlayerByCredential(email: String, password: String): Player? {
        return LocalPlayerProvider.allPlayers
            .find {
                it.email == email && it.password == password
            }
    }

    override fun createPlayer(email: String, name: String, password: String): Player {
        return LocalPlayerProvider.createPlayer(email, name, password)
    }
}
