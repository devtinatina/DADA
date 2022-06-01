package com.example.dada


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dada.databinding.ActivityMainBinding
import com.example.dada.fragments.HomeFragment
import com.example.dada.fragments.PostFragment
import com.example.dada.fragments.ProfileFragment


class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigationBar()
    }


    private fun initNavigationBar() {
        binding.myNavigation.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home_menu -> {
                        supportFragmentManager.beginTransaction()
                            .replace(binding.mainContent.id, HomeFragment()).commit()
                    }
                    R.id.search_menu -> {
                        supportFragmentManager.beginTransaction()
                            .replace(binding.mainContent.id, PostFragment()).commit()
                    }
                    R.id.user_menu -> {
                        supportFragmentManager.beginTransaction()
                            .replace(binding.mainContent.id, ProfileFragment()).commit()
                    }
                }
                true
            }
            selectedItemId = R.id.home_menu
        }
    }
}
