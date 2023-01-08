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
import ru.touchthegrass.tpc.ui.navigation.*
import ru.touchthegrass.tpc.ui.screen.EmptyScreen
import ru.touchthegrass.tpc.ui.screen.TpcLobbiesScreen
import ru.touchthegrass.tpc.ui.util.*
import ru.touchthegrass.tpc.viewmodel.TpcFilterState
import ru.touchthegrass.tpc.viewmodel.TpcHomeUIState

@Composable
fun TpcApp(
    tpcHomeUIState: TpcHomeUIState,
    tpcFilterState: TpcFilterState,
    closeFilterScreen: () -> Unit,
    navigateToFilter: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit
) {
    TpcNavigationWrapper(
        tpcHomeUIState = tpcHomeUIState,
        tpcFilterState = tpcFilterState,
        closeFilterScreen = closeFilterScreen,
        navigateToFilter = navigateToFilter,
        onPlayerFilterChanged = onPlayerFilterChanged
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TpcNavigationWrapper(
    tpcHomeUIState: TpcHomeUIState,
    tpcFilterState: TpcFilterState,
    closeFilterScreen: () -> Unit,
    navigateToFilter: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit
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
            NavigationDrawerContent(
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigationActions::navigateTo,
                onDrawerClicked = {
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
            tpcFilterState = tpcFilterState,
            navController = navController,
            selectedDestination = selectedDestination,
            navigateToTopLevelDestination = navigationActions::navigateTo,
            closeFilterScreen = closeFilterScreen,
            navigateToFilter = navigateToFilter,
            onPlayerFilterChanged = onPlayerFilterChanged
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TpcTabContent(
    tpcHomeUIState: TpcHomeUIState,
    tpcFilterState: TpcFilterState,
    navController: NavHostController,
    selectedDestination: String,
    navigateToTopLevelDestination: (TpcTopLevelDestination) -> Unit,
    closeFilterScreen: () -> Unit,
    navigateToFilter: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit
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
            tpcFilterState = tpcFilterState,
            closeFilterScreen = closeFilterScreen,
            navigateToFilter = navigateToFilter,
            onPlayerFilterChanged = onPlayerFilterChanged
        )
        if (navBarEnabled) {
            TpcBottomNavigationBar(
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
    tpcFilterState: TpcFilterState,
    closeFilterScreen: () -> Unit,
    navigateToFilter: () -> Unit,
    onPlayerFilterChanged: (String) -> Unit
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
                closeFilterScreen = closeFilterScreen,
                navigateToFilter = navigateToFilter,
                onPlayerFilterChanged = onPlayerFilterChanged,
            )
        }
        composable(TpcRoute.RATING) {
            EmptyScreen()
        }
        composable(TpcRoute.PROFILE) {
            EmptyScreen()
        }
    }
}
