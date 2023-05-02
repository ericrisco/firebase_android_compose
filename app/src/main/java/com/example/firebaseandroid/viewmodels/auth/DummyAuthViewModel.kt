package com.example.firebaseandroid.viewmodels.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseandroid.models.User
import kotlinx.coroutines.*

class DummyAuthViewModel : AuthViewModelInterface, ViewModel() {
    private var _email: String by mutableStateOf("")
    override var email: String
        get() = _email
        set(value) {
            _email = value
        }

    private var _password: String by mutableStateOf("")
    override var password: String
        get() = _password
        set(value) {
            _password = value
        }

    override fun register(onSuccess: (result: User) -> Unit, onFailure: (exception: Exception) -> Unit) {
        viewModelScope.launch {
            val result = async {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    User(id = "123", email = email)
                } else {
                    throw Exception("Email or password is empty.")
                }
            }
            try {
                onSuccess(result.await())
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    override fun login(onSuccess: (result: User) -> Unit, onFailure: (exception: Exception) -> Unit) {
        register(onSuccess, onFailure)
    }

    override fun isUserLoggedIn(onSuccess: (isLoggedIn: Boolean) -> Unit, onFailure: (exception: Exception) -> Unit) {
        onSuccess(false)
    }

    override fun recoverPassword(onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit) {
        viewModelScope.launch {
            try {
                onSuccess()
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    override fun getUser(onSuccess: (result: User) -> Unit, onFailure: (exception: Exception) -> Unit) {
        viewModelScope.launch {
            val result = async {
                User(id = "123", email = email)
            }
            try {
                onSuccess(result.await())
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    override fun logout(onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit) {
        onSuccess()
    }
}