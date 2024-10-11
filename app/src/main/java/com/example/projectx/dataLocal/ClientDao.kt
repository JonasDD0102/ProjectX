package com.example.projectx.dataLocal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.selects.select

@Dao
interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(client: DataClient)

    @Query("select * from dataclient")
    fun getAll():LiveData<List<DataClient>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(client: DataClient)

    @Query("delete from dataclient where id=:id")
    suspend fun deleteById(id:Int)

    @Query("delete from dataclient")
    suspend fun delete_All()
}