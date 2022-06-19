package com.example.dada.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dada.board.BoardInsideActivity2
import com.example.dada.board.BoardListLVAdapter2
import com.example.dada.board.BoardModel2
import com.example.dada.databinding.FragmentPostBinding
import com.example.dada.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class PostFragment : Fragment() {

    private lateinit var binding : FragmentPostBinding

    private val boardDataList = mutableListOf<BoardModel2>()

    private val boardKeyList = mutableListOf<String>()

    private lateinit var boardRVAdapter: BoardListLVAdapter2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(layoutInflater)

        boardRVAdapter = BoardListLVAdapter2(boardDataList)
        binding.boardListView2.adapter = boardRVAdapter

        binding.boardListView2.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(context, BoardInsideActivity2::class.java)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)
        }

        getFBBoardData()

        return binding.root
    }

    private fun getFBBoardData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                boardDataList.clear()

                for (dataModel in dataSnapshot.children) {
                    dataModel.key

                    var item = dataModel.getValue(BoardModel2::class.java)
                    boardDataList.add(item!!)
                    boardKeyList.add(dataModel.key.toString())
                }
                boardKeyList.reverse()
                boardDataList.reverse()
                boardRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        FBRef.instaRef.addValueEventListener(postListener)
    }

}