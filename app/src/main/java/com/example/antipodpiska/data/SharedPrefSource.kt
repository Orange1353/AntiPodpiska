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

    fun saveUserToShared(user: User, context: Context){
        val preferencesUser: SharedPreferences = context.getSharedPreferences("Temp", Context.MODE_PRIVATE)
        val prefsEditorUser: SharedPreferences.Editor = preferencesUser.edit()

        prefsEditorUser.putString("email", user.email).apply()
        prefsEditorUser.putString("password", user.password).apply()
        prefsEditorUser.putString("nickname", user.nickname).apply()
        prefsEditorUser.putString("phone", user.phone).apply()
        prefsEditorUser.putString("name", user.name).apply()
        prefsEditorUser.putBoolean("pushAll", user.pushAll).apply()
        prefsEditorUser.putInt("beginPush", user.beginPush).apply()
        prefsEditorUser.putBoolean("periodPush", user.periodPush).apply()
    }



    fun getTempUser(context: Context): List<String> {
        val preferencesUser: SharedPreferences = context.getSharedPreferences("Temp", Context.MODE_PRIVATE)

        return listOf( preferencesUser.getString("email", "").toString(),
                preferencesUser.getString("password", "").toString(),
                preferencesUser.getString("nickname", "").toString())

    }

    fun getUserFromShared(context: Context): User {
        var user: User = User()
        val prefsEditorUser: SharedPreferences = context.getSharedPreferences("Temp", Context.MODE_PRIVATE)
        user.email = prefsEditorUser.getString("email", "").toString()
        user.password = prefsEditorUser.getString("password", "").toString()
        user.nickname = prefsEditorUser.getString("nickname", "").toString()
        user.phone = prefsEditorUser.getString("phone", "").toString()
        user.name= prefsEditorUser.getString("name", "").toString()
        user.pushAll= prefsEditorUser.getBoolean("pushAll", true)
        user.beginPush= prefsEditorUser.getInt("beginPush", 2)
        user.periodPush= prefsEditorUser.getBoolean("periodPush", true)

        return user
    }

    fun deleteShared(sub: Sub){
        val gson = Gson()
        val json = gson.toJson(sub)
        prefsEditor.remove(sub.id.toString()).apply()
    }

    fun deleteSharedUser(context: Context){
        val preferencesUser: SharedPreferences = context.getSharedPreferences("Temp", Context.MODE_PRIVATE)
        val prefsEditorUser: SharedPreferences.Editor = preferencesUser.edit()

        prefsEditorUser.remove("email").apply()
    }

    fun getFromShared(sub: Sub) : Sub{

        val gson = Gson()
        val json: String = preferences.getString("5562010050634948298", "").toString()
        val sub1: Sub = gson.fromJson(json, Sub::class.java)
        return sub1

    }


}