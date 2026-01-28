package com.tasnimulhasan.quran

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasnimulhasan.domain.localusecase.datastore.theme.GetAppConfigurationStreamUseCase
import com.tasnimulhasan.entity.enum.ThemeColor
import com.tasnimulhasan.entity.enum.ThemeStyleType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.isNotEmpty

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getAppConfigurationStreamUseCase: GetAppConfigurationStreamUseCase
) : ViewModel() {
    private val viewModelState = MutableStateFlow(value = MainViewModelState())

    val activityState = viewModelState.map { it.asActivityState() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = viewModelState.value.asActivityState()
    )

    init {
        watchAppConfigurationStream()
    }

    private fun watchAppConfigurationStream() {
        viewModelScope.launch {
            viewModelState.update { it.copy(isLoading = true) }
            delay(timeMillis = 300)

            getAppConfigurationStreamUseCase.invoke().collectLatest { appConfiguration ->
                viewModelState.update { state ->
                    state.copy(
                        isLoading = false,
                        useDynamicColors = appConfiguration.useDynamicColors,
                        themeStyle = appConfiguration.themeStyle,
                        themeColor = appConfiguration.themeColor,
                    )
                }
            }
        }
    }
}


/**
 * Data class that represents the state of activity.
 */
data class MainActivityState(
    val isLoading: Boolean,
    val useDynamicColors: Boolean,
    val themeStyle: ThemeStyleType,
    val themeColor: ThemeColor,
    val isLoggedIn: Boolean
)

/**
 * Data class that represents the state of the view model.
 */
private data class MainViewModelState(
    val isLoading: Boolean = true,
    val useDynamicColors: Boolean = true,
    val themeStyle: ThemeStyleType = ThemeStyleType.FollowAndroidSystem,
    val themeColor: ThemeColor = ThemeColor.Default,
    val isLoggedIn: Boolean = false
) {
    fun asActivityState() = MainActivityState(
        isLoading = isLoading,
        useDynamicColors = useDynamicColors,
        themeStyle = themeStyle,
        themeColor = themeColor,
        isLoggedIn = isLoggedIn
    )
}