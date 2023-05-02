package com.example.firebaseandroid.viewmodels.storage

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*

class FirebaseStorageViewModel: ViewModel(), StorageViewModelInterface {
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    override fun uploadImage(
        image: Bitmap,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val uuid = UUID.randomUUID().toString()
        val imageRef = storageRef.child("images/$uuid.jpg")

        val byteArray = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 50, byteArray)
        val imageData = byteArray.toByteArray()

        val uploadTask = imageRef.putBytes(imageData)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                onSuccess(downloadUri.toString())
            } else {
                onFailure(task.exception!!)
            }
        }
    }

}