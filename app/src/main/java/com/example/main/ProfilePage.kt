package com.example.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ProfilePage : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var sharedPref: android.content.SharedPreferences
    private var userId: Int = -1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        dbHelper = DBHelper.getInstance(this)
        sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)

        // ✅ get logged-in userId
        userId = sharedPref.getInt("USER_ID", -1)
        if (userId == -1) {
            Toast.makeText(this, "Session expired. Please log in again.", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, login::class.java))
            finish()
            return
        }

        val tvName = findViewById<TextView>(R.id.tvName)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val etContact = findViewById<EditText>(R.id.etContact)
        val rgGender = findViewById<RadioGroup>(R.id.rgGender)
        val etCity = findViewById<EditText>(R.id.etCity)
        val etState = findViewById<EditText>(R.id.etState)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        // Back navigation to homepage
        findViewById<ImageView>(R.id.home_nav).setOnClickListener {
            startActivity(Intent(this, homepage::class.java))
            finish()
        }

        // ✅ Load user details
        loadUser(tvName, tvEmail, etContact, rgGender, etCity, etState)

        // Save profile
        btnSave.setOnClickListener {
            val contact = etContact.text.toString().trim()
            val gender = when (rgGender.checkedRadioButtonId) {
                R.id.rbMale -> "Male"
                R.id.rbFemale -> "Female"
                else -> ""
            }
            val city = etCity.text.toString().trim()
            val state = etState.text.toString().trim()

            val success = dbHelper.updateUser(userId, contact, gender, city, state)
            if (success) {
                Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show()
                loadUser(tvName, tvEmail, etContact, rgGender, etCity, etState)
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
            }
        }

        // Logout
        btnLogout.setOnClickListener {
            sharedPref.edit().clear().apply()
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, login::class.java))
            finishAffinity()
        }

        // Delete account
        btnDelete.setOnClickListener {
            val success = dbHelper.deleteUser(userId)
            if (success) {
                sharedPref.edit().clear().apply()
                Toast.makeText(this, "Account deleted", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, login::class.java))
                finishAffinity()
            } else {
                Toast.makeText(this, "Failed to delete account", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ✅ Helper function to load user details
    private fun loadUser(
        tvName: TextView,
        tvEmail: TextView,
        etContact: EditText,
        rgGender: RadioGroup,
        etCity: EditText,
        etState: EditText
    ) {
        val user = dbHelper.getUserById(userId)
        if (user != null) {
            tvName.text = user.name
            tvEmail.text = user.email
            etContact.setText(user.contact ?: "")
            etCity.setText(user.city ?: "")
            etState.setText(user.state ?: "")
            when (user.gender) {
                "Male" -> rgGender.check(R.id.rbMale)
                "Female" -> rgGender.check(R.id.rbFemale)
                else -> rgGender.clearCheck()
            }
        }
    }
}
