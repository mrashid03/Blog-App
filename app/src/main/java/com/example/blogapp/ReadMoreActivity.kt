package com.example.blogapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.blogapp.Model.BlogItemModel
import com.example.blogapp.databinding.ActivityReadMoreBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ReadMoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadMoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setOnClickListener{
            finish()
        }

        val blogs = intent.getParcelableExtra<BlogItemModel>("blogItem")
        Log.d("ReadMoreActivity", "Received blog title: ${blogs?.heading}, description : ${blogs?.post}")


        if(blogs != null){

            // retrieve user related data here e.x blog title etc.
            binding.titleText.text = blogs.heading ?: "No title"
            binding.userName.text = blogs.userName ?: "Unknown user"
            binding.date.text = blogs.date ?: "No date"
            binding.blogDescriptionTextView.text =blogs.post

            val userImageUrl = blogs.profileImage
            Glide.with(this)
                .load(userImageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.profileImage)
        }
        else{
            Toast.makeText(this, "Failed to load blogs", Toast.LENGTH_SHORT).show()
        }
    }
}