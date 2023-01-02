package liliaikha.my.realestate.ui.main

import androidx.lifecycle.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import liliaikha.my.realestate.database.ApartmentDao

class PageViewModel(dao: ApartmentDao) : ViewModel() {
    val apartments = dao.getAllWords().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        emptyList()
    )
//
//
//    private val _index = MutableLiveData<Int>()
//    val text: LiveData<String> = Transformations.map(_index) {
//        "Hello world from section: $it"
//    }
//
//    fun setIndex(index: Int) {
//        _index.value = index
//    }
}