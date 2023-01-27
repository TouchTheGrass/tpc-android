package ru.touchthegrass.tpc.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.touchthegrass.tpc.R
import ru.touchthegrass.tpc.data.GameRule
import ru.touchthegrass.tpc.ui.navigation.NAVIGABLE_DESTINATIONS
import ru.touchthegrass.tpc.ui.navigation.SETTINGS
import ru.touchthegrass.tpc.ui.navigation.TpcTopLevelDestination
import ru.touchthegrass.tpc.ui.util.LayoutType
import ru.touchthegrass.tpc.ui.util.tpcDrawerMeasurePolicy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    selectedDestination: String,
    navigateToTopLevelDestination: (TpcTopLevelDestination) -> Unit,
    createLobby: () -> Unit,
    logoutUser: () -> Unit,
    closeDrawer: () -> Unit = {}
) {
    ModalDrawerSheet {
        Layout(
            modifier = Modifier.padding(16.dp),
            content = {
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.app_name).uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        IconButton(onClick = closeDrawer) {
                            Icon(
                                imageVector = Icons.Default.MenuOpen,
                                contentDescription = stringResource(R.string.navigation_drawer)
                            )
                        }
                    }

                    ExtendedFloatingActionButton(
                        onClick = createLobby,
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add),
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .weight(1f),
                            text = stringResource(R.string.create_lobby),
                            textAlign = TextAlign.Left
                        )
                    }

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
                            contentDescription = stringResource(R.string.play),
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .weight(1f),
                            text = stringResource(R.string.rejoin_game),
                            textAlign = TextAlign.Left
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .layoutId(LayoutType.CONTENT)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    NAVIGABLE_DESTINATIONS.forEach { tpcDestination ->
                        NavigationDrawerItem(
                            selectedDestination = selectedDestination,
                            tpcDestination = tpcDestination,
                        ) {
                            navigateToTopLevelDestination(tpcDestination)
                        }
                    }
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = stringResource(R.string.settings)
                    )
                    SETTINGS.forEach { tpcDestination ->
                        NavigationDrawerItem(
                            selectedDestination = selectedDestination,
                            tpcDestination = tpcDestination,
                        ) {
                            logoutUser()
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
fun LobbyDrawer(
    rules: List<GameRule>,
    closeDrawer: () -> Unit
) {
    ModalDrawerSheet {
        Layout(
            modifier = Modifier.padding(16.dp),
            content = {
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.game_rules).uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        IconButton(onClick = closeDrawer) {
                            Icon(
                                imageVector = Icons.Default.MenuOpen,
                                contentDescription = stringResource(id = R.string.lobby_drawer)
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .layoutId(LayoutType.CONTENT)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.Start
                ) {
                    for (rule in rules) {
                        GameRuleChip(
                            icon = rule.icon,
                            title = rule.value
                        )
                    }
                }
            },
            measurePolicy = tpcDrawerMeasurePolicy
        )
    }
}
