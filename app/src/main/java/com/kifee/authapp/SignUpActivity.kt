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
import com.kifee.authapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    // delaying the initialization of a property until a later point,
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root) //signup activity is the main content view bound to the class

        // curly braces appear coz the function is a high order function that can take other functions as parameters
        // Brackets are only used when we want to pass parameters

        supportActionBar?.hide()

        auth = Firebase.auth
        binding.btnSignUp.setOnClickListener {

            if(checkAllFields()){
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    // if successful the account is created

                    if(it.isSuccessful){
                        auth.signOut()
                        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    else{
                        // failed creation of account
                        Toast.makeText(this, ""+ it.exception?.message,Toast.LENGTH_SHORT).show()
                    }

                }
            }

        }

        binding.tvCreateAccount.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkAllFields(): Boolean{
        val email = binding.etEmail.text.toString()

        if (binding.etEmail.text.toString()==""){
            binding.textInputLayoutEmail.error = "This is a required field"
            return false
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.textInputLayoutEmail.error = "Check email format"
            return false
        }

        if(binding.etPassword.text.toString()==""){
            binding.textInputLayoutPassword.error = "This is a required field"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }

        if(binding.etPassword.length() < 6){
            binding.textInputLayoutPassword.error = "Password at least 8 characters"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false

        }
        if(binding.etConfirmPassword.text.toString()==""){
            binding.textInputLayoutConfirmPassword.error = "This is a required field"
            binding.textInputLayoutConfirmPassword.errorIconDrawable = null
            return false
        }

        if (binding.etPassword.text.toString() != binding.etConfirmPassword.text.toString()){
            binding.textInputLayoutConfirmPassword.error = "Passwords do not match"
            return false
        }


        return true

    }

}