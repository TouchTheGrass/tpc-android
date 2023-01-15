package ru.touchthegrass.tpc.manager.local

import ru.touchthegrass.tpc.data.Player
import ru.touchthegrass.tpc.manager.UserManager
import ru.touchthegrass.tpc.repository.PlayerRepository

class LocalUserManager(override val playerRepository: PlayerRepository): UserManager {

    override var currentPlayer: Player? = null

    override fun loginUser(email: String, password: String): Player? {
        return playerRepository.getPlayerByCredential(email, password)
    }

    override fun logoutUser() {
        currentPlayer = null
    }
}