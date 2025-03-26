package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.AppComponent
import com.example.newsapp.di.DaggerAppComponent

class NewsApp: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        appComponent = DaggerAppComponent.factory().create(this)
        super.onCreate()
    }
}