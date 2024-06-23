package com.kifee.authapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kifee.authapp.databinding.ActivitySignInBinding
import com.kifee.authapp.databinding.ActivitySignUpBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() // hide action bar

        auth = Firebase.auth


        binding.btnSignIn.setOnClickListener {

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            // if all fields are filled correctly
            if (checkAllField()){
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    // if the account exists
                    if (it.isSuccessful){
                        Toast.makeText(this, "Sign In successful", Toast.LENGTH_SHORT).show()
                        // go to another activity

                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)

                        finish() //destroy the activity
                    }else{
                        // if sign in failed
                        Toast.makeText(this, ""+ it.exception?.message,Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }

        binding.tvCreateAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkAllField(): Boolean {
        val email = binding.etEmail.text.toString()

        if (binding.etEmail.text.toString() == "") {
            binding.textInputLayoutEmail.error = "This is a required field"
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textInputLayoutEmail.error = "Check email format"
            return false
        }

        if (binding.etPassword.text.toString() == "") {
            binding.textInputLayoutPassword.error = "This is a required field"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }

        if (binding.etPassword.length() < 6) {
            binding.textInputLayoutPassword.error = "Password at least 8 characters"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false

        }
        return true
    }
}