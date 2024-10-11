package com.example.projectx.dataLocal

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class DataClient (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val address:String,
    val amountToPay:String
):Serializable