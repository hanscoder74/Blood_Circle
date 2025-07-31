package com.example.main

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "DonorsDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = """
            CREATE TABLE donors (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                weight TEXT,
                address TEXT,
                city TEXT,
                state TEXT,
                pincode TEXT,
                phone TEXT,
                disease TEXT,
                allergy TEXT,
                tattoos TEXT
            )
        """.trimIndent()
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS donors")
        onCreate(db)
    }

    fun insertDonor(
        weight: String,
        address: String,
        city: String,
        state: String,
        pincode: String,
        phone: String,
        disease: String,
        allergy: String,
        tattoos: String
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("weight", weight)
            put("address", address)
            put("city", city)
            put("state", state)
            put("pincode", pincode)
            put("phone", phone)
            put("disease", disease)
            put("allergy", allergy)
            put("tattoos", tattoos)
        }

        val result = db.insert("donors", null, values)
        return result != -1L
    }
}
