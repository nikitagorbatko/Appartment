package liliaikha.my.realestate.ui.charts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import liliaikha.my.realestate.database.ApartmentDao
import liliaikha.my.realestate.database.DynamicInfo

class ChartsFragmentViewModel(private val dao: ApartmentDao): ViewModel() {
    val dynamics = dao.getDynamicInfo().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        emptyList()
    )

    val years = dao.getYears().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        emptyList()
    )

    val cities = dao.getCities().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        emptyList()
    )

    suspend fun getDynamicsFirst(city: String, year: String) = dao.getDynamicInfoSuspend(city, year)
    suspend fun getDynamicsSecond(city: String, year: String) = dao.getDynamicInfoSuspend(city, year)
    suspend fun getDynamicsThird(city: String, year: String) = dao.getDynamicInfoSuspend(city, year)
    suspend fun getDynamicsForth(city: String, year: String) = dao.getDynamicInfoSuspend(city, year)
}