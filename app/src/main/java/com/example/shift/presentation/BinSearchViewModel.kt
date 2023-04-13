package com.example.shift.presentation

import android.util.Log
import androidx.lifecycle.*
import com.example.shift.TAG
import com.example.shift.data.BinRepository
import kotlinx.coroutines.launch

class BinSearchViewModel(private val repository: BinRepository) : ViewModel() {
    private val _state: MutableLiveData<SearchState> = MutableLiveData(SearchState.Initial)

    val state: LiveData<SearchState> = _state

    fun loadData(binNum: Long) {
        viewModelScope.launch {
            _state.value = SearchState.Loading
            try {
                val binInfoModel = repository.getByNum(binNum)
                Log.i(javaClass.simpleName, "got bin info")
                _state.postValue(SearchState.Content(binInfoModel))
            } catch (e: Exception) {
                Log.e(TAG, e.message.orEmpty())
                _state.postValue(SearchState.Error(e.message.orEmpty()))
            }
        }
    }
}