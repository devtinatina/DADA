package com.example.dada

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dada.auth.IntroActivity
import com.example.dada.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class SplashActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashBinding;

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val executor = Executors.newSingleThreadScheduledExecutor()

        if(auth.currentUser?.uid == null) {
            executor.schedule({startActivity(Intent(this, IntroActivity::class.java))}, 2, TimeUnit.SECONDS)
        } else {
            executor.schedule({startActivity(Intent(this, MainActivity::class.java))}, 2, TimeUnit.SECONDS)
        }
    }
}