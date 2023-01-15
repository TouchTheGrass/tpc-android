package ru.touchthegrass.tpc.repository.local

import junit.framework.TestCase.assertEquals
import org.junit.Test

import ru.touchthegrass.tpc.repository.local.LocalLobbyRepository

class LocalLobbyRepositoryTest {

    val repository = LocalLobbyRepository()

    @Test
    fun provideLobbiesTest() {
        assertEquals(100, repository.completedLobbies.size,)
        assertEquals(10, repository.activeLobbies)
    }
}