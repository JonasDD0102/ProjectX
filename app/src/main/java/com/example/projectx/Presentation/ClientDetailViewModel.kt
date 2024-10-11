package com.example.projectx.Presentation

import ActionCrud
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.projectx.ClientApplication
import com.example.projectx.dataLocal.ClientDao
import com.example.projectx.dataLocal.DataClient
import kotlinx.coroutines.launch

class ClientDetailViewModel(
    private val clientDao: ClientDao
):ViewModel() {



    fun exucute(actionCrud: ActionCrud){
        when(actionCrud.typeCrud){
            TypeCrud.UPDATE.name ->  update(actionCrud.client!!)
            TypeCrud.CREATE.name ->  create(actionCrud.client!!)
            TypeCrud.DELETE.name ->  deleteById(actionCrud.client!!.id)
            TypeCrud.DELETE_ALL.name -> deleteAll()
        }
    }


    fun update(client: DataClient){
        viewModelScope.launch {
            clientDao.update(client)
        }
    }

    fun deleteById(id:Int){
        viewModelScope.launch {
            clientDao.deleteById(id)
        }
    }

    fun deleteAll(){
        viewModelScope.launch{
            clientDao.delete_All()
        }
    }

    fun create(client: DataClient){
        viewModelScope.launch {
            clientDao.insert(client)
        }
    }


    companion object {
        fun getVmFactory(application: Application):ViewModelProvider.Factory {
            val instaceDataBase = (application as ClientApplication).getAppDataBase()
            val dao  = instaceDataBase.userDao()


            val factory =  object : ViewModelProvider.Factory {

                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ClientDetailViewModel(dao) as T
                }
            }

            return factory
        }
    }
}

