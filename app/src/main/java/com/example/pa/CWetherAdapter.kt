package com.example.pa

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CWetherAdapter(val cityList: List<String>, val activity: WetherActivity) : RecyclerView.Adapter<CWetherAdapter.ViewHolder>() {
    private var wetherList:ArrayList<Wether> = ArrayList()
    inner class ViewHolder(view: View) :RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val loadingView: FragmentContainerView = view.findViewById(R.id.loading_view)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cwether_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position:Int) {
        val item = cityList[position]
        holder.title.text = item
        //加载
        holder.loadingView.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            initWethers()
            holder.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            holder.recyclerView.adapter = WetherAdapter(wetherList)
            // 数据加载完成，隐藏加载动画
            holder.loadingView.visibility = View.GONE
        }, 500)
    }
    override fun getItemCount() = cityList.size
    private fun initWethers(){
        wetherList.add(Wether("现在",R.drawable.ic_clear_day,"31°"))
        wetherList.add(Wether("11时",R.drawable.ic_clear_day,"32°"))
        wetherList.add(Wether("12时",R.drawable.ic_partly_cloud_day,"33°"))
        wetherList.add(Wether("13时",R.drawable.ic_partly_cloud_day,"33°"))
        wetherList.add(Wether("14时",R.drawable.ic_partly_cloud_day,"33°"))
        wetherList.add(Wether("15时",R.drawable.ic_partly_cloud_day,"32°"))
        wetherList.add(Wether("16时",R.drawable.ic_partly_cloud_day,"31°"))
        wetherList.add(Wether("17时",R.drawable.ic_partly_cloud_day,"28°"))
        wetherList.add(Wether("18时",R.drawable.ic_clear_day,"27°"))
        wetherList.add(Wether("19时",R.drawable.ic_clear_day,"25°"))
        wetherList.add(Wether("20时",R.drawable.ic_clear_day,"25°"))
        wetherList.add(Wether("21时",R.drawable.ic_clear_day,"24°"))
        wetherList.add(Wether("22时",R.drawable.ic_clear_day,"24°"))
        wetherList.add(Wether("23时",R.drawable.ic_partly_cloud_day,"23°"))
        wetherList.add(Wether("24时",R.drawable.ic_partly_cloud_day,"23°"))
        wetherList.add(Wether("1时",R.drawable.ic_clear_day,"23°"))
        wetherList.add(Wether("2时",R.drawable.ic_clear_day,"22°"))
        wetherList.add(Wether("3时",R.drawable.ic_clear_day,"22°"))
        wetherList.add(Wether("4时",R.drawable.ic_partly_cloud_day,"22°"))
        wetherList.add(Wether("5时",R.drawable.ic_partly_cloud_day,"22°"))
        wetherList.add(Wether("6时",R.drawable.ic_partly_cloud_day,"23°"))
        wetherList.add(Wether("7时",R.drawable.ic_partly_cloud_day,"25°"))
        wetherList.add(Wether("8时",R.drawable.ic_partly_cloud_day,"25°"))
        wetherList.add(Wether("9时",R.drawable.ic_clear_day,"26°"))
    }
}