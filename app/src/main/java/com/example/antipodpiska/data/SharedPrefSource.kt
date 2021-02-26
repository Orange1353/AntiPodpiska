package com.example.antipodpiska.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import java.util.HashMap


class SharedPrefSource constructor(context: Context)  {
    private val PREFS_NAME = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private var preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    val prefsEditor: SharedPreferences.Editor = preferences.edit()


   fun saveToShared(sub: Sub){

       val gson = Gson()
       val json = gson.toJson(sub)
       prefsEditor.putString(sub.id.toString(), json).apply();

   }

    fun addTempUser(email : String, password: String, nickname: String, context: Context){
        val preferencesUser: SharedPreferences = context.getSharedPreferences("Temp", Context.MODE_PRIVATE)
        val prefsEditorUser: SharedPreferences.Editor = preferencesUser.edit()

        prefsEditorUser.putString("email", email).apply()
        prefsEditorUser.putString("password", password).apply()
        prefsEditorUser.putString("nickname", nickname).apply()

    }

    fun getTempUser(context: Context): List<String> {
        val preferencesUser: SharedPreferences = context.getSharedPreferences("Temp", Context.MODE_PRIVATE)
        val t = preferencesUser.getString("email", "").toString()

        return listOf( preferencesUser.getString("email", "").toString(), preferencesUser.getString("password", "").toString(),
                preferencesUser.getString("nickname", "").toString())

    }

    fun deleteShared(sub: Sub){
        val gson = Gson()
        val json = gson.toJson(sub)
        prefsEditor.remove(sub.id.toString()).apply()
    }

    fun getFromShared(sub: Sub) : Sub{

        val gson = Gson()
        val json: String = preferences.getString("5562010050634948298", "").toString()
        val sub1: Sub = gson.fromJson(json, Sub::class.java)
        return sub1

    }


}