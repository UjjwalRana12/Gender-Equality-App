package com.android.messenger

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.play.core.integrity.e
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class MainActivity : AppCompatActivity() {

    lateinit var regis_username: EditText
    lateinit var regis_email: EditText
    lateinit var regis_password: EditText
    lateinit var regis_button: Button
    lateinit var regis_tv: TextView
    lateinit var img_button:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements after setContentView
        regis_username = findViewById(R.id.regis_tv_username)
        regis_email = findViewById(R.id.regis_tv_email)
        regis_password = findViewById(R.id.regis_tv_pass)
        regis_button = findViewById(R.id.regis_button)
        regis_tv = findViewById(R.id.regis_tv)
        img_button=findViewById(R.id.img_save_btn)

        regis_button.setOnClickListener {
           performRegister()
        }

        regis_tv.setOnClickListener {
            val intent = Intent(this, LoginActivity2::class.java)
            startActivity(intent)
        }

        img_button.setOnClickListener{
            val intent=Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,0)
        }

    }
    var SelectedPhotoUri:Uri?=null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {

            SelectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, SelectedPhotoUri)
            img_button.setImageBitmap(bitmap)
//          the method down there also work in case of simple image view without adding any dependencies
//            val bitmapDrawable = BitmapDrawable(resources, bitmap)
//            img_button.background = bitmapDrawable

        }
    }


    private fun uploadImageToFBStorage() {
        if(SelectedPhotoUri==null) return
        val filename=UUID.randomUUID().toString()
       val ref= FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(SelectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("MainActivity","uccessfully uploaded imafe:${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("MainActivity","file location $it")
                }
                saveUserToFBDataBase(it.toString())
            }
    }

    private fun saveUserToFBDataBase(profileImageUrl:String) {
        val uid =FirebaseAuth.getInstance().uid?:""
       val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
       val user=User(uid,regis_username.text.toString(),profileImageUrl)
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("MainActivity","user saved successfully in firebase")
                Toast.makeText(this,"user saved successfully in firebase",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this,"user NOT saved successfully in firebase",Toast.LENGTH_SHORT).show()
                Log.e("MainActivity", "Failed to save user to Firebase:")
            }
    }
    class User(val uid:String,val username:String,val profileImageUrl:String)

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
                uploadImageToFBStorage()

                Log.d("MainActivity","successfully created with uid: ${it.result.user?.uid}")
                Toast.makeText(this,"successfully created with uid: ${it.result.user?.uid}",Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener{
                Log.d("MainActivity","failed to create user:${it.message}")

            }
    }
}
