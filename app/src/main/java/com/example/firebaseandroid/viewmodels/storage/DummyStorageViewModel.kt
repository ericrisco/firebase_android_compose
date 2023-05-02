package com.example.firebaseandroid.viewmodels.storage

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception

class DummyStorageViewModel : ViewModel(), StorageViewModelInterface {
    override fun uploadImage(image: Bitmap, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                onSuccess("https://pbs.twimg.com/media/DpGF4aRX4AAdBYw.jpg")
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}