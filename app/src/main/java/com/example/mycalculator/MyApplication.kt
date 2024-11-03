package com.example.mycalculator

import android.app.Application
import com.example.mycalculator.data.remote.FactorDBClient


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize FactorDBClient here
        FactorDBClient // Accessing the object initializes i
    }
}