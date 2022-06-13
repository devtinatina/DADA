package com.example.dada.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dada.MainActivity
import com.example.dada.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {

    lateinit var binding : ActivitySignupBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.SignUpBtn.setOnClickListener {
            val email = binding.SignUpEmailInput.text.toString()
            val password = binding.SignUpPwInput.text.toString()
            val passwordCk = binding.SignUpPwCheckInput.text.toString()

            var signUpFlag = true

            if(email.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_LONG).show()
                signUpFlag = false
            }

            if(password.isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
                signUpFlag = false
            }

            if(passwordCk.isEmpty()) {
                Toast.makeText(this, "비밀번호 확인을 입력해주세요", Toast.LENGTH_LONG).show()
                signUpFlag = false
            }

            if(password != passwordCk) {
                Toast.makeText(this, "비밀번호 확인이 다릅니다", Toast.LENGTH_LONG).show()
                signUpFlag = false
            }

            if(password.length < 8) {
                Toast.makeText(this, "비밀번호를 8자리 이상으로 입력해주세요", Toast.LENGTH_LONG).show()
                signUpFlag = false
            }

            if(signUpFlag) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "성공", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "실패", Toast.LENGTH_LONG).show()
                        }
                    }
            }

        }
    }
}