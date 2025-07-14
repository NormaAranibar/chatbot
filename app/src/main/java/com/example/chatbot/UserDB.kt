package com.example.chatbot

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class UserDB(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object {
        const val DATABASE_NAME = "UserDatabase.db"
        const val DATABASE_VERSION = 1
        const val TABLE_USERS = "Usuario"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "nombre"
        const val COLUMN_EMAIL = "correo"
        const val COLUMN_PASSWORD = "contraseña"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT,
                $COLUMN_EMAIL TEXT UNIQUE,
                $COLUMN_PASSWORD TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // se registra un nuevo usuario (returns true if successful)
    fun registerUser(nombre: String,correo:String, contraseña: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, nombre)
            put(COLUMN_EMAIL,correo)
            put(COLUMN_PASSWORD, contraseña)
        }
        val result = db.insert(TABLE_USERS, null, values)
        return result != -1L
    }

    // ver si existe el usuario(returns true if username/password match)
    fun loginUser(correo:String, contraseña: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID),
            "$COLUMN_EMAIL= ? AND $COLUMN_PASSWORD = ?",
            arrayOf(correo, contraseña),
            null, null, null
        )
        val isLoggedIn = cursor.count > 0
        cursor.close()
        return isLoggedIn
    }
    }