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

    private val _regionsChannel = Channel<List<String>>()
    val regionsChannel = _regionsChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            _state.value = State.DOWNLOADING
            val apartments = dao.getAllApartments()
            val regions = dao.getRegions()
            _state.value = State.PRESENT
            _channel.send(apartments)
            _regionsChannel.send(regions)
        }
    }

    fun getFilteredApartments(
        minRoomsParameter: Int,
        maxRoomsParameter: Int,
        minAreaParameter: Int,
        maxAreaParameter: Int,
        regionParameter: String?,
        minPriceParameter: Int?,
        maxPriceParameter: Int?,
        withCity: Boolean
    ) {
        val minPrice = minPriceParameter ?: 0
        val maxPrice = maxPriceParameter ?: 1984853500

        val minRooms = if (minRoomsParameter == 0) 1 else minRoomsParameter
        val maxRooms = if (maxRoomsParameter == 0) 6 else maxRoomsParameter
        val maxArea = if (maxAreaParameter == 0) 950 else maxAreaParameter

        val region = if (withCity) {
            regionParameter!!
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
                region,
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