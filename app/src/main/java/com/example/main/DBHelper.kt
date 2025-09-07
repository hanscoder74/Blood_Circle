package com.example.main

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Updated User model
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val contact: String? = null,
    val gender: String? = null,
    val city: String? = null,
    val state: String? = null
)

class DBHelper private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserDB.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_USERS = "users"
        private var instance: DBHelper? = null

        fun getInstance(context: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_USERS (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                contact TEXT,
                gender TEXT,
                city TEXT,
                state TEXT
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // Insert new user
    fun insertUser(name: String, email: String, password: String): Boolean {
        return try {
            val db = writableDatabase
            val values = ContentValues().apply {
                put("name", name)
                put("email", email)
                put("password", password)
            }
            val result = db.insert(TABLE_USERS, null, values)
            result != -1L
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Get user by email & password
    fun getUser(email: String, password: String): User? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USERS WHERE email=? AND password=?",
            arrayOf(email, password)
        )
        return if (cursor.moveToFirst()) {
            val user = User(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("name")),
                cursor.getString(cursor.getColumnIndexOrThrow("email")),
                cursor.getString(cursor.getColumnIndexOrThrow("password")),
                cursor.getString(cursor.getColumnIndexOrThrow("contact")),
                cursor.getString(cursor.getColumnIndexOrThrow("gender")),
                cursor.getString(cursor.getColumnIndexOrThrow("city")),
                cursor.getString(cursor.getColumnIndexOrThrow("state"))
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    // ✅ Get user by ID
    fun getUserById(id: Int): User? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE id=?", arrayOf(id.toString()))
        return if (cursor.moveToFirst()) {
            val user = User(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("name")),
                cursor.getString(cursor.getColumnIndexOrThrow("email")),
                cursor.getString(cursor.getColumnIndexOrThrow("password")),
                cursor.getString(cursor.getColumnIndexOrThrow("contact")),
                cursor.getString(cursor.getColumnIndexOrThrow("gender")),
                cursor.getString(cursor.getColumnIndexOrThrow("city")),
                cursor.getString(cursor.getColumnIndexOrThrow("state"))
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    // ✅ Update profile fields
    fun updateUser(id: Int, contact: String, gender: String, city: String, state: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("contact", contact)
            put("gender", gender)
            put("city", city)
            put("state", state)
        }
        val result = db.update(TABLE_USERS, values, "id=?", arrayOf(id.toString()))
        return result > 0
    }

    // ✅ Delete user
    fun deleteUser(id: Int): Boolean {
        val db = writableDatabase
        val result = db.delete(TABLE_USERS, "id=?", arrayOf(id.toString()))
        return result > 0
    }

    // ✅ Check if email exists
    fun isEmailExists(email: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE email=?", arrayOf(email))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
}
