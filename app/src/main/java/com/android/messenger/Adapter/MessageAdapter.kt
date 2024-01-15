package com.android.messenger.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.messenger.MainActivity
import com.android.messenger.R
import com.squareup.picasso.Picasso

class MessageAdapter(val context:Context,private var currentUser: ArrayList<MainActivity.User>):RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val txt_name=itemView.findViewById<TextView>(R.id.row_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_message_layout,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currentUser.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser=currentUser[position]
        holder.txt_name.text=currentUser.username
    }
}