package com.riftar.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riftar.domain.userdetail.model.UserDetail
import com.riftar.domain.userdetail.usecase.GetUserDetailUseCase
import kotlinx.coroutines.launch

class UserDetailViewModel(private val getUserDetailUseCase: GetUserDetailUseCase) : ViewModel() {
    private val _user = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail>
        get() = _user
    val isLoading: LiveData<Boolean>
        get() = mIsLoading
    private val mIsLoading = MutableLiveData<Boolean>()
    val errorMessage: LiveData<String>
        get() = mErrorMessage
    private val mErrorMessage = MutableLiveData<String>()

    fun getUserDetail(userName: String) {
        viewModelScope.launch {
            mIsLoading.value = true
            val result = getUserDetailUseCase.invoke(userName)
            result.onSuccess { data ->
                _user.value = data
            }.onFailure { exception ->
                mErrorMessage.value = exception.message
            }
            mIsLoading.value = false
        }
    }

    fun saveNotes(notes: String) {
//        TODO("Not yet implemented")
    }
}