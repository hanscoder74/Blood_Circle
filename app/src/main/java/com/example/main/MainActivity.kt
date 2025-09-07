package com.example.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bloodconnect) // splash layout

        val sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        lifecycleScope.launch {
            delay(3000) // 3 sec splash
            if (isLoggedIn) {
                startActivity(Intent(this@MainActivity, homepage::class.java))
            } else {
                startActivity(Intent(this@MainActivity, login::class.java))
            }
            finish()
        }
    }
}
