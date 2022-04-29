package com.jetpack.mvvmstatemanagement

import retrofit2.http.GET

interface UserService {
    @GET("/users")
    suspend fun fetchUser(): List<User>
}