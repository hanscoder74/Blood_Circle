package com.example.main

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class findDonorPage : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DonorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_donors)

        dbHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerViewDonors)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val cityDropdown = findViewById<Spinner>(R.id.cityDropdown)
        val stateDropdown = findViewById<Spinner>(R.id.stateDropdown)
        val bloodDropdown = findViewById<Spinner>(R.id.bloodDropdown)
        val btnSearch = findViewById<Button>(R.id.btnSearch)

        // Sample data for dropdowns
        val cities = listOf("City","Delhi","Mumbai","Bangalore","Hazaribagh","Pune","Kolkata","Ranchi","Jamshedpur")
        val states = listOf("State","Delhi","Maharashtra","Karnataka","Jharkhand","West Bengal")
        val bloodGroups = listOf("Blood Group","A+","A-","B+","B-","AB+","AB-","O+","O-")

        cityDropdown.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cities)
        stateDropdown.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, states)
        bloodDropdown.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bloodGroups)

        btnSearch.setOnClickListener {
            val city = cityDropdown.selectedItem.toString()
            val state = stateDropdown.selectedItem.toString()
            val blood = bloodDropdown.selectedItem.toString()

            val donors = dbHelper.getDonors(
                city.takeIf { it.isNotEmpty() },
                state.takeIf { it.isNotEmpty() },
                blood.takeIf { it.isNotEmpty() }
            )

            adapter = DonorAdapter(donors)
            recyclerView.adapter = adapter
        }
    }
}
