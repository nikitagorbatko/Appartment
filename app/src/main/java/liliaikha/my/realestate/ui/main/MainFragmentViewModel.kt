package liliaikha.my.realestate.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainFragmentViewModel: ViewModel() {
    private val _state = MutableStateFlow(MainFragmentState.APARTMENTS)
    val state = _state.asStateFlow()

    fun setFlatState() {
        _state.value = MainFragmentState.FLAT
    }

    fun setApartmentsState() {
        _state.value = MainFragmentState.APARTMENTS
    }
}