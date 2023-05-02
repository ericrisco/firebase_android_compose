package com.example.firebaseandroid.ui.screens.wallscreen

import android.Manifest
import android.graphics.Bitmap
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.firebaseandroid.models.Tweet
import com.example.firebaseandroid.models.TweetType
import com.example.firebaseandroid.viewmodels.auth.AuthViewModelInterface
import com.example.firebaseandroid.viewmodels.log.LogViewModelInterface
import com.example.firebaseandroid.viewmodels.storage.StorageViewModelInterface
import com.example.firebaseandroid.viewmodels.tweets.TweetsViewModelInterface
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun TweetForm(
    tweetsViewModel: TweetsViewModelInterface,
    authViewModel: AuthViewModelInterface,
    storageViewModel: StorageViewModelInterface,
    logViewModel: LogViewModelInterface,
    setErrorMessage: (String) -> Unit,
    setShowErrorDialog: (Boolean) -> Unit,
    modifier: Modifier
) {
    val screenName = "TweetForm"
    val (tweetText, setTweetText) = remember { mutableStateOf("") }

    val takePictureLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            sendTweet(
                bitmap = bitmap,
                tweetType = TweetType.IMAGE,
                tweetsViewModel = tweetsViewModel,
                authViewModel = authViewModel,
                storageViewModel = storageViewModel,
                logViewModel = logViewModel,
                setErrorMessage = setErrorMessage,
                setShowErrorDialog = setShowErrorDialog,
                setTweetText = setTweetText
            )
        } else {
            val errorMessage = "Error taking picture.";
            setErrorMessage(errorMessage)
            setShowErrorDialog(true)
            logViewModel.crash(screenName, Exception(errorMessage))
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            takePictureLauncher.launch(null)
        } else {
            val errorMessage = "Permission for camera is denied.";
            setErrorMessage(errorMessage)
            setShowErrorDialog(true)
            logViewModel.crash(screenName, Exception(errorMessage))
        }
    }

    Column(modifier = modifier
        .padding(horizontal = 2.dp, vertical = 2.dp)
        .imePadding()
    ) {
        Row(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value = tweetText,
                onValueChange = { setTweetText(it) },
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
                    .fillMaxWidth()
                    .heightIn(min = 120.dp, max = 120.dp),
                label = { Text(text = "Write your tweet") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {}),
                maxLines = Int.MAX_VALUE,
                singleLine = false
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = {
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                },
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.AccountBox,
                        contentDescription = "Image button",
                        modifier = Modifier.size(24.dp)
                    )
                    Text("Send Image")
                }
            }

            TextButton(
                onClick = {
                    sendTweet(
                        tweetText = tweetText,
                        tweetType = TweetType.TEXT,
                        tweetsViewModel = tweetsViewModel,
                        authViewModel = authViewModel,
                        storageViewModel = storageViewModel,
                        logViewModel = logViewModel,
                        setErrorMessage = setErrorMessage,
                        setShowErrorDialog = setShowErrorDialog,
                        setTweetText = setTweetText
                    )
                },
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Send tweet button",
                        modifier = Modifier.size(24.dp)
                    )
                    Text("Send")
                }
            }
        }
    }
}

private fun sendTweet(
    tweetText: String? = null,
    bitmap: Bitmap? = null,
    tweetType: TweetType,
    tweetsViewModel: TweetsViewModelInterface,
    authViewModel: AuthViewModelInterface,
    storageViewModel: StorageViewModelInterface,
    logViewModel: LogViewModelInterface,
    setErrorMessage: (String) -> Unit,
    setShowErrorDialog: (Boolean) -> Unit,
    setTweetText: (String) -> Unit
) {
    val screenName = "sendTweet"

    authViewModel.getUser(
        onSuccess = { user ->
            val id = UUID.randomUUID().toString()

            val calendar = Calendar.getInstance()
            val formatter = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
            val formattedDateTime = formatter.format(calendar.time)

            when (tweetType) {
                TweetType.TEXT -> {

                    val tweet = Tweet(
                        id = id,
                        userName = user.email,
                        type = tweetType,
                        message = if(tweetText.isNullOrEmpty()) " " else tweetText,
                        timestamp = formattedDateTime
                    )

                    tweetsViewModel.postTweet(
                        tweet = tweet,
                        onSuccess = {
                            setTweetText("")
                            logViewModel.log(screenName, "TWEET_MESSAGE_POSTED")
                        },
                        onFailure = { exception ->
                            setErrorMessage(exception.message ?: "Unknown error")
                            setShowErrorDialog(true)
                            logViewModel.crash(screenName, exception)
                        }
                    )
                }
                TweetType.IMAGE -> {

                    if(bitmap != null) {

                        storageViewModel.uploadImage(
                            bitmap,
                            onSuccess = { uri ->
                                val tweet = Tweet(
                                    id = id,
                                    userName = user.email,
                                    type = tweetType,
                                    message = uri,
                                    timestamp = formattedDateTime
                                )
                                tweetsViewModel.postTweet(
                                    tweet = tweet,
                                    onSuccess = {
                                        setTweetText("")
                                        logViewModel.log(screenName, "TWEET_IMAGE_POSTED")
                                    },
                                    onFailure = { exception ->
                                        setErrorMessage(exception.message ?: "Unknown error")
                                        setShowErrorDialog(true)
                                        logViewModel.crash(screenName, exception)
                                    }
                                )
                            },
                            onFailure = { exception ->
                                setErrorMessage(exception.message ?: "Unknown error")
                                setShowErrorDialog(true)
                                logViewModel.crash(screenName, exception)
                            }
                         )
                    }
                }
            }

        },
        onFailure = { exception ->
            setErrorMessage(exception.message ?: "Unknown error")
            setShowErrorDialog(true)
            logViewModel.crash(screenName, exception)
        }
    )
}