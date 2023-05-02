# Firebase Course Android Kotlin Project with Jetpack Compose and MVVM Architecture

This repository contains a sample Android Kotlin project developed using Jetpack Compose and the MVVM architectural pattern. The project demonstrates how to integrate various Firebase services, such as Authentication, Analytics, Crashlytics, Cloud Storage, and Firestore, into an app that allows users to log in, view and post tweets with text or images.

## Features

- Login, registration, and password recovery using Firebase Authentication
- Tracking app usage and crashes with Firebase Analytics and Crashlytics
- Viewing a list of tweets related to a user
- Posting new text or image tweets
- Uploading images to Cloud Storage and saving messages to Firestore 
- Receiving Push Notifications

## Project Structure

The project contains four ViewModel interfaces, each corresponding to a specific Firebase service:

- AuthViewModelInterface: Responsible for authentication-related functionality, such as login, registration, and password recovery
- LogViewModelInterface: Handles tracking of application usage and crashes
- StorageViewModelInterface: Manages interactions with Firebase Cloud Storage, such as uploading and retrieving image files
- TweetsViewModelInterface: Handles the creation, retrieval, and storage of tweets

These interfaces allow for easy swapping between dummy implementations and Firebase implementations. Dummy implementations are provided for development and testing purposes, while Firebase implementations are used for production.

## Getting Started

To get started with the project, follow these steps:

1. Clone the repository: git clone https://github.com/yourusername/firebase-course-android-kotlin.git
2. Open the project in Android Studio and sync the Gradle files
3. Set up a Firebase project and connect it to the Android app by following the instructions in the official documentation
4. Enable the required Firebase services (Authentication, Analytics, Crashlytics, Cloud Storage, and Firestore) in the Firebase console
5. Replace the dummy implementations of the ViewModel interfaces with the corresponding Firebase implementations
6. Build and run the app on an emulator or physical device

## Contributions

Feel free to contribute to this project by submitting pull requests or reporting issues. If you have any questions, please open an issue and the maintainers will get back to you as soon as possible.

## License

This project is licensed under the MIT License. For more information, please see the LICENSE file.
