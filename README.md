<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blog App README</title>
    <style>
        body { font-family: Arial, sans-serif; line-height: 1.6; margin: 20px; }
        h1, h2, h3, h4 { color: #333; }
        pre { background: #f4f4f4; padding: 10px; border-radius: 5px; }
        code { background: #f4f4f4; padding: 2px 4px; border-radius: 3px; }
        a { color: #007BFF; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>

    <h1>Blog App</h1>

    <h2>Overview</h2>
    <p>The Blog App is a simple yet powerful application that allows users to sign up, log in, create, and manage blog posts. Built with Kotlin for the backend logic and utilizing Firebase for database storage, this app provides a seamless experience for users to share their thoughts and engage with others through likes.</p>

    <h2>Features</h2>
    <ul>
        <li><strong>User Authentication</strong>: Secure sign-up and login functionality using Firebase Authentication.</li>
        <li><strong>Blog Management</strong>: Create, edit, and delete blog posts with a user-friendly interface.</li>
        <li><strong>Like System</strong>: Users can like posts, and the total likes are stored and displayed.</li>
        <li><strong>Firebase Database</strong>: All blog data is stored in Firebase Firestore, providing real-time synchronization and data persistence.</li>
    </ul>

    <h2>Technologies Used</h2>
    <ul>
        <li><strong>Backend</strong>: Kotlin</li>
        <li><strong>Database</strong>: Firebase Firestore</li>
        <li><strong>Authentication</strong>: Firebase Authentication</li>
        <li><strong>Android Development</strong>: Jetpack components, Coroutines</li>
    </ul>

    <h2>Getting Started</h2>

    <h3>Prerequisites</h3>
    <ul>
        <li>Android Studio installed</li>
        <li>Firebase account with a Firestore database set up</li>
    </ul>

    <h3>Installation</h3>
    <ol>
        <li><strong>Clone the Repository</strong>:
            <pre><code>git clone https://github.com/yourusername/blog-app.git
cd blog-app</code></pre>
        </li>
        <li><strong>Set Up Firebase</strong>:
            <ul>
                <li>Go to the <a href="https://console.firebase.google.com/">Firebase Console</a>.</li>
                <li>Create a new project and enable Firestore.</li>
                <li>Add your Android app to the project and download the <code>google-services.json</code> file.</li>
                <li>Place the <code>google-services.json</code> file in the <code>app/</code> directory of your project.</li>
            </ul>
        </li>
        <li><strong>Add Dependencies</strong>:
            Open the <code>build.gradle</code> (Module) file and add the following dependencies:
            <pre><code>implementation platform('com.google.firebase:firebase-bom:31.1.0')
implementation 'com.google.firebase:firebase-auth-ktx'
implementation 'com.google.firebase:firebase-firestore-ktx'
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0'
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0'</code></pre>
        </li>
        <li><strong>Sync Gradle</strong>: Click "Sync Now" to install the dependencies.</li>
    </ol>

    <h3>Configuration</h3>
    <ol>
        <li><strong>Set Up Authentication</strong>:
            In the Firebase Console, navigate to the Authentication section. Enable Email/Password sign-in method.
        </li>
        <li><strong>Firestore Rules</strong>:
            Set your Firestore security rules to allow read/write access for authenticated users:
            <pre><code>service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}</code></pre>
        </li>
    </ol>

    <h3>Running the App</h3>
    <ol>
        <li>Connect an Android device or start an emulator.</li>
        <li>Open the project in Android Studio.</li>
        <li>Run the application using the "Run" button or by selecting <code>Run > Run 'app'</code>.</li>
    </ol>

    <h2>Usage</h2>
    <ol>
        <li><strong>Sign Up</strong>: Create a new account using a valid email and password.</li>
        <li><strong>Login</strong>: Use your credentials to log in to the app.</li>
        <li><strong>Create Blog Post</strong>: Navigate to the create blog section to write and publish your posts.</li>
        <li><strong>Like Posts</strong>: Browse blog posts and click the like button to show your appreciation.</li>
    </ol>

    <h2>Contributing</h2>
    <p>We welcome contributions! If you would like to help improve the app, please fork the repository and submit a pull request. Ensure that your code adheres to our coding standards and includes appropriate tests.</p>

    <h2>License</h2>
    <p>This project is licensed under the MIT License. See the <a href="LICENSE">LICENSE</a> file for details.</p>

    <h2>Acknowledgments</h2>
    <ul>
        <li><a href="https://firebase.google.com/">Firebase</a> for the backend infrastructure.</li>
        <li><a href="https://kotlinlang.org/">Kotlin</a> for the backend logic implementation.</li>
    </ul>

    <h2>Contact</h2>
    <p>For any questions or suggestions, please contact <a href="mailto:your.email@example.com">your.email@example.com</a>.</p>

</body>
</html>
