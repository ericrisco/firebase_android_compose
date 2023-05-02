package com.example.firebaseandroid.viewmodels.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.firebaseandroid.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthViewModel: AuthViewModelInterface, ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

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

    override fun register(
        onSuccess: (result: User) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess(User(id = auth.currentUser?.uid ?: "", email = email))
                } else {
                    onFailure(task.exception ?: Exception("Unknown exception."))
                }
            }
    }

    override fun login(
        onSuccess: (result: User) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess(User(id = auth.currentUser?.uid ?: "", email = email))
                } else {
                    onFailure(task.exception ?: Exception("Unknown exception."))
                }
            }
    }

    override fun isUserLoggedIn(
        onSuccess: (isLoggedIn: Boolean) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        val currentUser = auth.currentUser
        onSuccess(currentUser != null)
    }

    override fun recoverPassword(onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception ?: Exception("Unknown exception."))
                }
            }
    }

    override fun getUser(
        onSuccess: (result: User) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            onSuccess(User(id = currentUser.uid, email = currentUser.email ?: ""))
        } else {
            onFailure(Exception("User is not logged in."))
        }
    }

    override fun logout(onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit) {
        try {
            auth.signOut()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}