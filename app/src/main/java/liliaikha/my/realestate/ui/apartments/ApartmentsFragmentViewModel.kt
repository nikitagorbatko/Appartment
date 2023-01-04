package liliaikha.my.realestate.ui.apartments

import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import liliaikha.my.realestate.database.ApartmentDao
import liliaikha.my.realestate.ui.State

class ApartmentsFragmentViewModel(dao: ApartmentDao) : ViewModel() {
    private val _state = MutableStateFlow(State.DOWNLOADING)
    val state = _state.asStateFlow()

    val apartments = dao.getAllApartments().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(8000L),
        emptyList()
    )

    init {
        viewModelScope.launch {
            apartments.collect {
                if (it.isNotEmpty()) {
                    _state.value = State.PRESENT
                }
            }
        }
    }
}