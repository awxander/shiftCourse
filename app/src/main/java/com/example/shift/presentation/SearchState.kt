package com.example.shift.presentation

import com.example.shift.data.BinInfoModel

sealed interface SearchState{

    object Initial: SearchState

    object Loading: SearchState

    data class Content(val binInfoModel: BinInfoModel) : SearchState

    data class Error(val text: String) : SearchState
}
