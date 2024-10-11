package com.example.projectx

import android.app.Application
import androidx.room.Room
import com.example.projectx.dataLocal.ClientDataBase

class ClientApplication():Application(){
    lateinit var dataBase : ClientDataBase

    override fun onCreate() {
        super.onCreate()

        dataBase = Room.databaseBuilder(
            applicationContext,
            ClientDataBase::class.java,"name"
        ).build()

    }

    fun getAppDataBase(): ClientDataBase {
        return dataBase
    }
}