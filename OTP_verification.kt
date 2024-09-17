package com.example.enhanceskills

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OTP_verification : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId : String
    private lateinit var dialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)

        val btn = findViewById<Button>(R.id.button2)
        val otp = findViewById<TextInputEditText>(R.id.Phone)

        auth = FirebaseAuth.getInstance()

        val builder= AlertDialog.Builder(this)
        builder.setMessage("Please wait .....")
        builder.setTitle("Loading")
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()

        val phoneNumber = "+91"+intent.getStringExtra("Number")

        val option = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    dialog.dismiss()
                    Toast.makeText(this@OTP_verification, "Please Try Again ${p0}", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    dialog.dismiss()
                    verificationId = p0
                }
            }).build()
        PhoneAuthProvider.verifyPhoneNumber(option)
        btn.setOnClickListener(){
            if (otp.text!!.isEmpty()){
                Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show()
            }
            else{
                dialog.show()
                val credential = PhoneAuthProvider.getCredential(verificationId, otp.text!!.toString())
                auth.signInWithCredential(credential)
                    .addOnCompleteListener{
                        if (it.isSuccessful){
                            dialog.dismiss()
                            val Intent = Intent(this,REGISTER_USER_::class.java)
                            Toast.makeText(this, "You Now Register Your Self", Toast.LENGTH_SHORT).show()
                            startActivity(Intent)
                            finish()
                        }
                        else{
                            dialog.dismiss()
                            Toast.makeText(this, "Error ${it.exception}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}