package ru.touchthegrass.tpc.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.touchthegrass.tpc.ui.theme.TpcTheme
import ru.touchthegrass.tpc.viewmodel.TpcHomeViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: TpcHomeViewModel by viewModels()

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TpcTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val filterState by viewModel.filterState.collectAsStateWithLifecycle()

                TpcApp(
                    tpcHomeUIState = uiState,
                    tpcFilterState = filterState,
                    closeFilterScreen = {
                        viewModel.closeFilterScreen()
                    },
                    navigateToFilter = {
                        viewModel.openFilterScreen()
                    },
                    onPlayerFilterChanged = { newValue ->
                        viewModel.changeSearchPlayerFilter(newValue)
                    }
                )
            }
        }
    }
}
