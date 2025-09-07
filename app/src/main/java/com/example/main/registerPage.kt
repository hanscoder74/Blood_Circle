package com.example.main

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class registerPage : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_donor) // XML file name

        dbHelper = DatabaseHelper(this)

        // UI references
        val editWeight = findViewById<EditText>(R.id.editWeight)
        val spinnerBloodGroup = findViewById<Spinner>(R.id.spinnerBloodGroup)
        val editAddress = findViewById<EditText>(R.id.editAddress)
        val editCity = findViewById<EditText>(R.id.editCity)
        val editState = findViewById<EditText>(R.id.editState)
        val editPincode = findViewById<EditText>(R.id.editPincode)
        val editPhone = findViewById<EditText>(R.id.editPhone)

        val radioDisease = findViewById<RadioGroup>(R.id.radioDisease)
        val radioAllergy = findViewById<RadioGroup>(R.id.radioAllergy)
        val radioTattoos = findViewById<RadioGroup>(R.id.radioTattoos)

        val submitButton = findViewById<Button>(R.id.submitButton)

        // Setup blood group dropdown
        val bloodGroups = listOf("Select Blood Group", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
        spinnerBloodGroup.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bloodGroups)

        // Submit button action
        submitButton.setOnClickListener {
            val weight = editWeight.text.toString().trim()
            val bloodGroup = spinnerBloodGroup.selectedItem.toString()
            val address = editAddress.text.toString().trim()
            val city = editCity.text.toString().trim()
            val state = editState.text.toString().trim()
            val pincode = editPincode.text.toString().trim()
            val phone = editPhone.text.toString().trim()

            val disease = getSelectedOption(radioDisease)
            val allergy = getSelectedOption(radioAllergy)
            val tattoos = getSelectedOption(radioTattoos)

            // Validation
            if (weight.isEmpty() || address.isEmpty() || city.isEmpty() ||
                state.isEmpty() || pincode.isEmpty() || phone.isEmpty() ||
                bloodGroup == "Select Blood Group"
            ) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val success = dbHelper.insertDonor(
                bloodGroup, weight, address, city, state,
                pincode, phone, disease, allergy, tattoos
            )

            if (success) {
                Toast.makeText(this, "Donor Registered Successfully!", Toast.LENGTH_SHORT).show()
                finish() // Close and go back
            } else {
                Toast.makeText(this, "Failed to register donor", Toast.LENGTH_SHORT).show()
            }

            val intent1 = Intent(this, ConfirmationActivity::class.java)
            intent1.putExtra("BLOOD_GROUP", bloodGroup)
            intent1.putExtra("WEIGHT", weight)
            intent1.putExtra("ADDRESS", address)
            intent1.putExtra("CITY", city)
            intent1.putExtra("STATE", state)
            intent1.putExtra("PINCODE", pincode)
            intent1.putExtra("PHONE", phone)
            intent1.putExtra("DISEASE", disease)
            intent1.putExtra("ALLERGY", allergy)
            intent1.putExtra("TATTOOS", tattoos)
            startActivity(intent1)
        }
    }

    // Helper method for radio buttons
    private fun getSelectedOption(radioGroup: RadioGroup): String {
        val selectedId = radioGroup.checkedRadioButtonId
        return if (selectedId != -1) {
            findViewById<RadioButton>(selectedId).text.toString()
        } else {
            "No"
        }
    }
}
