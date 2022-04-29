package com.jetpack.mvvmstatemanagement

sealed class UserState {
    object StartState: UserState()
    object LoadingState: UserState()
    data class Success(val users: List<User>) : UserState()
    data class Error(val errorMessage: String) : UserState()
}
