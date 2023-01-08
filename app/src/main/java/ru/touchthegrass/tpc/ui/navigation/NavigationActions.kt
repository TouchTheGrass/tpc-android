package ru.touchthegrass.tpc.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import ru.touchthegrass.tpc.R

object TpcRoute {
    const val LOBBIES = "Lobbies"
    const val RATING = "Rating"
    const val PROFILE = "Profile"
    const val LOGOUT = "Logout"
}

data class TpcTopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)

class TpcNavigationActions(private val navController: NavHostController) {

    fun navigateTo(destination: TpcTopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

val NAVIGABLE_DESTINATIONS = listOf(
    TpcTopLevelDestination(
        route = TpcRoute.LOBBIES,
        selectedIcon = Icons.Default.Diversity3,
        unselectedIcon = Icons.Default.Diversity3,
        iconTextId = R.string.tab_lobbies
    ),
    TpcTopLevelDestination(
        route = TpcRoute.RATING,
        selectedIcon = Icons.Default.Stars,
        unselectedIcon = Icons.Default.Stars,
        iconTextId = R.string.tab_rating
    ),
    TpcTopLevelDestination(
        route = TpcRoute.PROFILE,
        selectedIcon = Icons.Outlined.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        iconTextId = R.string.tab_profile
    )
)

val SETTINGS = listOf(
    TpcTopLevelDestination(
        route = TpcRoute.LOGOUT,
        selectedIcon = Icons.Default.Logout,
        unselectedIcon = Icons.Default.Logout,
        iconTextId = R.string.logout
    )
)
