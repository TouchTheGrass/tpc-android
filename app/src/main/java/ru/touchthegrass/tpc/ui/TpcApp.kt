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
import ru.touchthegrass.tpc.ui.component.BottomNavigationBar
import ru.touchthegrass.tpc.ui.component.LobbyDrawer
import ru.touchthegrass.tpc.ui.component.NavigationDrawer
import ru.touchthegrass.tpc.ui.navigation.*
import ru.touchthegrass.tpc.ui.screen.*
import ru.touchthegrass.tpc.ui.util.*
import ru.touchthegrass.tpc.model.TpcFilterState
import ru.touchthegrass.tpc.model.TpcHomeUIState
import ru.touchthegrass.tpc.model.TpcLobbyState
import ru.touchthegrass.tpc.model.TpcPlayerState

@Composable
fun TpcApp(
    tpcHomeUIState: TpcHomeUIState,
    tpcPlayerState: TpcPlayerState,
    tpcFilterState: TpcFilterState,
    tpcLobbyState: TpcLobbyState,
    loginUser: (String, String) -> Unit,
    registerUser: (String, String, String) -> Unit,
    navigateToLoginScreen: () -> Unit,
    navigateToRegistrationScreen: () -> Unit,
    closeFilterScreen: () -> Unit,
    navigateToFilterScreen: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit,
    closeLobbyScreen: () -> Unit,
    navigateOnLobbyScreen: (Int) -> Unit,
    createLobby: () -> Unit,
    onPieceColorChanged: (PieceColor) -> Unit,
    onReadinessChanged: (Boolean) -> Unit,
    onConfirmTurnPressed: () -> Unit
) {

    if (tpcPlayerState.currentPlayer == null) {
        if (tpcHomeUIState.openedRegister) {
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
    } else if (tpcHomeUIState.openedLobby) {

        if (tpcLobbyState.gameSession!!.status == GameSessionStatus.GAME) {
            GameScreen(
                tpcPlayerState = tpcPlayerState,
                tpcLobbyState = tpcLobbyState,
                onConfirmTurnPressed = onConfirmTurnPressed
            )
        } else {
            TpcLobbyWrapper(
                tpcPlayerState = tpcPlayerState,
                tpcLobbyState = tpcLobbyState,
                closeLobbyScreen = closeLobbyScreen,
                onPieceColorChanged = onPieceColorChanged,
                onReadinessChanged = onReadinessChanged
            )
        }
    } else {
        TpcNavigationWrapper(
            tpcHomeUIState = tpcHomeUIState,
            tpcPlayerState = tpcPlayerState,
            tpcFilterState = tpcFilterState,
            tpcLobbyState = tpcLobbyState,
            closeFilterScreen = closeFilterScreen,
            navigateToFilterScreen = navigateToFilterScreen,
            onPlayerFilterChanged = onPlayerFilterChanged,
            navigateOnLobbyScreen = navigateOnLobbyScreen,
            createLobby = createLobby
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TpcLobbyWrapper(
    tpcPlayerState: TpcPlayerState,
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
            tpcPlayerState = tpcPlayerState,
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
    tpcPlayerState: TpcPlayerState,
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
            currentPlayer = tpcPlayerState.currentPlayer!!,
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
    tpcHomeUIState: TpcHomeUIState,
    tpcPlayerState: TpcPlayerState,
    tpcFilterState: TpcFilterState,
    tpcLobbyState: TpcLobbyState,
    closeFilterScreen: () -> Unit,
    navigateToFilterScreen: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit,
    navigateOnLobbyScreen: (Int) -> Unit,
    createLobby: () -> Unit
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
            tpcHomeUIState = tpcHomeUIState,
            tpcPlayerState = tpcPlayerState,
            tpcFilterState = tpcFilterState,
            tpcLobbyState = tpcLobbyState,
            navController = navController,
            selectedDestination = selectedDestination,
            navigateToTopLevelDestination = navigationActions::navigateTo,
            closeFilterScreen = closeFilterScreen,
            navigateToFilter = navigateToFilterScreen,
            onPlayerFilterChanged = onPlayerFilterChanged,
            navigateOnLobbyScreen = navigateOnLobbyScreen,
            createLobby = createLobby
        )
    }
}

@Composable
fun TpcTabContent(
    tpcHomeUIState: TpcHomeUIState,
    tpcPlayerState: TpcPlayerState,
    tpcFilterState: TpcFilterState,
    tpcLobbyState: TpcLobbyState,
    navController: NavHostController,
    selectedDestination: String,
    navigateToTopLevelDestination: (TpcTopLevelDestination) -> Unit,
    closeFilterScreen: () -> Unit,
    navigateToFilter: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit,
    navigateOnLobbyScreen: (Int) -> Unit,
    createLobby: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {

        val navBarEnabled = !tpcHomeUIState.openedFilter

        TpcNavHost(
            modifier = Modifier.weight(1f),
            navController = navController,
            tpcHomeUIState = tpcHomeUIState,
            tpcPlayerState = tpcPlayerState,
            tpcFilterState = tpcFilterState,
            tpcLobbyState = tpcLobbyState,
            closeFilterScreen = closeFilterScreen,
            navigateToFilter = navigateToFilter,
            onPlayerFilterChanged = onPlayerFilterChanged,
            navigateOnLobbyScreen = navigateOnLobbyScreen,
            createLobby = createLobby
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
    tpcHomeUIState: TpcHomeUIState,
    tpcPlayerState: TpcPlayerState,
    tpcFilterState: TpcFilterState,
    tpcLobbyState: TpcLobbyState,
    closeFilterScreen: () -> Unit,
    navigateToFilter: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit,
    navigateOnLobbyScreen: (Int) -> Unit,
    createLobby: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = TpcRoute.LOBBIES,
    ) {
        composable(TpcRoute.LOBBIES) {
            TpcLobbiesScreen(
                tpcHomeUIState = tpcHomeUIState,
                tpcFilterState = tpcFilterState,
                tpcLobbyState = tpcLobbyState,
                closeFilterScreen = closeFilterScreen,
                navigateToFilter = navigateToFilter,
                onPlayerFilterChanged = onPlayerFilterChanged,
                navigateOnLobbyScreen = navigateOnLobbyScreen,
                createLobby = createLobby
            )
        }
        composable(TpcRoute.RATING) {
            TpcRatingScreen(
                tpcHomeUIState = tpcHomeUIState,
                tpcPlayerState = tpcPlayerState,
                tpcFilterState = tpcFilterState,
                closeFilterScreen = closeFilterScreen,
                navigateToFilter = navigateToFilter,
                onPlayerFilterChanged = onPlayerFilterChanged
            )
        }
        composable(TpcRoute.PROFILE) {
            TpcProfileScreen(
                tpcPlayerState = tpcPlayerState,
                tpcFilterState = tpcFilterState,
                navigateToFilter = navigateToFilter,
                onPlayerFilterChanged = onPlayerFilterChanged,
            )
        }
    }
}
