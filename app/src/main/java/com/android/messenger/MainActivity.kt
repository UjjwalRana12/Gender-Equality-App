package com.android.messenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var regis_username: EditText
    lateinit var regis_email: EditText
    lateinit var regis_password: EditText
    lateinit var regis_button: Button
    lateinit var regis_tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements after setContentView
        regis_username = findViewById(R.id.regis_tv_username)
        regis_email = findViewById(R.id.regis_tv_email)
        regis_password = findViewById(R.id.regis_tv_pass)
        regis_button = findViewById(R.id.regis_button)
        regis_tv = findViewById(R.id.regis_tv)

        regis_button.setOnClickListener {
           performRegister()
        }

        regis_tv.setOnClickListener {
            val intent = Intent(this, LoginActivity2::class.java)
            startActivity(intent)
        }
    }

    private fun performRegister() {
        val email = regis_email.text.toString()
        val password = regis_password.text.toString()
        val username = regis_username.text.toString()

        if(email.isEmpty()||password.isEmpty()) {
            Toast.makeText(this,"Please enter your email address and password",Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("MainActivity", "password is $password")
        Log.d("MainActivity", "username is $username")
        Log.d("MainActivity", "email is $email")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(!it.isSuccessful)return@addOnCompleteListener

                else{
                    Log.d("MainActivity","successfully created with uid: ${it.result.user?.uid}")
                    Toast.makeText(this,"successfully created with uid: ${it.result.user?.uid}",Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener{
                Log.d("MainActivity","failed to create user:${it.message}")

            }
    }
}
