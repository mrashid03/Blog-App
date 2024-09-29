package com.example.blogapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Data
import android.widget.Toast
import com.example.blogapp.Model.BlogItemModel
import com.example.blogapp.Model.UserData
import com.example.blogapp.databinding.ActivityAddArticleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date

class AddArticleActivity : AppCompatActivity() {
    private val binding: ActivityAddArticleBinding by lazy {
        ActivityAddArticleBinding.inflate(layoutInflater)
    }

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance("https://blog-app-5e817-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("blogs")
    private val userReference: DatabaseReference = FirebaseDatabase.getInstance("https://blog-app-5e817-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users")
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imageButton.setOnClickListener{
            finish()
        }

        binding.readMoreButton.setOnClickListener {

            val title = binding.blogTitle.editText?.text.toString().trim()
            val description = binding.blogDescription.editText?.text.toString().trim()

            if(title.isEmpty() || description.isEmpty()){
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }

            // get current user
            val user: FirebaseUser? = auth.currentUser

            if(user != null){
                val userId = user.uid
                val userName = user.displayName?: "Anonymous"
                val userImageUrl = user.photoUrl?: ""

                // fetch user name and user profile from database
                userReference.child(userId).addListenerForSingleValueEvent(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userData = snapshot.getValue(UserData::class.java)
                        if(userData != null){
                            val userNameFromDB = userData.name
                            val userImageurlFromDB = userData.profileImage
                            val currentDate = SimpleDateFormat("dd-MM-yyyy").format(Date())
                            val key = databaseReference.push().key

                            // create a blog item model
                            val blogItem = key?.let { it1 ->
                                BlogItemModel(
                                    title,
                                    userNameFromDB,
                                    currentDate,
                                    userId,
                                    post=description,
                                    0,
                                    userImageurlFromDB,
                                    postId = it1
                                )
                            }

                            // generate a unique key for blog post
                            if(key != null){

                                if (blogItem != null) {
                                    blogItem.postId = key
                                }
                                val blogReference = databaseReference.child(key)
                                blogReference.setValue(blogItem).addOnCompleteListener {
                                    if(it.isSuccessful){
                                        finish()
                                    }else{
                                        Toast.makeText(this@AddArticleActivity,"Failed to add Blog", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
            }
        }

    }
}