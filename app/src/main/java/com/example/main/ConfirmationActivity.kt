package com.example.main

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirmation)

        dbHelper = DatabaseHelper(this)

        // Receive data from Intent
        val bloodGroup = intent.getStringExtra("BLOOD_GROUP") ?: ""
        val weight = intent.getStringExtra("WEIGHT") ?: ""
        val address = intent.getStringExtra("ADDRESS") ?: ""
        val city = intent.getStringExtra("CITY") ?: ""
        val state = intent.getStringExtra("STATE") ?: ""
        val pincode = intent.getStringExtra("PINCODE") ?: ""
        val phone = intent.getStringExtra("PHONE") ?: ""
        val disease = intent.getStringExtra("DISEASE") ?: ""
        val allergy = intent.getStringExtra("ALLERGY") ?: ""
        val tattoos = intent.getStringExtra("TATTOOS") ?: ""

        // Show confirmation details
        findViewById<TextView>(R.id.confirmbloodgrp).text = bloodGroup
        findViewById<TextView>(R.id.confirmWeight).text = weight
        findViewById<TextView>(R.id.confirmAddress).text = address
        findViewById<TextView>(R.id.confirmCity).text = city
        findViewById<TextView>(R.id.confirmState).text = state
        findViewById<TextView>(R.id.confirmPincode).text = pincode
        findViewById<TextView>(R.id.confirmPhone).text = phone
        findViewById<TextView>(R.id.confirmDisease).text = disease
        findViewById<TextView>(R.id.confirmAllergy).text = allergy
        findViewById<TextView>(R.id.confirmTattoos).text = tattoos

        // Done button saves donor to DB
        findViewById<Button>(R.id.btnDone).setOnClickListener {
            val success = dbHelper.insertDonor(
                bloodGroup, weight, address, city, state, pincode, phone, disease, allergy, tattoos
            )
            if (success) {
                Toast.makeText(this, "✅ Donor data saved successfully!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "❌ Error saving donor data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
