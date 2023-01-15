package ru.touchthegrass.tpc.manager

import ru.touchthegrass.tpc.data.Player
import ru.touchthegrass.tpc.repository.PlayerRepository

interface UserManager {

    val playerRepository: PlayerRepository

    val currentPlayer: Player?

    fun loginUser(email: String, password: String): Player?

    fun registerUser(email: String, name: String, password: String): Player

    fun logoutUser()
}