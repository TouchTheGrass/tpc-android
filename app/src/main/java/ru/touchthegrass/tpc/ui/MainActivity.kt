package ru.touchthegrass.tpc.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.touchthegrass.tpc.ui.model.TpcHomeViewModel
import ru.touchthegrass.tpc.ui.theme.TpcTheme

class MainActivity : ComponentActivity() {

    private val viewModel: TpcHomeViewModel by viewModels()

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TpcTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val playerState by viewModel.playerState.collectAsStateWithLifecycle()
                val dataState by viewModel.dataState.collectAsStateWithLifecycle()
                val lobbyState by viewModel.lobbyState.collectAsStateWithLifecycle()
                val gameState by viewModel.gameState.collectAsStateWithLifecycle()

                TpcApp(
                    uiState = uiState,
                    userState = playerState,
                    dataState = dataState,
                    lobbyState = lobbyState,
                    gameState = gameState,
                    loginUser = { email, password ->
                        viewModel.loginUser(email, password)
                    },
                    registerUser = { email, name, password ->
                        viewModel.registerUser(email, name, password)
                    },
                    navigateToLoginScreen = {
                        viewModel.openLoginScreen()
                    },
                    navigateToRegistrationScreen = {
                        viewModel.openRegisterScreen()
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
                        viewModel.connectLobby(gameSessionId)
                    },
                    onRatingScreenNavigated = {
                        viewModel.updateRatingInfo()
                    },
                    onProfileScreenNavigated = {
                        viewModel.updateUserInfo()
                    },
                    createLobby = {
                        viewModel.createLobby()
                    },
                    logoutUser = {
                        viewModel.logoutUser()
                    },
                    onPieceColorChanged = { pieceColor ->
                        viewModel.changePieceColor(pieceColor)
                    },
                    onReadinessChanged = { isReady ->
                        viewModel.changeReadiness(isReady)
                    },
                    onPieceSelected = { pieceId ->
                        viewModel.selectPiece(pieceId)
                    },
                    onPositionSelected = { position ->
                        viewModel.selectPosition(position)
                    },
                    onConfirmTurnPressed = { pieceId, position ->
                        viewModel.movePiece(pieceId, position)
                    },
                    onConsiderPressed = {
                        viewModel.considerGame()
                    }
                )
            }
        }
    }
}
