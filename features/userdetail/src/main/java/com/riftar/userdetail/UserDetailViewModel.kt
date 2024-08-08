package com.riftar.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riftar.domain.userdetail.model.UserDetail
import com.riftar.domain.userdetail.usecase.GetUserDetailUseCase
import com.riftar.domain.userdetail.usecase.SaveNotesUseCase
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val saveNotesUseCase: SaveNotesUseCase
) : ViewModel() {
    private val _user = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail>
        get() = _user
    private val _isSaveNoteSuccess = MutableLiveData<Boolean>()
    val isSaveNoteSuccess: LiveData<Boolean>
        get() = _isSaveNoteSuccess

    val isLoading: LiveData<Boolean>
        get() = mIsLoading
    private val mIsLoading = MutableLiveData<Boolean>()
    val errorMessage: LiveData<String>
        get() = mErrorMessage
    private val mErrorMessage = MutableLiveData<String>()

    fun getUserDetail(userId: Int, userName: String) {
        viewModelScope.launch {
            mIsLoading.value = true
            val result = getUserDetailUseCase.invoke(userId, userName)
            result.onSuccess { data ->
                _user.value = data
            }.onFailure { exception ->
                mErrorMessage.value = exception.message
            }
            mIsLoading.value = false
        }
    }

    fun saveNotes(notes: String) {
        viewModelScope.launch {
            mIsLoading.value = true
            val result = saveNotesUseCase.invoke(userDetail.value?.id ?: 0, notes)
            result.onSuccess { isSuccess ->
                _isSaveNoteSuccess.value = isSuccess
            }.onFailure { exception ->
                _isSaveNoteSuccess.value = false
                mErrorMessage.value = exception.message
            }
            mIsLoading.value = false
        }
    }
}