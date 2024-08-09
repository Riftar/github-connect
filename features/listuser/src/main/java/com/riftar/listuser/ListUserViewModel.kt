package com.riftar.listuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.riftar.domain.listuser.model.User
import com.riftar.domain.listuser.usecase.GetListUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

class ListUserViewModel(private val getListUserUseCase: GetListUserUseCase) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun getListUser(): Flow<PagingData<User>> {
        return _searchQuery
            .debounce(1000)
            .flatMapLatest { query ->
                getListUserUseCase.invoke(query)
            }
            .cachedIn(viewModelScope)
    }

}