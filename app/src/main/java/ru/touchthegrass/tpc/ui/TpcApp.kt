package ru.touchthegrass.tpc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ru.touchthegrass.tpc.data.GameSessionStatus
import ru.touchthegrass.tpc.data.PieceColor
import ru.touchthegrass.tpc.state.*
import ru.touchthegrass.tpc.ui.component.BottomNavigationBar
import ru.touchthegrass.tpc.ui.component.LobbyDrawer
import ru.touchthegrass.tpc.ui.component.NavigationDrawer
import ru.touchthegrass.tpc.ui.navigation.*
import ru.touchthegrass.tpc.ui.screen.*
import ru.touchthegrass.tpc.ui.util.*

@Composable
fun TpcApp(
    uiState: TpcUIState,
    userState: TpcUserState,
    dataState: TpcDataState,
    lobbyState: TpcLobbyState,
    gameState: TpcGameState,
    loginUser: (String, String) -> Unit,
    registerUser: (String, String, String) -> Unit,
    navigateToLoginScreen: () -> Unit,
    navigateToRegistrationScreen: () -> Unit,
    closeFilterScreen: () -> Unit,
    navigateToFilterScreen: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit,
    closeLobbyScreen: () -> Unit,
    navigateOnLobbyScreen: (Int) -> Unit,
    onRatingScreenNavigated: () -> Unit,
    onProfileScreenNavigated: () -> Unit,
    createLobby: () -> Unit,
    logoutUser: () -> Unit,
    onPieceColorChanged: (PieceColor) -> Unit,
    onReadinessChanged: (Boolean) -> Unit,
    onPieceSelected: (Int?) -> Unit,
    onPositionSelected: (String?) -> Unit,
    onConfirmTurnPressed: (Int, String) -> Unit,
    onConsiderPressed: () -> Unit
) {

    if (userState.currentPlayer == null) {
        if (uiState.openedRegister) {
            RegisterScreen(
                onLoginPressed = navigateToLoginScreen,
                onRegisterPressed = registerUser
            )
        } else {
            LoginScreen(
                onLoginPressed = loginUser,
                onRegisterPressed = navigateToRegistrationScreen
            )
        }
    } else if (uiState.openedLobby) {

        if (lobbyState.gameSession!!.status == GameSessionStatus.GAME) {
            GameScreen(
                userState = userState,
                lobbyState = lobbyState,
                gameState = gameState,
                onPieceSelected = onPieceSelected,
                onPositionSelected = onPositionSelected,
                onConfirmTurnPressed = onConfirmTurnPressed,
                onConsiderPressed = onConsiderPressed
            )
        } else {
            TpcLobbyWrapper(
                tpcUserState = userState,
                tpcLobbyState = lobbyState,
                closeLobbyScreen = closeLobbyScreen,
                onPieceColorChanged = onPieceColorChanged,
                onReadinessChanged = onReadinessChanged
            )
        }
    } else {
        TpcNavigationWrapper(
            uiState = uiState,
            userState = userState,
            dataState = dataState,
            closeFilterScreen = closeFilterScreen,
            navigateToFilterScreen = navigateToFilterScreen,
            onPlayerFilterChanged = onPlayerFilterChanged,
            navigateOnLobbyScreen = navigateOnLobbyScreen,
            onRatingScreenNavigated = onRatingScreenNavigated,
            onProfileScreenNavigated = onProfileScreenNavigated,
            createLobby = createLobby,
            logoutUser = logoutUser
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TpcLobbyWrapper(
    tpcUserState: TpcUserState,
    tpcLobbyState: TpcLobbyState,
    closeLobbyScreen: () -> Unit,
    onPieceColorChanged: (PieceColor) -> Unit,
    onReadinessChanged: (Boolean) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            LobbyDrawer(
                rules = tpcLobbyState.gameSession?.rules ?: error("Current lobby is nullable"),
                closeDrawer = {
                    scope.launch {
                        drawerState.close()
                    }
                }
            )
        }
    ) {
        TpcLobbyContent(
            tpcUserState = tpcUserState,
            tpcLobbyState = tpcLobbyState,
            closeLobbyScreen = closeLobbyScreen,
            onPieceColorChanged = onPieceColorChanged,
            onReadinessChanged = onReadinessChanged
        ) {
            scope.launch {
                drawerState.open()
            }
        }
    }
}

@Composable
fun TpcLobbyContent(
    tpcUserState: TpcUserState,
    tpcLobbyState: TpcLobbyState,
    closeLobbyScreen: () -> Unit,
    onPieceColorChanged: (PieceColor) -> Unit,
    onReadinessChanged: (Boolean) -> Unit,
    onDrawerPressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {
        TpcLobbyScreen(
            currentPlayer = tpcUserState.currentPlayer!!,
            playerGameSessionInfos = tpcLobbyState.playerGameSessionInfos,
            closeLobbyScreen = closeLobbyScreen,
            onPieceColorChanged = onPieceColorChanged,
            onReadinessChanged = onReadinessChanged,
            onDrawerPressed = onDrawerPressed
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TpcNavigationWrapper(
    uiState: TpcUIState,
    userState: TpcUserState,
    dataState: TpcDataState,
    closeFilterScreen: () -> Unit,
    navigateToFilterScreen: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit,
    navigateOnLobbyScreen: (Int) -> Unit,
    onRatingScreenNavigated: () -> Unit,
    onProfileScreenNavigated: () -> Unit,
    createLobby: () -> Unit,
    logoutUser: () -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        TpcNavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: TpcRoute.LOBBIES

    ModalNavigationDrawer(
        drawerContent = {
            NavigationDrawer(
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigationActions::navigateTo,
                createLobby = createLobby,
                logoutUser = logoutUser,
                closeDrawer = {
                    scope.launch {
                        drawerState.close()
                    }
                }
            )
        },
        drawerState = drawerState
    ) {
        TpcTabContent(
            uiState = uiState,
            userState = userState,
            dataState = dataState,
            navController = navController,
            selectedDestination = selectedDestination,
            navigateToTopLevelDestination = navigationActions::navigateTo,
            closeFilterScreen = closeFilterScreen,
            navigateToFilter = navigateToFilterScreen,
            onPlayerFilterChanged = onPlayerFilterChanged,
            navigateOnLobbyScreen = navigateOnLobbyScreen,
            onRatingScreenNavigated = onRatingScreenNavigated,
            onProfileScreenNavigated = onProfileScreenNavigated,
            createLobby = createLobby
        )
    }
}

@Composable
fun TpcTabContent(
    uiState: TpcUIState,
    userState: TpcUserState,
    dataState: TpcDataState,
    navController: NavHostController,
    selectedDestination: String,
    navigateToTopLevelDestination: (TpcTopLevelDestination) -> Unit,
    closeFilterScreen: () -> Unit,
    navigateToFilter: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit,
    navigateOnLobbyScreen: (Int) -> Unit,
    onRatingScreenNavigated: () -> Unit,
    onProfileScreenNavigated: () -> Unit,
    createLobby: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {

        val navBarEnabled = !uiState.openedFilter

        TpcNavHost(
            modifier = Modifier.weight(1f),
            navController = navController,
            uiState = uiState,
            userState = userState,
            dataState = dataState,
            closeFilterScreen = closeFilterScreen,
            navigateToFilter = navigateToFilter,
            onPlayerFilterChanged = onPlayerFilterChanged,
            navigateOnLobbyScreen = navigateOnLobbyScreen,
            createLobby = createLobby,
            onRatingScreenNavigated = onRatingScreenNavigated,
            onProfileScreenNavigated = onProfileScreenNavigated
        )
        if (navBarEnabled) {
            BottomNavigationBar(
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigateToTopLevelDestination
            )
        }
    }
}

@Composable
private fun TpcNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    uiState: TpcUIState,
    userState: TpcUserState,
    dataState: TpcDataState,
    closeFilterScreen: () -> Unit,
    navigateToFilter: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit,
    navigateOnLobbyScreen: (Int) -> Unit,
    createLobby: () -> Unit,
    onRatingScreenNavigated: () -> Unit,
    onProfileScreenNavigated: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = TpcRoute.LOBBIES,
    ) {
        composable(TpcRoute.LOBBIES) {
            TpcLobbiesScreen(
                uiState = uiState,
                dataState = dataState,
                closeFilterScreen = closeFilterScreen,
                navigateToFilter = navigateToFilter,
                onPlayerFilterChanged = onPlayerFilterChanged,
                navigateOnLobbyScreen = navigateOnLobbyScreen,
                createLobby = createLobby
            )
        }
        composable(TpcRoute.RATING) {
            onRatingScreenNavigated()
            TpcRatingScreen(
                uiState = uiState,
                userState = userState,
                dataState = dataState,
                closeFilterScreen = closeFilterScreen,
                navigateToFilter = navigateToFilter,
                onPlayerFilterChanged = onPlayerFilterChanged
            )
        }
        composable(TpcRoute.PROFILE) {
            onProfileScreenNavigated()
            TpcProfileScreen(
                userState = userState,
                dataState = dataState,
                navigateToFilter = navigateToFilter,
                onPlayerFilterChanged = onPlayerFilterChanged,
            )
        }
    }
}
