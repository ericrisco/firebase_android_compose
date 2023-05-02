package com.example.firebaseandroid.viewmodels.log

import androidx.lifecycle.ViewModel

class DummyLogViewModel : ViewModel(), LogViewModelInterface {
    override fun log(screen: String, action: String) {
        println("Log - Screen: $screen, Action: $action")
    }

    override fun crash(screen: String, exception: Exception) {
        println("Crash - Screen: $screen, Exception: ${exception.message}")
    }
}