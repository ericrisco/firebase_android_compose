package com.example.firebaseandroid.viewmodels.storage

import android.graphics.Bitmap

interface StorageViewModelInterface {
    fun uploadImage(image: Bitmap, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit)
}