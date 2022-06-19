package com.example.dada.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.dada.R
import com.example.dada.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class BoardListLVAdapter2 (val boardList : MutableList<BoardModel2>) : BaseAdapter() {

    override fun getCount(): Int {
        return boardList.size
    }

    override fun getItem(position: Int): Any {
        return boardList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView

        if (view == null) {

            view = LayoutInflater.from(parent?.context).inflate(R.layout.board_list_item2, parent, false)

        }

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dataModel = dataSnapshot.getValue(BoardModel2::class.java)

                val image = view?.findViewById<ImageView>(R.id.boardListImageArea2)
                if (parent != null){
                    Glide.with(parent.context)
                        .load(dataModel!!.imgUrl)
                        .into(image!!)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        FBRef.instaRef.child(boardList[position].key).addValueEventListener(postListener)

        return view!!
    }

}