package ru.touchthegrass.tpc.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.ui.navigation.NAVIGABLE_DESTINATIONS
import ru.touchthegrass.tpc.ui.navigation.TpcTopLevelDestination

@Composable
fun BottomNavigationBar(
    selectedDestination: String,
    navigateToTopLevelDestination: (TpcTopLevelDestination) -> Unit
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        NAVIGABLE_DESTINATIONS.forEach { tpcDestination ->
            NavigationBarItem(
                selected = selectedDestination == tpcDestination.route,
                onClick = { navigateToTopLevelDestination(tpcDestination) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                ),
                icon = {
                    Icon(
                        imageVector = tpcDestination.selectedIcon,
                        contentDescription = stringResource(tpcDestination.iconTextId)
                    )
                }
            )
        }
    }
}

@Composable
fun BottomButtonBar(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        horizontalArrangement = Arrangement.Center
    ) {
        ExtendedFloatingActionButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ) {
            Text(
                modifier = Modifier,
                text = text,
                textAlign = TextAlign.Center
            )
        }
    }
}
