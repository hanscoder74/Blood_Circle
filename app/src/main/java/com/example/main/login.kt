package com.example.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class login : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var sharedPref: android.content.SharedPreferences

    // Tabs & layouts
    private lateinit var loginTab: LinearLayout
    private lateinit var signupTab: LinearLayout
    private lateinit var loginUnderline: View
    private lateinit var signupUnderline: View
    private lateinit var loginLayout: LinearLayout
    private lateinit var signupLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = DBHelper.getInstance(this)
        sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)

        // --- Tabs ---
        loginTab = findViewById(R.id.loginTab)
        signupTab = findViewById(R.id.signupTab)
        loginUnderline = findViewById(R.id.loginUnderline)
        signupUnderline = findViewById(R.id.signupUnderline)
        loginLayout = findViewById(R.id.signInLayout)
        signupLayout = findViewById(R.id.signUpLayout)

        // --- Tab click listeners ---
        loginTab.setOnClickListener { showLoginTab() }
        signupTab.setOnClickListener { showSignupTab() }

        // --- Login button ---
        findViewById<Button>(R.id.logIn).setOnClickListener {
            val email = findViewById<EditText>(R.id.logInEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.logInPassword).text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showToast("Email and password required")
                return@setOnClickListener
            }

            val user = dbHelper.getUser(email, password)
            if (user != null) {
                saveLoginSession(user.email, user.name, user.id)
                showToast("Login successful!")
                goToHomepage()
            } else {
                showToast("Invalid credentials")
            }
        }

        // --- Signup button ---
        findViewById<Button>(R.id.signUp).setOnClickListener {
            val name = findViewById<EditText>(R.id.name).text.toString().trim()
            val email = findViewById<EditText>(R.id.signUpEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.signUpPassword).text.toString().trim()
            val confirmPassword = findViewById<EditText>(R.id.signUpPasswordCon).text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showToast("All fields are required")
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                showToast("Passwords do not match")
                return@setOnClickListener
            }
            if (!isValidPassword(password)) {
                showToast("Password must have 1 capital, 1 number, 1 special char")
                return@setOnClickListener
            }

            if (dbHelper.isEmailExists(email)) {
                showToast("User already exists!")
                return@setOnClickListener
            }

            val success = dbHelper.insertUser(name, email, password)
            if (success) {
                val user = dbHelper.getUser(email, password)
                if (user != null) {
                    saveLoginSession(user.email, user.name, user.id)
                }
                showToast("Signup successful!")
                goToHomepage()
            } else {
                showToast("Signup failed!")
            }
        }
    }

    // --- Switch to Login Tab ---
    private fun showLoginTab() {
        loginLayout.visibility = View.VISIBLE
        signupLayout.visibility = View.GONE
        loginUnderline.visibility = View.VISIBLE
        signupUnderline.visibility = View.GONE
    }

    // --- Switch to Signup Tab ---
    private fun showSignupTab() {
        loginLayout.visibility = View.GONE
        signupLayout.visibility = View.VISIBLE
        loginUnderline.visibility = View.GONE
        signupUnderline.visibility = View.VISIBLE
    }

    // --- Password validation ---
    private fun isValidPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#\$%^&+=!]).{6,}$")
        return regex.matches(password)
    }

    // --- Save login session with userId ---
    private fun saveLoginSession(email: String, name: String, userId: Int) {
        sharedPref.edit().apply {
            putBoolean("isLoggedIn", true)
            putString("email", email)
            putString("name", name)
            putInt("USER_ID", userId) // ✅ store ID
            apply()
        }
    }

    // --- Go to Homepage ---
    private fun goToHomepage() {
        val intent = Intent(this, homepage::class.java)
        startActivity(intent)
        finish() // Close login activity
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
