package com.example.main

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class findDonorPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_donor) // Update with actual layout name

        val backText = findViewById<TextView>(R.id.backTextView)
        backText.setOnClickListener {
            finish()
        }

        val submitButton = findViewById<Button>(R.id.submitButton)
        submitButton.setOnClickListener {
            val weight = findViewById<EditText>(R.id.editWeight).text.toString()
            val address = findViewById<EditText>(R.id.editAddress).text.toString()
            val city = findViewById<EditText>(R.id.editCity).text.toString()
            val state = findViewById<EditText>(R.id.editState).text.toString()
            val pincode = findViewById<EditText>(R.id.editPincode).text.toString()
            val phone = findViewById<EditText>(R.id.editPhone).text.toString()

            val diseaseGroup = findViewById<RadioGroup>(R.id.radioDisease)
            val allergyGroup = findViewById<RadioGroup>(R.id.radioAllergy)
            val tattooGroup = findViewById<RadioGroup>(R.id.radioTattoos)

            val disease = diseaseGroup.findViewById<RadioButton>(
                diseaseGroup.checkedRadioButtonId
            )?.text.toString()

            val allergy = allergyGroup.findViewById<RadioButton>(
                allergyGroup.checkedRadioButtonId
            )?.text.toString()

            val tattoos = tattooGroup.findViewById<RadioButton>(
                tattooGroup.checkedRadioButtonId
            )?.text.toString()

            val intent = Intent(this, ConfirmationActivity::class.java).apply {
                putExtra("WEIGHT", weight)
                putExtra("ADDRESS", address)
                putExtra("CITY", city)
                putExtra("STATE", state)
                putExtra("PINCODE", pincode)
                putExtra("PHONE", phone)
                putExtra("DISEASE", disease)
                putExtra("ALLERGY", allergy)
                putExtra("TATTOOS", tattoos)
            }
            startActivity(intent)
        }
    }
}
