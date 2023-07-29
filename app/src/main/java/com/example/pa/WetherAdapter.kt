package com.example.pa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Wether(val time:String, val type:Int, val num:String)
class WetherAdapter(val wetherList: List<Wether>) :RecyclerView.Adapter<WetherAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) :RecyclerView.ViewHolder(view){
        val wetherTime: TextView = view.findViewById(R.id.time)
        val wetherType: ImageView = view.findViewById(R.id.type)
        val wetherNum: TextView = view.findViewById(R.id.num)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.wether_item,parent,false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position:Int){
        val wether = wetherList[position]
        holder.wetherTime.text = wether.time
        holder.wetherType.setImageResource(wether.type)
        holder.wetherNum.text = wether.num
    }
    override fun getItemCount() = wetherList.size
}