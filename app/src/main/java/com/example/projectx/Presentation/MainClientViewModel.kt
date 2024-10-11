package com.example.projectx.Presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectx.ClientApplication
import com.example.projectx.dataLocal.ClientDao
import com.example.projectx.dataLocal.DataClient

class MainClientViewModel(clientDao: ClientDao): ViewModel(){

    val getListClient : LiveData<List<DataClient>> = clientDao.getAll()



    companion object{

        fun create(application: Application):MainClientViewModel{
            val getDataBaseInstance = (application as ClientApplication).getAppDataBase()
            val dao = getDataBaseInstance.userDao()

            return MainClientViewModel(dao)
        }
    }
}