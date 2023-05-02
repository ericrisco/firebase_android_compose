package com.example.firebaseandroid.viewmodels.tweets

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.firebaseandroid.models.Tweet
import com.example.firebaseandroid.viewmodels.storage.StorageViewModelInterface
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FirestoreTweetsViewModel: TweetsViewModelInterface, ViewModel() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val tweetsCollection = db.collection("tweets")

    override fun fetchTweets(onSuccess: (List<Tweet>) -> Unit, onFailure: (Exception) -> Unit) {
        tweetsCollection
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    onFailure(exception)
                    return@addSnapshotListener
                }

                val tweets = querySnapshot?.documents?.mapNotNull { document ->
                    document.toObject(Tweet::class.java)
                } ?: emptyList()
                onSuccess(tweets)
            }
    }

    override fun postTweet(tweet: Tweet, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        tweetsCollection
            .add(tweet)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

}