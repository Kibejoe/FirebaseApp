package com.kifee.authapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kifee.authapp.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        auth = Firebase.auth
        binding.tvResetPassword.setOnClickListener {
            val email = binding.etEmail.text.toString()

            if (checkEmail()){
                auth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful){
                        // email is sent

                        Toast.makeText(this,"Link sent to email", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    else{
                        Toast.makeText(this, ""+ it.exception?.message,Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

    private fun checkEmail(): Boolean{
        val email = binding.etEmail.text.toString()

        if (binding.etEmail.text.toString() == "") {
            binding.textInputLayoutEmail.error = "This is a required field"
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textInputLayoutEmail.error = "Check email format"
            return false
        }
        return true
    }
}