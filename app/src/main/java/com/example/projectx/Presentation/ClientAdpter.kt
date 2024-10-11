package com.example.projectx.Presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projectx.R
import com.example.projectx.dataLocal.DataClient
import java.util.logging.Filter


class ClientAdpter(
   var myListCreate:(item:DataClient) -> Unit
):ListAdapter<DataClient, ClientViewHolde>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolde {
        val view:View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_client,parent,false)
        return ClientViewHolde(view)
    }

    override fun onBindViewHolder(holder: ClientViewHolde, position: Int) {
        val item = getItem(position)
        holder.bind(item,myListCreate)
    }

        private var fullList: List<DataClient> = listOf()

        override fun submitList(list: List<DataClient>?) {
            super.submitList(list)
            list?.let {
                fullList = ArrayList(it) // Update the full list only once
            }
        }


    /*val filteredList = if (query.isEmpty()) {
        // Reset to the full list if the query is empty
        fullList.toList()
    } else {
        fullList.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    submitList(filteredList){
        notifyDataSetChanged()
    }*/
        fun filter(query: String) {

            val mylistafilter = if (query.isEmpty()) {
                fullList.toList()
            } else if(query.isNotEmpty()) {
                fullList.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            }else{
                fullList
            }

            submitList(mylistafilter){
                notifyDataSetChanged()
            }
        }



    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataClient>() {
            override fun areItemsTheSame(oldItem: DataClient, newItem: DataClient): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataClient, newItem: DataClient): Boolean {
                return oldItem.name == newItem.name &&
                        oldItem.address == newItem.address &&
                        oldItem.amountToPay == newItem.amountToPay
            }
        }
    }


}

class ClientViewHolde(private val view: View):RecyclerView.ViewHolder(view){

    private val  tvName = view.findViewById<TextView>(R.id.tv_listClientName)
    private val  tvAddress = view.findViewById<TextView>(R.id.tv_listClientAddress)
    private val  tvAmountToPlay = view.findViewById<TextView>(R.id.tv_ListClientAmountPlay)

    fun bind (
        item : DataClient,
        myListCreate: (item: DataClient) -> Unit
    ){
         tvName.text = item.name
         tvAddress.text = item.address
         tvAmountToPlay.text = item.amountToPay

        view.setOnClickListener{
            myListCreate.invoke(item)
        }

    }

}