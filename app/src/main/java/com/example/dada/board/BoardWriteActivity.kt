package com.example.dada.board

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.dada.databinding.ActivityBoardWriteBinding
import com.example.dada.utils.FBAuth
import com.example.dada.utils.FBRef
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class BoardWriteActivity : AppCompatActivity() {

    lateinit var binding: ActivityBoardWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBoardWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.uploadBtn.setOnClickListener{

            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()

            val key = FBRef.boardRef.push().key.toString()

            FBRef.boardRef
                .child(key)
                .setValue(BoardModel(content, uid, time, key))

            imageUpload(key)

            finish()
        }

        binding.getImageFromAlbumBtn.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
        }
    }

    private fun imageUpload(key : String){
        // Get the data from an ImageView as bytes
        val storage = Firebase.storage
        val storageRef = storage.reference
        val mountainsRef = storageRef.child(key)

        val imageView = binding.getImageFromAlbumBtn
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener {
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK && requestCode == 100) {
            binding.getImageFromAlbumBtn.setImageURI(data?.data)
            binding.getImageFromCamera.setImageResource(0)
        }
    }

}