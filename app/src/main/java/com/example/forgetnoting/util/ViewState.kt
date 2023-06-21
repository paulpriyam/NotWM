package com.example.forgetnoting.util

sealed class ViewState {

    object Loading : ViewState()

    object Cancel : ViewState()

    data class Error(val errorMsg: String? = null) : ViewState()

    object Success:ViewState()
}