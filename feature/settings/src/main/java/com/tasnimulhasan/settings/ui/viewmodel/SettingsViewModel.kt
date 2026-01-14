package com.tasnimulhasan.settings.ui.viewmodel

import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.datastore.translation.GetPreferredTranslationUseCase
import com.tasnimulhasan.domain.localusecase.datastore.translation.SavePreferredTranslationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getPreferredTranslationUseCase: GetPreferredTranslationUseCase,
    private val savePreferredTranslationUseCase: SavePreferredTranslationUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState

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
        getPreferredTranslationName()
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

}