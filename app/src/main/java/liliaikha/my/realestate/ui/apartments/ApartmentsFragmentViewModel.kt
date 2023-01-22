package liliaikha.my.realestate.ui.apartments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import liliaikha.my.realestate.database.ApartmentDao
import liliaikha.my.realestate.database.ApartmentInfo

class ApartmentsFragmentViewModel(private val dao: ApartmentDao) : ViewModel() {
    var roomSliderValueFrom = 1.0f
    var roomSliderValueTo = 6.0f
    var areaSliderValueFrom = 1.0f
    var areaSliderValueTo = 950.0f
    var switchCitiesChecked = false
    var switchSortChecked = false
    var minPrice = ""
    var maxPrice = ""
    var selectedCitySpinnerPosition = 0
    var selectedSortSpinnerPosition = 0

    private val _maxRooms = MutableStateFlow(1.0f)
    val maxRooms = _maxRooms.asStateFlow()

    private val _maxArea = MutableStateFlow(950.0f)
    val maxArea = _maxArea.asStateFlow()

    private val _state = MutableStateFlow(ApartmentsFragmentState.DOWNLOADING)
    val state = _state.asStateFlow()

    private val _apartmentsState = MutableStateFlow<ArrayList<ApartmentInfo>>(arrayListOf())
    val apartmentsState = _apartmentsState.asStateFlow()

    private val _regionsState = MutableStateFlow<List<String>>(listOf())
    val regionsState = _regionsState.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = ApartmentsFragmentState.DOWNLOADING
            val apartments = dao.getAllApartments()
            val regions = dao.getRegions()
            _maxArea.value = dao.getMaxRooms().toFloat()
            _maxRooms.value =  dao.getMaxArea().toFloat()
            _state.value = ApartmentsFragmentState.PRESENT
            _apartmentsState.value = ArrayList(apartments)
            _regionsState.value = regions
        }
    }

    fun getFilteredApartments(
        minRoomsParameter: Int,
        maxRoomsParameter: Int,
        minAreaParameter: Int,
        maxAreaParameter: Int,
        regionParameter: String?,
        sortParameter: Int?,
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
        val byAsc = sortParameter!! % 2 == 0
        val sortField = when(sortParameter!!) {
                0 -> "RoomCount"
                1 -> "RoomCount"
                2 -> "TotalArea"
                3 -> "TotalArea"
                4 -> "Price"
                5 -> "Price"
                else -> "Id"
        }
        viewModelScope.launch {
            _state.value = ApartmentsFragmentState.DOWNLOADING
            val result = if (byAsc) {
                dao.getFilteredApartmentsAsc(
                    minRooms,
                    maxRooms,
                    minAreaParameter,
                    maxArea,
                    region,
                    minPrice,
                    maxPrice,
                    sortField,
                )
            } else {
                dao.getFilteredApartmentsDesc(
                    minRooms,
                    maxRooms,
                    minAreaParameter,
                    maxArea,
                    region,
                    minPrice,
                    maxPrice,
                    sortField,
                )
            }

            if (result.isEmpty()) {
                _state.value = ApartmentsFragmentState.EMPTY
            } else {
                _state.value = ApartmentsFragmentState.PRESENT
                _apartmentsState.value = ArrayList(result)
            }
        }
    }
}