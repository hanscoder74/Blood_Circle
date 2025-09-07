package com.example.main

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "DonorsDB", null, 2) { // version 2

    override fun onCreate(db: SQLiteDatabase?) {
        val query = """
            CREATE TABLE donors (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                blood_group TEXT NOT NULL,
                weight TEXT NOT NULL,
                address TEXT NOT NULL,
                city TEXT NOT NULL,
                state TEXT NOT NULL,
                pincode TEXT NOT NULL,
                phone TEXT NOT NULL,
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

    // Insert new donor
    fun insertDonor(
        bloodGroup: String,
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
            put("blood_group", bloodGroup)
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
        db.close()
        return result != -1L
    }

    // Fetch donors with optional filters
    fun getDonors(city: String?, state: String?, bloodGroup: String?): List<Donor> {
        val donors = mutableListOf<Donor>()
        val db = readableDatabase

        var query = "SELECT * FROM donors WHERE 1=1"
        val args = mutableListOf<String>()

        if (!city.isNullOrEmpty()) {
            query += " AND LOWER(city)=?"
            args.add(city.lowercase())
        }
        if (!state.isNullOrEmpty()) {
            query += " AND LOWER(state)=?"
            args.add(state.lowercase())
        }
        if (!bloodGroup.isNullOrEmpty()) {
            query += " AND LOWER(blood_group)=?"
            args.add(bloodGroup.lowercase())
        }

        val cursor = db.rawQuery(query, args.toTypedArray())
        if (cursor.moveToFirst()) {
            do {
                val donor = Donor(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    bloodGroup = cursor.getString(cursor.getColumnIndexOrThrow("blood_group")),
                    weight = cursor.getString(cursor.getColumnIndexOrThrow("weight")),
                    address = cursor.getString(cursor.getColumnIndexOrThrow("address")),
                    city = cursor.getString(cursor.getColumnIndexOrThrow("city")),
                    state = cursor.getString(cursor.getColumnIndexOrThrow("state")),
                    pincode = cursor.getString(cursor.getColumnIndexOrThrow("pincode")),
                    phone = cursor.getString(cursor.getColumnIndexOrThrow("phone")),
                    disease = cursor.getString(cursor.getColumnIndexOrThrow("disease")),
                    allergy = cursor.getString(cursor.getColumnIndexOrThrow("allergy")),
                    tattoos = cursor.getString(cursor.getColumnIndexOrThrow("tattoos"))
                )
                donors.add(donor)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return donors
    }

}
