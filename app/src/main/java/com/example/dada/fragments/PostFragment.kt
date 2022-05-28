package com.example.dada.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.dada.R
import com.example.dada.databinding.FragmentPostBinding



class PostFragment : Fragment() {

    private lateinit var binding : FragmentPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(layoutInflater)

        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_postFragment_to_homeFragment)
        }

        binding.profileTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_postFragment_to_profileFragment)
        }

        return binding.root
    }

}