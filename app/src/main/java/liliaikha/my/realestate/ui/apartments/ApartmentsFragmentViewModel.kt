package liliaikha.my.realestate.ui.apartments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import liliaikha.my.realestate.database.ApartmentDao
import liliaikha.my.realestate.database.ApartmentInfo
import liliaikha.my.realestate.ui.State

class ApartmentsFragmentViewModel(private val dao: ApartmentDao) : ViewModel() {
    private val _state = MutableStateFlow(State.DOWNLOADING)
    val state = _state.asStateFlow()

    private val _channel = Channel<List<ApartmentInfo>>()
    val channel = _channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            _state.value = State.DOWNLOADING
            val result = dao.getAllApartments()
            _state.value = State.PRESENT
            _channel.send(result)
        }
    }

    fun getFilteredApartments(
        minRoomsParameter: Int,
        maxRoomsParameter: Int,
        minAreaParameter: Int,
        maxAreaParameter: Int,
        cityParameter: String?,
        minPriceParameter: Int?,
        maxPriceParameter: Int?,
        withCity: Boolean
    ) {
        val minPrice = minPriceParameter ?: 0
        val maxPrice = maxPriceParameter ?: 1984853500

        val minRooms = if (minRoomsParameter == 0) 1 else minRoomsParameter
        val maxRooms = if (maxRoomsParameter == 0) 6 else maxRoomsParameter
        val maxArea = if (maxAreaParameter == 0) 950 else maxAreaParameter

        val city = if (withCity) {
            cityParameter!!
        } else {
            "%"
        }
        viewModelScope.launch {
            _state.value = State.DOWNLOADING
            val result = dao.getFilteredApartments(
                minRooms,
                maxRooms,
                minAreaParameter,
                maxArea,
                city,
                minPrice,
                maxPrice
            )
            if (result.isEmpty()) {
                _state.value = State.EMPTY
            } else {
                _state.value = State.PRESENT
                _channel.send(result)
            }
        }
    }
}