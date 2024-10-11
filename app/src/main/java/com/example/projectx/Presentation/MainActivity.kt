package com.example.projectx.Presentation

import ActionCrud
import TypeCrud
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.projectx.R
import com.example.projectx.dataLocal.ClientDao
import com.example.projectx.dataLocal.DataClient
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {


     private lateinit var ctnContent : LinearLayout

    private  val  viewModel: MainClientViewModel by lazy {
        MainClientViewModel.create(application)
    }
    private val viewModelDeatil: ClientDetailViewModel by viewModels {
        ClientDetailViewModel.getVmFactory(application)
    }

    private val myAdapter: ClientAdpter by lazy {
        ClientAdpter(::openRegisterClient)
    }
    private lateinit var rvList: RecyclerView

    private lateinit var search: SearchView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.myToolbar))

        rvList = findViewById(R.id.rvListClient)
        rvList.adapter = myAdapter


        ctnContent = findViewById(R.id.ctn_content)

        val btnCreateClient = findViewById<FloatingActionButton>(R.id.btn_openDetailClient)

        btnCreateClient.setOnClickListener {
            openRegisterClient()
        }


         search = findViewById(R.id.searchView1)

         setupSearchView()

    }

    override fun onStart() {
        super.onStart()
        listFromDataBase()
    }

    private fun listFromDataBase() {
        val listObserver = Observer<List<DataClient>>{myList ->
            if(myList.isEmpty()){
                ctnContent.visibility = View.VISIBLE
            }else{
                ctnContent.visibility = View.GONE
            }
            myAdapter.submitList(myList)
        }
        viewModel.getListClient.observe(this,listObserver)
    }

    private fun openRegisterClient(item :DataClient? = null){
    val intent  = Intent(CreateClient.openingScreen(this,item))
    startActivity(intent)
    }

    private fun deleteAll() {
        val actionCrud = ActionCrud(null, TypeCrud.DELETE_ALL.name)
        viewModelDeatil.exucute(actionCrud)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate : MenuInflater = menuInflater
        inflate.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
             R.id.menu_deleteAll -> {
             deleteAll()
             return true
            }
            else -> super.onOptionsItemSelected(item)
            }
        }

        private fun setupSearchView() {
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let  { myAdapter.filter(it) }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { myAdapter.filter(it) }
                    return true
                }
            })
        }

}