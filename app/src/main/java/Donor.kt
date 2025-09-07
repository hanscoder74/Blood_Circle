package com.example.main

data class Donor(
    val id: Int,
    val bloodGroup: String,
    val weight: String,
    val address: String,
    val city: String,
    val state: String,
    val pincode: String,
    val phone: String,
    val disease: String?,
    val allergy: String?,
    val tattoos: String?
)
