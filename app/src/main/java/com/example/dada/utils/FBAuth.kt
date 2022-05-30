package com.example.dada.utils

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class FBAuth {

    companion object {

        private lateinit var auth: FirebaseAuth

        fun getUid() : String {

            auth = FirebaseAuth.getInstance()

            return auth.currentUser?.uid.toString()
        }

        fun getTime() : String {

            val currentDataTime = Calendar.getInstance().time
            val dataFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA).format(currentDataTime)

            return dataFormat
        }
    }

}