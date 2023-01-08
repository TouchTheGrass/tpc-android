package ru.touchthegrass.tpc.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.R
import ru.touchthegrass.tpc.ui.util.LayoutType
import ru.touchthegrass.tpc.ui.util.TpcNavigationContentPosition
import ru.touchthegrass.tpc.ui.util.tpcDrawerMeasurePolicy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerContent(
    selectedDestination: String,
    navigateToTopLevelDestination: (TpcTopLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit = {}
) {
    ModalDrawerSheet {
        Layout(
            modifier = Modifier.padding(16.dp),
            content = {

                // Header
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Title
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name).uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        IconButton(onClick = onDrawerClicked) {
                            Icon(
                                imageVector = Icons.Default.MenuOpen,
                                contentDescription = stringResource(id = R.string.navigation_drawer)
                            )
                        }
                    }

                    // Create lobby button
                    ExtendedFloatingActionButton(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.add),
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .weight(1f),
                            text = stringResource(id = R.string.create_lobby),
                            textAlign = TextAlign.Left
                        )
                    }

                    // Rejoin game button
                    ExtendedFloatingActionButton(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 40.dp),
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = stringResource(id = R.string.play),
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .weight(1f),
                            text = stringResource(id = R.string.create_lobby),
                            textAlign = TextAlign.Left
                        )
                    }
                }

                // Navigation and settings
                Column(
                    modifier = Modifier
                        .layoutId(LayoutType.CONTENT)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    NAVIGABLE_DESTINATIONS.forEach { tpcDestination ->
                        TpcDrawerItem(
                            selectedDestination = selectedDestination,
                            tpcDestination = tpcDestination,
                        ) {
                            navigateToTopLevelDestination(tpcDestination)
                        }
                    }
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = stringResource(id = R.string.settings)
                    )
                    SETTINGS.forEach { tpcDestination ->
                        TpcDrawerItem(
                            selectedDestination = selectedDestination,
                            tpcDestination = tpcDestination,
                        ) {

                        }
                    }
                }
            },
            measurePolicy = tpcDrawerMeasurePolicy
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TpcDrawerItem(
    selectedDestination: String,
    tpcDestination: TpcTopLevelDestination,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        selected = selectedDestination == tpcDestination.route,
        label = {
            Text(
                text = stringResource(id = tpcDestination.iconTextId),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        },
        icon = {
            Icon(
                imageVector = tpcDestination.selectedIcon,
                contentDescription = stringResource(tpcDestination.iconTextId)
            )
        },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent
        ),
        onClick = onClick
    )
}

@Composable
fun TpcBottomNavigationBar(
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
                        contentDescription = stringResource(id = tpcDestination.iconTextId)
                    )
                }
            )
        }
    }
}
