package com.example.blogapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.blogapp.Model.UserData
import com.example.blogapp.databinding.ActivitySignInAndRegistrationBinding
import com.example.blogapp.register.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class SignInAndRegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInAndRegistrationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInAndRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://blog-app-5e817-default-rtdb.asia-southeast1.firebasedatabase.app")
        storage = FirebaseStorage.getInstance("gs://blog-app-5e817.appspot.com")

        // for visibility of fields
        val action = intent.getStringExtra("action")
        //adjust visibility for login
        if(action == "login"){
            binding.loginPassword.visibility = View.VISIBLE
            binding.loginEmailAddress.visibility = View.VISIBLE
            binding.loginButton.visibility = View.VISIBLE

            binding.registerButton.isEnabled = false
            binding.registerButton.alpha = 0.5f
            binding.registerNewHere.isEnabled = false
            binding.registerNewHere.alpha = 0.5f
            binding.registerEmail.visibility = View.GONE
            binding.registerName.visibility = View.GONE
            binding.cardView.visibility = View.GONE
            binding.registerPassword.visibility = View.GONE

            binding.loginButton.setOnClickListener {
                val loginEmail = binding.loginEmailAddress.text.toString()
                val loginPassword = binding.loginPassword.text.toString()
                if(loginEmail.isEmpty() || loginPassword.isEmpty()){
                    Toast.makeText(this, "Please fill all the Details", Toast.LENGTH_SHORT).show()
                }else{
                    auth.signInWithEmailAndPassword(loginEmail, loginPassword)
                        .addOnCompleteListener { task->
                            if(task.isSuccessful){
                                Toast.makeText(this, "Login Successful 😁", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }else{
                                Toast.makeText(this, "Login Failed, Please enter correct Details", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }else if(action == "register"){
            binding.loginButton.isEnabled = false
            binding.loginButton.alpha = 0.5f

            binding.registerButton.setOnClickListener{
                //get data from edit text field
                val registerName = binding.registerName.text.toString()
                val registerEmail = binding.registerEmail.text.toString()
                val registerPassword = binding.registerPassword.text.toString()

                if(registerName.isEmpty() || registerEmail.isEmpty() || registerPassword.isEmpty()){
                    Toast.makeText(this, "Please fill all The Details",Toast.LENGTH_SHORT).show()
                }else{
                    auth.createUserWithEmailAndPassword(registerEmail,registerPassword)
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful){

                                val user = auth.currentUser
                                auth.signOut()
                                user?.let {
                                    // save user data in to Firebase realtime database
                                    val userReference = database.getReference("users")
                                    val userId = user.uid
                                    val userData = UserData(
                                        registerName,
                                        registerEmail
                                    )
                                    userReference.child(userId).setValue(userData)
                                    // upload image to firebase storage
                                    val storageReference = storage.reference.child("profile_image/$userId.jpg")
                                    storageReference.putFile(imageUri!!)
                                        .addOnCompleteListener{ task ->
                                        if(task.isSuccessful){
                                            storageReference.downloadUrl.addOnCompleteListener { imageUri->
                                                if(imageUri.isSuccessful){
                                                    val imageUrl = imageUri.result.toString()

                                                    // save the image url to the realtime database
                                                    userReference.child(userId).child("profileImage").setValue(imageUrl)
                                                }
                                            }
                                        }


                                    }
                                    Toast.makeText(this, "User Register Successfully",Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, WelcomeActivity::class.java))
                                    finish()
                                }
                            }else{
                                Toast.makeText(this,"User Registration failed",Toast.LENGTH_SHORT).show()

                            }
                        }
                }
            }
        }
        // set on clicklistener for choose image
        binding.cardView.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent,"select Image"),
                PICK_IMAGE_REQUEST)
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null)
            imageUri = data.data
            Glide.with(this)
                .load(imageUri)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.registerUserImage)
    }
}