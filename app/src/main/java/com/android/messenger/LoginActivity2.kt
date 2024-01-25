package com.android.messenger

import LatestMessageFragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity2 : AppCompatActivity() {

    lateinit var login_email: EditText
    lateinit var login_pass: EditText
    lateinit var login_button: Button
    lateinit var login_tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        // Initialize UI elements after setContentView
        login_email = findViewById(R.id.login_et_user)
        login_pass = findViewById(R.id.login_et_pass)
        login_button = findViewById(R.id.login_button)
        login_tv = findViewById(R.id.login_tv)

        login_button.setOnClickListener {
           performLogin()
        }

        login_tv.setOnClickListener {
            finish()
        }
    }

    private fun performLogin() {
        val email = login_email.text.toString()
        val password = login_pass.text.toString()
        Log.d("LoginActivity2", "email is $email")
        Log.d("LoginActivity2", "password is $password")

        if(email.isEmpty()||password.isEmpty()) {
            Toast.makeText(this,"Please enter your email address and password", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(!it.isSuccessful)return@addOnCompleteListener

                else{
                    Log.d("Login","registraion successful")
                    val intent= Intent(this,HomePage::class.java)
                    startActivity(intent)
                }
            }
            .addOnFailureListener{
                Log.d("Login","Something went wrong")
            }
    }
}
