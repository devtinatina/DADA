package com.example.dada.board

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.dada.R
import com.example.dada.databinding.ActivityBoardInsideBinding
import com.example.dada.fragments.ProfileFragment
import com.example.dada.utils.FBAuth
import com.example.dada.utils.FBRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class BoardInsideActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardInsideBinding

    private val TAG = BoardInsideActivity::class.java.simpleName

    private lateinit var key: String

    private lateinit var imgPath : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBoardInsideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boardSettingIcon.setOnClickListener {
            showDialog()
        }

        binding.shareBtn.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_SEND)
                val uri: Uri = Uri.parse(imgPath)

                intent.type = ("image/*")
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                startActivity(Intent.createChooser(intent, "이미지 공유"))

            } catch (ignored: ActivityNotFoundException) {
                Log.d("test", "ignored : $ignored")
            }
        }

        key = intent.getStringExtra("key").toString()

        getBoardData(key)
        getImageData(key)
    }

    private fun showDialog(){

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        val alterDialog = mBuilder.show()
        alterDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {

        }
        alterDialog.findViewById<Button>(R.id.deleteBtn)?.setOnClickListener {
            FBRef.boardRef.child(key).removeValue()
        }
    }

    private fun getBoardData(key : String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dataModel = dataSnapshot.getValue(BoardModel::class.java)

                binding.insideBoardDatetime.text = dataModel!!.time
                binding.insideBoardContent.text = dataModel!!.content

                val myUid = FBAuth.getUid()
                val writerUid = dataModel.uid

                imgPath = dataModel!!.imgUrl

                if(myUid.equals(writerUid)){
                    binding.boardSettingIcon.isVisible = true
                } else {

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost::onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }

    private fun getImageData(key : String) {

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key)

        // ImageView in your Activity
        val imageViewFromFB = binding.insideBoardImage

        val ONE_MEGABYTE: Long = 1024 * 1024
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if(task.isSuccessful) {
                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)
            } else {

            }

        })
    }
}