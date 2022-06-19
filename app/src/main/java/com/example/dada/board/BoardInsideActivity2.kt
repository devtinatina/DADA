package com.example.dada.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.dada.databinding.ActivityBoardInside2Binding
import com.example.dada.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class BoardInsideActivity2 : AppCompatActivity() {

    private lateinit var binding : ActivityBoardInside2Binding

    private lateinit var key: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBoardInside2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        key = intent.getStringExtra("key").toString()

        getBoardData(key)
    }

    private fun getBoardData(key : String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dataModel = dataSnapshot.getValue(BoardModel2::class.java)

                binding.insideBoardDatetime.text = dataModel!!.date
                binding.insideBoardContent.text = dataModel!!.post
                binding.insideBoardlikeArea.text = "좋아요 " + dataModel!!.like.toString()
                Glide.with(this@BoardInsideActivity2)
                    .load(dataModel.imgUrl)
                    .into(binding.insideBoardImage)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        FBRef.instaRef.child(key).addValueEventListener(postListener)
    }
}