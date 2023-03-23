package com.example.listcontacts

import android.app.Application
import com.example.listcontacts.di.DaggerAppComponent
import com.example.listcontacts.di.DataBaseModule
import com.example.listcontacts.di.SDK

class App : Application() {
    val sdk by lazy { SDK(applicationContext) }

    val appComponent by lazy {
        DaggerAppComponent
            .builder()
            .dataBaseModule(DataBaseModule(applicationContext))
            .build()
    }
}