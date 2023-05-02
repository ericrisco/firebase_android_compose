package com.example.firebaseandroid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.firebaseandroid.Routes
import com.example.firebaseandroid.models.Tweet
import com.example.firebaseandroid.viewmodels.auth.DummyAuthViewModel
import com.example.firebaseandroid.viewmodels.tweets.TweetsViewModelInterface
import com.example.firebaseandroid.viewmodels.storage.StorageViewModelInterface
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import com.example.firebaseandroid.ui.screens.wallscreen.TweetForm
import com.example.firebaseandroid.ui.screens.wallscreen.TweetList
import com.example.firebaseandroid.viewmodels.auth.AuthViewModelInterface
import com.example.firebaseandroid.viewmodels.log.LogViewModelInterface


@Composable
fun WallScreen(tweetsViewModel: TweetsViewModelInterface,
               authViewModel: AuthViewModelInterface,
               storageViewModel: StorageViewModelInterface,
               navController: NavController,
               logViewModel: LogViewModelInterface
) {

    val screenName = "WallScreen"
    val (showErrorDialog, setShowErrorDialog) = remember { mutableStateOf(false) }
    val (errorMessage, setErrorMessage) = remember { mutableStateOf("") }
    val tweets = fetchTweetsAsState(tweetsViewModel, setErrorMessage, setShowErrorDialog, logViewModel)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Home") },
                actions = {
                    TextButton(onClick = {
                        authViewModel.logout(onSuccess = {
                            navController.navigate(Routes.AUTH_SCREEN)
                        }, onFailure = { exception ->
                            logViewModel.crash(screenName, exception)
                        })
                    }) {
                        Text(
                            text = "Logout",
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = {
            Box(modifier = Modifier
                .fillMaxSize()
            ) {
                Column {
                    TweetList(tweets, modifier = Modifier.weight(3f))
                    TweetForm(
                        tweetsViewModel,
                        authViewModel,
                        storageViewModel,
                        logViewModel,
                        setErrorMessage,
                        setShowErrorDialog,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    )

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { setShowErrorDialog(false) },
            title = { Text(text = "Error") },
            text = { Text(text = errorMessage) },
            confirmButton = {
                TextButton(onClick = { setShowErrorDialog(false) }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun fetchTweetsAsState(
    tweetsViewModel: TweetsViewModelInterface,
    setErrorMessage: (String) -> Unit,
    setShowErrorDialog: (Boolean) -> Unit,
    logViewModel: LogViewModelInterface
): List<Tweet> {
    val screenName = "WallScreen"
    val (tweets, setTweets) = remember { mutableStateOf(emptyList<Tweet>()) }

    tweetsViewModel.fetchTweets(
        onSuccess = { fetchedTweets -> setTweets(fetchedTweets) },
        onFailure = { exception -> {
            setErrorMessage(exception.message ?: "Unknown error")
            setShowErrorDialog(true)
            logViewModel.crash(screenName, exception)
        } }
    )

    return tweets
}
