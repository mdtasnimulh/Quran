package com.tasnimulhasan.quranrecitation.ui.viewmodel

import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.GetRecitationsUseCase
import com.tasnimulhasan.entity.sura.QuranRecitationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class QuranRecitationViewModel @Inject constructor(
    private val getRecitationsUseCase: GetRecitationsUseCase
) : BaseViewModel() {

    private val _recitations = MutableStateFlow<List<QuranRecitationEntity>>(emptyList())
    val recitations: StateFlow<List<QuranRecitationEntity>> = _recitations

    val action: (QuranRecitationAction) -> Unit = { action ->
        when (action) {
            else -> {}
        }
    }

    init {
        execute {
            _recitations.value = getRecitationsUseCase.invoke()
        }
    }

}