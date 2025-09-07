package com.example.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class homepage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        val sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        val name = sharedPref.getString("name", "User")

        // ✅ Display greeting
        val userText = findViewById<TextView>(R.id.tvUserName)
        userText.text = "Hey $name 👋"

        val welcomeText = findViewById<TextView>(R.id.wlcm)
        welcomeText.text = "Welcome back"

        // ✅ Go to Profile page
        findViewById<ImageView>(R.id.profile_nav).setOnClickListener {
            startActivity(Intent(this, ProfilePage::class.java))
        }

        // Example navigation (keep or update as per your needs)
        findViewById<LinearLayout>(R.id.registerDonner).setOnClickListener {
            startActivity(Intent(this, registerPage::class.java))
        }
        findViewById<LinearLayout>(R.id.findDonner).setOnClickListener {
            startActivity(Intent(this, findDonorPage::class.java))
        }
        findViewById<LinearLayout>(R.id.viewRequests).setOnClickListener {
            startActivity(Intent(this, viewRequest::class.java))
        }
        findViewById<LinearLayout>(R.id.findHospitals).setOnClickListener {
            startActivity(Intent(this, FindHospital::class.java))
        }
        findViewById<LinearLayout>(R.id.importantNotices).setOnClickListener {
            startActivity(Intent(this, Notice::class.java))
        }
        findViewById<LinearLayout>(R.id.sellBuyBlood).setOnClickListener {
            startActivity(Intent(this, SellBuy::class.java))
        }
    }
}
