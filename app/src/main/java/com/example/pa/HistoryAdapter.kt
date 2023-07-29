package com.example.pa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter {
    data class History(val id: Int,val title:String,val time:String)
    class HistoryAdapter(val historyList: List<History>, val activity: HistoryActivity) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) :RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.title)
            val time: TextView = view.findViewById(R.id.time)
            val del: ImageView = view.findViewById(R.id.clean)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
            return ViewHolder(view)
        }
        override fun onBindViewHolder(holder: ViewHolder, position:Int) {
            val item = historyList[position]
            holder.title.text = item.title
            holder.time.text = item.time
            holder.del.setOnClickListener {
                //删除历史记录
                val dbHelper = DatabaseHelper.getInstance(activity)
                dbHelper.deleteHistory(item.id)
                //刷新
                activity.refresh()
            }
        }
        override fun getItemCount() = historyList.size
    }

}