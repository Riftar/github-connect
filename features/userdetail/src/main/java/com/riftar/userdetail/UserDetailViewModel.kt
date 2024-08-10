package com.riftar.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riftar.common.helper.NetworkConnectivityObserver
import com.riftar.domain.userdetail.model.UserDetail
import com.riftar.domain.userdetail.usecase.GetUserDetailUseCase
import com.riftar.domain.userdetail.usecase.SaveNotesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val saveNotesUseCase: SaveNotesUseCase,
    private val networkConnectivityObserver: NetworkConnectivityObserver
) : ViewModel() {
    private val _userDetailState = MutableStateFlow<UserDetailState>(UserDetailState.Loading)
    val userDetailState: StateFlow<UserDetailState> = _userDetailState.asStateFlow()

    private val _saveNotesState = MutableStateFlow<SaveNotesState>(SaveNotesState.Initial)
    val saveNotesState: StateFlow<SaveNotesState> = _saveNotesState.asStateFlow()

    private var userId: Int? = null
    private var userName: String? = null

    init {
        observeNetworkConnectivity()
    }

    fun getFlowUserDetail(userId: Int, userName: String) {
        viewModelScope.launch {
            _userDetailState.value = UserDetailState.Loading
            getUserDetailUseCase.invoke(userId, userName)
                .collect { result ->
                    result.onSuccess { data ->
                        _userDetailState.value = UserDetailState.Success(data)
                    }.onFailure { exception ->
                        _userDetailState.value =
                            UserDetailState.Error(exception.message ?: "Unknown error occurred")
                    }

                    // Assign this value after the API call to prevent a double call from
                    // network observer
                    this@UserDetailViewModel.userId = userId
                    this@UserDetailViewModel.userName = userName
                }
        }
    }

    fun saveNotes(userId: Int, notes: String) {
        viewModelScope.launch {
            _saveNotesState.value = SaveNotesState.Loading
            saveNotesUseCase.invoke(userId, notes)
                .collect { result ->
                    result.onSuccess {
                        _saveNotesState.value = SaveNotesState.Success("Success add note!")
                    }.onFailure { exception ->
                        _saveNotesState.value =
                            SaveNotesState.Error(exception.message ?: "Unknown error occurred")
                    }
                }
        }
    }

    private fun observeNetworkConnectivity() {
        viewModelScope.launch {
            networkConnectivityObserver.observe().collect { isConnected ->
                /**
                 *  only call API when userId and userName is not null
                 *  it means after the original API call.
                 */

                if (isConnected && userId != null && userName != null) {
                    getFlowUserDetail(userId!!, userName!!)
                }
            }
        }
    }
}

sealed class UserDetailState {
    data object Loading : UserDetailState()
    data class Success(val userDetail: UserDetail) : UserDetailState()
    data class Error(val message: String) : UserDetailState()
}

sealed class SaveNotesState {
    data object Initial : SaveNotesState()
    data object Loading : SaveNotesState()
    data class Success(val message: String) : SaveNotesState()
    data class Error(val message: String) : SaveNotesState()
}