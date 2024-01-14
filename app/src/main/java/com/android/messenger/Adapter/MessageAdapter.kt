package com.android.messenger.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.messenger.DataClass.Message
import com.android.messenger.R

class MessageAdapter(private var chatlist:List<Message>):RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val Row_Image:ImageView=itemView.findViewById(R.id.row_imageview)
        val Row_Text:TextView=itemView.findViewById(R.id.row_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_message_layout,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chatlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val Chatlist=chatlist[position]
        holder.apply {
            Row_Text.text=Chatlist.Person
            Row_Image.setImageResource(Chatlist.Image)
        }
    }
}