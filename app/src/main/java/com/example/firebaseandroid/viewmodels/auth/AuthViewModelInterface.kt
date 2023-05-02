package com.example.firebaseandroid.viewmodels.auth

import com.example.firebaseandroid.models.User

interface AuthViewModelInterface {
    var email: String
    var password: String

    fun register(onSuccess: (result: User) -> Unit, onFailure: (exception: Exception) -> Unit)
    fun login(onSuccess: (result: User) -> Unit, onFailure: (exception: Exception) -> Unit)
    fun isUserLoggedIn(onSuccess: (isLoggedIn: Boolean) -> Unit, onFailure: (exception: Exception) -> Unit)
    fun recoverPassword(onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit)
    fun getUser(onSuccess: (result: User) -> Unit, onFailure: (exception: Exception) -> Unit)
    fun logout(onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit)
}