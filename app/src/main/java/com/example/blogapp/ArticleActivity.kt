package com.example.blogapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blogapp.Model.BlogItemModel
import com.example.blogapp.adapter.ArticleAdapter
import com.example.blogapp.databinding.ActivityArticleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ArticleActivity : AppCompatActivity(), OnItemClickListener {
    private val binding: ActivityArticleBinding by lazy{
        ActivityArticleBinding.inflate(layoutInflater)
    }
    private lateinit var databaseReference: DatabaseReference
    private val auth = FirebaseAuth.getInstance()
    private lateinit var blogAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        blogAdapter = ArticleAdapter(this, emptyList(),this)
        val currentUserId = auth.currentUser?.uid
        val recyclerView = binding.articleRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = blogAdapter


        // get saved blog data from database
        databaseReference = FirebaseDatabase.getInstance("https://blog-app-5e817-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("blogs")

        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val blogSavedList = ArrayList<BlogItemModel>()
                for (postSnapshot in snapshot.children){
                    val blogSaved = postSnapshot.getValue(BlogItemModel::class.java)
                    if(blogSaved != null && currentUserId == blogSaved.userId){
                        blogSavedList.add(blogSaved)
                    }
                }
                blogAdapter.setData(blogSavedList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ArticleActivity, "Error loading saved blogs", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemClick(blogItem: BlogItemModel) {
        // Handle the item click here, e.g., open a new activity or show details
        Toast.makeText(this, "Clicked on: ${blogItem.heading}", Toast.LENGTH_SHORT).show()
    }
}

interface OnItemClickListener {
    fun onItemClick(blogItem: BlogItemModel)
}