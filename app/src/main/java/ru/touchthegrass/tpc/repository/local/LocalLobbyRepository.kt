package ru.touchthegrass.tpc.repository.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.touchthegrass.tpc.data.Lobby
import ru.touchthegrass.tpc.data.local.LocalLobbyProvider
import ru.touchthegrass.tpc.repository.LobbyRepository

class LocalLobbyRepository: LobbyRepository {
    override fun getAllLobbies(): Flow<List<Lobby>> = flow {
        emit(LocalLobbyProvider.allLobbies)
    }

    override fun getLobbyById(id: Long): Flow<Lobby> = flow {
        LocalLobbyProvider.getById(id)
    }
}