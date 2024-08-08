package com.riftar.listuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.riftar.domain.listuser.usecase.GetListUserUseCase

class ListUserViewModel(private val getListUserUseCase: GetListUserUseCase) : ViewModel() {
    //TODO add pull to refresh
    fun getListUser() = getListUserUseCase.invoke().cachedIn(viewModelScope)
}