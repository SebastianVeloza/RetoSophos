package com.example.retosohphos.utils

import android.app.Application

class Users:Application() {
    companion object{
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        super.onCreate()
        prefs=Prefs(applicationContext)
    }
}