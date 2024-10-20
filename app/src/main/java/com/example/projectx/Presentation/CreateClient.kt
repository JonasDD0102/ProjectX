package com.example.projectx.Presentation

import ActionCrud
import TypeCrud
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectx.R
import com.example.projectx.dataLocal.DataClient
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.snackbar.Snackbar

class CreateClient : AppCompatActivity() {

    private var client :DataClient? = null

    private lateinit var  btnRegisterClient : Button


    private  val viewModel :ClientDetailViewModel by viewModels{
        ClientDetailViewModel.getVmFactory(application)
    }


    companion object{
        const val KEY_OPEN_CREATE_CLIENT = "KEY_OPEN_CREATE_CLIENT"

        fun openingScreen(context:Context,client:DataClient?): Intent {
            val intent = Intent(context, CreateClient::class.java)
                .apply {
                    putExtra(KEY_OPEN_CREATE_CLIENT,client)
                }
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_client)
        setSupportActionBar(findViewById(R.id.myToolbar))
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.creatClient)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        client = intent.getSerializableExtra(KEY_OPEN_CREATE_CLIENT) as DataClient ?

        val edtNameClient = findViewById<EditText>(R.id.edtNameRegisterClient)
        val edtAddressClient = findViewById<EditText>(R.id.edtAddressRegisterClient)
        val edtAmountToPlayClient = findViewById<EditText>(R.id.edt_AmountToPlay_register)
        btnRegisterClient = findViewById(R.id.btnRegisterClient)

        if(client != null){
            edtNameClient.setText(client!!.name)
            edtAddressClient.setText(client!!.address)
            edtAmountToPlayClient.setText(client!!.amountToPay)
        }

        btnRegisterClient.setOnClickListener {
            val edtName = edtNameClient.text.toString()
            val edtaddress = edtAddressClient.text.toString()
            val edtAmount = edtAmountToPlayClient.text.toString()

            if (edtName.isNotEmpty() && edtaddress.isNotEmpty() && edtAmount.isNotEmpty()) {
                if (client == null) {
                    addOrUpdate(0, edtName, edtaddress, edtAmount, TypeCrud.CREATE)
                } else {
                    addOrUpdate(client!!.id, edtName, edtaddress, edtAmount, TypeCrud.CREATE)
                }
            }
            edtaddress.plus(getString(R.string.address_Edt_Client,edtAddressClient))
            edtAmount.plus(getString(R.string.amount_to_pay_edt,edtAmountToPlayClient ))
        }
    }

    private fun addOrUpdate(
        id:Int,
        name:String,
        address:String,
        amountToPlay:String,
        typeCrud: TypeCrud
        ){
        val client = DataClient(id,name,address,amountToPlay)
        performAction(client,typeCrud)

    }

    private fun performAction(client: DataClient,typeCrud: TypeCrud){
        val actionClient = ActionCrud(client,typeCrud.name)
        viewModel.exucute(actionClient)
        finish()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
            inflater.inflate(R.menu.detail_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  when (item.itemId){
            R.id.menuIdDeletebyId -> {
                 if(client != null){
                    performAction(client!!,TypeCrud.DELETE)
                 }else{
                     messageErro(btnRegisterClient,"item not faund")
                 }
              return true
            }
           else -> super.onOptionsItemSelected(item)
        }
    }

    fun messageErro(view: View, message:String){
        Snackbar.make(view,message, Snackbar.LENGTH_LONG)
            .setAction("action", null)
            .show()
    }

}