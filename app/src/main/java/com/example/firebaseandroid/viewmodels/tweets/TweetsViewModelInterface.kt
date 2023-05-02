package com.example.firebaseandroid.viewmodels.tweets

import android.graphics.Bitmap
import com.example.firebaseandroid.models.Tweet
import com.example.firebaseandroid.viewmodels.storage.StorageViewModelInterface

interface TweetsViewModelInterface {
    fun fetchTweets(onSuccess: (List<Tweet>) -> Unit, onFailure: (Exception) -> Unit)
    fun postTweet(tweet: Tweet, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
}