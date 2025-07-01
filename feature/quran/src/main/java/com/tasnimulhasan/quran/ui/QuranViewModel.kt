package com.tasnimulhasan.quran.ui

import com.tasnimulhasan.common.constant.SuraNames
import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.datastore.GetLastReadSuraUseCase
import com.tasnimulhasan.domain.localusecase.local.FetchAllSuraNamesUseCase
import com.tasnimulhasan.domain.localusecase.suraname.InsertSuraNamesUseCase
import com.tasnimulhasan.entity.LastReadSuraInfoEntity
import com.tasnimulhasan.entity.sura.SuraNameEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class QuranViewModel @Inject constructor(
    private val fetchAllSuraNamesUseCase: FetchAllSuraNamesUseCase,
    private val insertSuraNamesUseCase: InsertSuraNamesUseCase,
    private val getLastReadSuraUseCase: GetLastReadSuraUseCase,
) : BaseViewModel() {

    val suraNames = MutableStateFlow<List<SuraNameEntity>>(emptyList())
    val lastReadSura = MutableStateFlow<LastReadSuraInfoEntity?>(null)

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading(false))
    val uiState: StateFlow<UiState> get() = _uiState

    val action: (UiAction) -> Unit = {
        when (it) {
            is UiAction.FetchAllSuraNames -> fetchAllSuraNames()
            is UiAction.GetLastReadSura -> getLastReadSura()
        }
    }

    private fun fetchAllSuraNames() {
        execute {
            fetchAllSuraNamesUseCase.invoke().collectLatest { result ->
                suraNames.value = result

                if (result.isEmpty()) {
                    insertSuraNames(
                        params = InsertSuraNamesUseCase.Params(SuraNames.suraList)
                    )
                }
            }
        }
    }

    private fun insertSuraNames(params: InsertSuraNamesUseCase.Params) {
        execute {
            if (suraNames.value.isEmpty()) {
                insertSuraNamesUseCase.invoke(params)
            }
        }
    }

    private fun getLastReadSura() {
        execute {
            getLastReadSuraUseCase.invoke().collectLatest {
                lastReadSura.value = it
            }
        }
    }

}

sealed interface UiState {
    data class Loading(val isLoading: Boolean) : UiState
    data class Error(val message: String) : UiState
    data class Success(val suraNames: List<SuraNameEntity>) : UiState
}

sealed interface UiAction {
    data object FetchAllSuraNames : UiAction
    data object GetLastReadSura : UiAction
}