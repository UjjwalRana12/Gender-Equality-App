package com.android.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.android.messenger.Adapter.MessageAdapter
import com.android.messenger.DataClass.Message

class NewMessageActivity : AppCompatActivity() {

    lateinit var recyclerView1: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title="Select User"

        recyclerView1=findViewById(R.id.recyclerView_newMessage)


        val chatList= listOf(
            Message(listOf(R.drawable.set_img),"hello"),
            Message(listOf(R.drawable.set_img),"hello"),
            Message(listOf(R.drawable.set_img),"hello"),

        )

        val adapter = MessageAdapter(chatList)
        recyclerView1.adapter=adapter
    }
}