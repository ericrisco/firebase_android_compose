package com.example.firebaseandroid.models

data class Tweet(
    val id: String,
    val userName: String,
    val type: TweetType,
    val message: String,
    val timestamp: String
    ){
    constructor(): this("", "", TweetType.TEXT, "", "")
}
