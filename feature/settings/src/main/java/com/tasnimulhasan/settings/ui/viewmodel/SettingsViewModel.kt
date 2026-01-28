package com.tasnimulhasan.settings.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.datastore.theme.ChangeThemeColorUseCase
import com.tasnimulhasan.domain.localusecase.datastore.theme.ChangeThemeStyleUseCase
import com.tasnimulhasan.domain.localusecase.datastore.theme.GetAppConfigurationStreamUseCase
import com.tasnimulhasan.domain.localusecase.datastore.theme.ToggleDynamicColorsUseCase
import com.tasnimulhasan.domain.localusecase.datastore.translation.GetPreferredTranslationUseCase
import com.tasnimulhasan.domain.localusecase.datastore.translation.SavePreferredTranslationUseCase
import com.tasnimulhasan.entity.enum.ThemeColor
import com.tasnimulhasan.entity.enum.ThemeStyleType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.invoke

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getAppConfigurationStreamUseCase: GetAppConfigurationStreamUseCase,
    private val getPreferredTranslationUseCase: GetPreferredTranslationUseCase,
    private val savePreferredTranslationUseCase: SavePreferredTranslationUseCase,
    private val toggleDynamicColorsUseCase: ToggleDynamicColorsUseCase,
    private val changeThemeStyleUseCase: ChangeThemeStyleUseCase,
    private val changeThemeColorUseCase: ChangeThemeColorUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState

    private val viewModelState = MutableStateFlow(value = SettingsViewModelState())
    val screenState = viewModelState.map { it.asScreenState() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = viewModelState.value.asScreenState()
    )

    private val _translationName = MutableStateFlow("")
    val translationName: StateFlow<String> = _translationName

    private val _showSaveToast = MutableStateFlow(false)
    val showSaveToast: StateFlow<Boolean> = _showSaveToast

    var showPreferredDialog = false

    val translationOptions = listOf(
        "quran_en_sahih" to "English (Sahih International)",
        "quran_en_yusuf_ali" to "English (Yusuf Ali)",
        "quran_en_pickthall" to "English (Pickthall)",
        "bn_mohiuddin_khan" to "Bangla (Mohiuddin Khan)",
    )

    val action: (UiAction) -> Unit = {
        when (it) {
            is UiAction.SavePreferredTranslationName -> saveTranslationName(it.translation)
            is UiAction.GetPreferredTranslationName -> getPreferredTranslationName()
        }
    }

    init {
        watchAppConfigurationStream()
        getPreferredTranslationName()
    }

    private fun watchAppConfigurationStream() {
        execute {
            getAppConfigurationStreamUseCase.invoke().collectLatest { appConfiguration ->
                viewModelState.update { state ->
                    state.copy(
                        useDynamicColors = appConfiguration.useDynamicColors,
                        themeStyle = appConfiguration.themeStyle,
                        themeColor = appConfiguration.themeColor
                    )
                }
            }
        }
    }

    private fun getPreferredTranslationName() {
        execute {
            getPreferredTranslationUseCase.invoke().collectLatest { result ->
                _translationName.value = result
                if (result.isEmpty()) showPreferredDialog = true
            }
        }
    }

    private fun saveTranslationName(translationName: String) {
        execute {
            savePreferredTranslationUseCase.invoke(translationName)
            _translationName.value = translationName
            _showSaveToast.value = true
        }
    }

    fun consumeSaveToast() {
        _showSaveToast.value = false
    }

    fun changeThemeStyle(themeStyle: ThemeStyleType) {
        execute { changeThemeStyleUseCase.invoke(themeStyle = themeStyle) }
    }

    fun toggleDynamicColors() {
        execute {
            toggleDynamicColorsUseCase.invoke()
        }
    }

    fun changeThemeColor(themeColor: ThemeColor) {
        execute { changeThemeColorUseCase.invoke(themeColor = themeColor) }
    }

}

private data class SettingsViewModelState(
    val useDynamicColors: Boolean = true,
    val themeStyle: ThemeStyleType = ThemeStyleType.FollowAndroidSystem,
    val themeColor: ThemeColor = ThemeColor.Default
) {
    fun asScreenState() = SettingsScreenState(
        useDynamicColors = useDynamicColors,
        themeStyle = themeStyle,
        themeColor = themeColor
    )
}

data class SettingsScreenState(
    val useDynamicColors: Boolean,
    val themeStyle: ThemeStyleType,
    val themeColor: ThemeColor,
)