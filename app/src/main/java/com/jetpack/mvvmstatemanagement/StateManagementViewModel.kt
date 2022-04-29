package com.jetpack.mvvmstatemanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class StateManagementViewModel @Inject constructor(
    private val userService: UserService
): ViewModel() {
    val userState = MutableStateFlow<UserState>(UserState.StartState)

    init {
        fetchUser()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            userState.tryEmit(UserState.LoadingState)
            withContext(Dispatchers.IO) {
                try {
                    val user = userService.fetchUser()
                    userState.tryEmit(UserState.Success(user))
                } catch (e: Exception) {
                    userState.tryEmit(UserState.Error(e.localizedMessage))
                }
            }
        }
    }
}



















