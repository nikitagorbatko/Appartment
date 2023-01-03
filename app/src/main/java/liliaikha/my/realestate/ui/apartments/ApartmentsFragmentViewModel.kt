package liliaikha.my.realestate.ui.apartments

import androidx.lifecycle.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import liliaikha.my.realestate.database.ApartmentDao

class ApartmentsFragmentViewModel(dao: ApartmentDao) : ViewModel() {
    val apartments = dao.getAllApartments().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        emptyList()
    )
}