package com.example.main

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        val find = findViewById<LinearLayout>(R.id.findDonner)
        find.setOnClickListener {
            val intent = Intent(this, findDonorPage::class.java)
            startActivity(intent)
        }

        val register = findViewById<LinearLayout>(R.id.registerDonner)
        register.setOnClickListener {
            val intent = Intent(this, registerPage::class.java)
            startActivity(intent)
        }
    }
}
