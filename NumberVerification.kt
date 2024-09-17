package com.example.enhanceskills

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class NumberVerification : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_verification)
        auth = FirebaseAuth.getInstance()
        val btn = findViewById<Button>(R.id.button2)
        val number = findViewById<TextInputEditText>(R.id.Phone)
        btn.setOnClickListener(){
            if (number.text!!.isEmpty()){
                Toast.makeText(this, "Please Enter Your Number", Toast.LENGTH_SHORT).show()
            }else{
                val Intent= Intent(this,OTP_verification::class.java)
                Intent.putExtra("Number",number.text!!.toString())
                startActivity(Intent)
            }
        }
    }
}