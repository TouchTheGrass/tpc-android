package ru.touchthegrass.tpc.data.local

import ru.touchthegrass.tpc.data.Lobby

object LocalLobbyProvider {

    val allLobbies = listOf(

        Lobby(
            id = 1,
            players = LocalPlayersProvider.getRandomGroup(),
            rules = LocalGameRulesProvider.getRandomRules()
        ),
        Lobby(
            id = 2,
            players = LocalPlayersProvider.getRandomGroup(),
            rules = LocalGameRulesProvider.getRandomRules()
        ),
        Lobby(
            id = 3,
            players = LocalPlayersProvider.getRandomGroup(),
            rules = LocalGameRulesProvider.getRandomRules()
        ),
        Lobby(
            id = 4,
            players = LocalPlayersProvider.getRandomGroup(),
            rules = LocalGameRulesProvider.getRandomRules()
        ),
        Lobby(
            id = 5,
            players = LocalPlayersProvider.getRandomGroup(),
            rules = LocalGameRulesProvider.getRandomRules()
        ),
        Lobby(
            id = 6,
            players = LocalPlayersProvider.getRandomGroup(),
            rules = LocalGameRulesProvider.getRandomRules()
        )
    )

    fun getById(id: Long): Lobby? = allLobbies.firstOrNull { it.id == id }
}
