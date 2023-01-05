package liliaikha.my.realestate.ui.charts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import liliaikha.my.realestate.database.ApartmentDao

class ChartsFragmentViewModel(private val dao: ApartmentDao) : ViewModel() {
    val dynamics = dao.getDynamicInfo().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        emptyList()
    )
}