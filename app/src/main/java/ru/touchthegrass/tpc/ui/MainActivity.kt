package ru.touchthegrass.tpc.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.touchthegrass.tpc.ui.theme.TpcTheme
import ru.touchthegrass.tpc.model.TpcHomeViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: TpcHomeViewModel by viewModels()

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TpcTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val playerState by viewModel.playerState.collectAsStateWithLifecycle()
                val filterState by viewModel.filterState.collectAsStateWithLifecycle()
                val lobbyState by viewModel.lobbyState.collectAsStateWithLifecycle()

                TpcApp(
                    tpcHomeUIState = uiState,
                    tpcPlayerState = playerState,
                    tpcFilterState = filterState,
                    tpcLobbyState = lobbyState,
                    loginUser = { email, password ->
                        viewModel.loginUser(email, password)
                    },
                    navigateToRegistrationScreen = {

                    },
                    closeFilterScreen = {
                        viewModel.closeFilterScreen()
                    },
                    navigateToFilterScreen = {
                        viewModel.openFilterScreen()
                    },
                    onPlayerFilterChanged = { newValue ->
                        viewModel.changeSearchPlayerFilter(newValue)
                    },
                    closeLobbyScreen = {
                        viewModel.closeLobbyScreen()
                    },
                    navigateOnLobbyScreen = { gameSessionId ->
                        viewModel.setCurrentLobby(gameSessionId)
                    },
                    createLobby = {
                        viewModel.createLobby()
                    },
                    onPieceColorChanged = { pieceColor ->
                        viewModel.setPieceColor(pieceColor)
                    },
                    onReadinessChanged = { isReady ->
                        viewModel.changeReadiness(isReady)
                    },
                    onConfirmTurnPressed = {
                        viewModel.movePiece()
                    }
                )
            }
        }
    }
}
