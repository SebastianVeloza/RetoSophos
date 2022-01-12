package com.example.retosohphos.activities

import androidx.appcompat.app.AppCompatActivity
import com.example.retosohphos.R

class MyToolBar {
    fun show(activities: AppCompatActivity,title:String,upbutton:Boolean){
        activities.setSupportActionBar(activities.findViewById(R.id.toolbar))
        activities.supportActionBar?.title=title
        activities.supportActionBar?.setDisplayHomeAsUpEnabled(upbutton)
    }
}