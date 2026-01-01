package com.tasnimulhasan.suradetails.ui

import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.datastore.IsLastReadSuraAvailableUseCase
import com.tasnimulhasan.domain.localusecase.datastore.SetLastReadSuraUseCase
import com.tasnimulhasan.domain.localusecase.datastore.translation.GetPreferredTranslationUseCase
import com.tasnimulhasan.domain.localusecase.datastore.translation.SavePreferredTranslationUseCase
import com.tasnimulhasan.domain.localusecase.local.FetchQuranEnglishSahihUseCase
import com.tasnimulhasan.domain.localusecase.local.FetchSurahFromLocalDbUseCase
import com.tasnimulhasan.entity.LastReadSuraInfoEntity
import com.tasnimulhasan.entity.QuranEnglishSahihEntity
import com.tasnimulhasan.entity.QuranLocalDbEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SuraDetailsViewModel @Inject constructor(
    private val fetchSurahFromLocalDbUseCase: FetchSurahFromLocalDbUseCase,
    private val fetchQuranEnglishSahihUseCase: FetchQuranEnglishSahihUseCase,
    private val isLastReadSuraAvailableUseCase: IsLastReadSuraAvailableUseCase,
    private val setLastReadSuraUseCase: SetLastReadSuraUseCase,
    private val getPreferredTranslationUseCase: GetPreferredTranslationUseCase,
    private val savePreferredTranslationUseCase: SavePreferredTranslationUseCase,
) : BaseViewModel() {

    val suraArabicList = MutableStateFlow<List<QuranLocalDbEntity>>(emptyList())
    val suraEnSahiList = MutableStateFlow<List<QuranEnglishSahihEntity>>(emptyList())

    val ayaCount = MutableStateFlow(0)

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading(false))
    val uiState: StateFlow<UiState> get() = _uiState

    private val _translationName = MutableStateFlow("")
    val translationName: StateFlow<String> = _translationName

    val action: (UiAction) -> Unit = {
        when (it) {
            is UiAction.FetchAllLocalDbSura -> fetchAllLocalSura(FetchSurahFromLocalDbUseCase.Params(it.suraNumber))
            is UiAction.FetchQuranEnglishSahih -> fetchQuranEnglishSahih(it.params)
            is UiAction.SetLastReadSura -> setLastReadSura(it.sura)
            is UiAction.SetLastReadSuraAvailable -> setLastReadSuraAvailable(it.available)
            is UiAction.SavePreferredTranslationName -> saveTranslationName(it.translation)
            is UiAction.GetPreferredTranslationName -> getPreferredTranslationName()
        }
    }

    init {
        getPreferredTranslationName()
    }

    private fun fetchAllLocalSura(params: FetchSurahFromLocalDbUseCase.Params) {
        execute {
            fetchSurahFromLocalDbUseCase.invoke(params).collectLatest { apiResult ->
                _uiState.value = when (apiResult) {
                    is DataResult.Loading -> UiState.Loading(apiResult.loading)
                    is DataResult.Success -> {
                        suraArabicList.value = apiResult.data
                        ayaCount.value = apiResult.data.size
                        UiState.Ready
                    }
                    is DataResult.Error -> UiState.Error(apiResult.message)
                }
            }
        }
    }

    private fun fetchQuranEnglishSahih(params: FetchQuranEnglishSahihUseCase.Params) {
        execute {
            fetchQuranEnglishSahihUseCase.invoke(params).collectLatest { apiResult ->
                _uiState.value = when (apiResult) {
                    is DataResult.Loading -> UiState.Loading(apiResult.loading)
                    is DataResult.Success -> {
                        suraEnSahiList.value = apiResult.data
                        UiState.Ready
                    }
                    is DataResult.Error -> UiState.Error(apiResult.message)
                }
            }
        }
    }

    private fun setLastReadSura(sura: LastReadSuraInfoEntity) {
        execute {
            setLastReadSuraUseCase.invoke(sura)
        }
    }

    private fun setLastReadSuraAvailable(available: Boolean) {
        execute {
            isLastReadSuraAvailableUseCase.invoke(available)
        }
    }

    private fun saveTranslationName(translationName: String) {
        execute {
            savePreferredTranslationUseCase.invoke(translationName)
            _translationName.value = translationName
        }
    }

    private fun getPreferredTranslationName() {
        execute {
            getPreferredTranslationUseCase.invoke().collectLatest { result ->
                _translationName.value = result
            }
        }
    }
}

sealed interface UiState {
    data class Loading(val isLoading: Boolean) : UiState
    data class Error(val message: String) : UiState
    data object Ready : UiState
}

sealed interface UiAction {
    data class FetchAllLocalDbSura(val suraNumber: Int) : UiAction
    data class FetchQuranEnglishSahih(val params: FetchQuranEnglishSahihUseCase.Params) : UiAction
    data class SetLastReadSura(val sura: LastReadSuraInfoEntity) : UiAction
    data class SetLastReadSuraAvailable(val available: Boolean) : UiAction
    data class SavePreferredTranslationName(val translation: String): UiAction
    data object GetPreferredTranslationName : UiAction
}