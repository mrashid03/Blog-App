<b> Overview </b>

The Blog App is a simple yet powerful application that allows users to sign up, log in, create, and manage blog posts. Built with Kotlin for the backend logic and utilizing Firebase for database storage, this app provides a seamless experience for users to share their thoughts and engage with others through likes.

Features

User Authentication: Secure sign-up and login functionality using Firebase Authentication. Blog Management: Create, edit, and delete blog posts with a user-friendly interface. Like System: Users can like posts, save the blogs, and the total likes are stored and displayed. Firebase Database: All blog data is stored in Firebase Firestore, providing real-time synchronization and data persistence. Technologies Used Backend: Kotlin Database: Firebase Firestore Authentication: Firebase Authentication, Firebase Storage, Realtime Database Android Development: XML, Coroutines, Recycler View Getting Started Prerequisites Android Studio installed Firebase account with a Firestore database set up

Installation

Clone the Repository: git clone https://github.com/mrashid03/Blog-App.git cd blog-app
Set Up Firebase:
-> Go to the Firebase Console. -> Create a new project and enable Firestore. -> Add your Android app to the project and download the google-services.json file. -> Place the google-services.json file in the app/ directory of your project.

3.Add Dependencies: Open the build.gradle (Module) file and add the following dependencies:

implementation platform('com.google.firebase:firebase-bom:31.1.0') implementation 'com.google.firebase:firebase-auth-ktx' implementation 'com.google.firebase:firebase-firestore-ktx' implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0' implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0'

Sync Gradle: Click "Sync Now" to install the dependencies.
Configuration

Set Up Authentication:
-> In the Firebase Console, navigate to the Authentication section. -> Enable Email/Password sign-in method.

Firestore Rules: Set your Firestore security rules to allow read/write access for authenticated users:
service cloud.firestore { match /databases/{database}/documents { match /{document=**} { allow read, write: if request.auth != null; } } }

Running the App

Connect an Android device or start an emulator.
Open the project in Android Studio.
Run the application using the "Run" button or by selecting Run > Run 'app'.
Usage

Sign Up: Create a new account using a valid email and password.
Login: Use your credentials to log in to the app.
Create Blog Post: Navigate to the create blog section to write and publish your posts.
Like Posts: Browse blog posts and click the like button to show your appreciation.
Contributing We welcome contributions! If you would like to help improve the app, please fork the repository and submit a pull request. Ensure that your code adheres to our coding standards and includes appropriate tests.

Acknowledgments -> Firebase for the backend infrastructure. -> Kotlin for the backend logic implementation.

Contact For any questions or suggestions, please contact official.rashid25@gmail.com.
