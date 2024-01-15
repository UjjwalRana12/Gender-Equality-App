package com.android.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.messenger.Adapter.MessageAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NewMessageActivity : AppCompatActivity() {

    lateinit var UserrecyclerView: RecyclerView
    lateinit var UserList:ArrayList<MainActivity.User>
    lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        supportActionBar?.show()
        UserrecyclerView=findViewById(R.id.recyclerView_newMessage)
        UserList=ArrayList()
        adapter=MessageAdapter(this,UserList)


        UserrecyclerView.layoutManager= LinearLayoutManager(this)
        UserrecyclerView.adapter=adapter

        supportActionBar?.title="Select User"

        fetchUsers()
    }

    private fun fetchUsers() {
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                UserList.clear()

                p0.children.forEach {
                    Log.d("NewMessageActivite", it.toString())
                    val currentUser = it.getValue(MainActivity.User::class.java)
                    UserList.add(currentUser!!)
                }

                // Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}