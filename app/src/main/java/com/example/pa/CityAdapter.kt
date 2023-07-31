package com.example.pa

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

data class City(val name:String)
class CityAdapter(val cityList: List<City>, val activity: CityActivity) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) :RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val add: ImageView = view.findViewById(R.id.add)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position:Int) {
        val item = cityList[position]
        holder.title.text = item.name
        if(item.name in activity.chooseList){
           holder.add.alpha = 0.3f
        }
        else{
            holder.add.alpha = 1.0f
        }
        holder.add.setOnClickListener {
            if(item.name in activity.chooseList)
                return@setOnClickListener
            activity.chooseList.add(0,item.name)
            val gson = Gson()
            val json = gson.toJson(activity.chooseList)
            val prefs = activity.getSharedPreferences("wether", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("city", json)
            Log.w("assert", "${activity.chooseList}")
            editor.apply()
            activity.onBackPressed()
        }
    }
    override fun getItemCount() = cityList.size
}