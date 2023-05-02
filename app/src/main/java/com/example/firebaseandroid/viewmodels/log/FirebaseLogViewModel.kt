package com.example.firebaseandroid.viewmodels.log

import androidx.lifecycle.ViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

class FirebaseLogViewModel(): ViewModel(), LogViewModelInterface {

    private val firebaseAnalytics = Firebase.analytics
    private val firebaseCrashlytics = Firebase.crashlytics

    override fun log(screen: String, action: String) {
        firebaseAnalytics.logEvent(action){
            param(FirebaseAnalytics.Param.SCREEN_NAME, screen)
        }
    }

    override fun crash(screen: String, exception: Exception) {
        firebaseCrashlytics.setCustomKey("Screen", screen)
        firebaseCrashlytics.recordException(exception)
    }

}