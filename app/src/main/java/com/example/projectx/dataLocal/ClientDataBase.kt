package com.example.projectx.dataLocal

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DataClient::class], version = 1)
abstract class ClientDataBase :RoomDatabase(){
    abstract fun userDao():ClientDao
}
