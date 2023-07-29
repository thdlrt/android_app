package com.example.pa

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

data class News(val id: Int, val smallPix: String?, val bigPix: String?, val title: String,
                val content: String, val icon:Int, val type:String, val writer:String)
class NewsAdapter(private val items: List<News>, private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_WITH_IMAGE = 0
        const val TYPE_WITHOUT_IMAGE = 1
    }

    inner class ViewHolderWithImage(view: View) : RecyclerView.ViewHolder(view) {
        val title:TextView = view.findViewById(R.id.title)
        val icon:ImageView = view.findViewById(R.id.icon)
        val type:TextView = view.findViewById(R.id.type)
        val from:TextView = view.findViewById(R.id.from)
        val pix:ImageView = view.findViewById(R.id.pix)
        val main: ConstraintLayout = view.findViewById(R.id.main)
    }

    inner class ViewHolderWithoutImage(view: View) : RecyclerView.ViewHolder(view) {
        val title:TextView = view.findViewById(R.id.title)
        val icon:ImageView = view.findViewById(R.id.icon)
        val type:TextView = view.findViewById(R.id.type)
        val from:TextView = view.findViewById(R.id.from)
        val main: ConstraintLayout = view.findViewById(R.id.main)
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].smallPix != null) {
            TYPE_WITH_IMAGE
        } else {
            TYPE_WITHOUT_IMAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_WITH_IMAGE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.news_pix_item, parent, false)
            ViewHolderWithImage(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.news_nopix_item, parent, false)
            ViewHolderWithoutImage(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is ViewHolderWithImage) {
            holder.title.text = item.title
            holder.type.text = item.type
            holder.from.text = item.writer
            holder.icon.setImageResource(item.icon)
            val bitmap = BitmapFactory.decodeFile(item.smallPix)
            holder.pix.setImageBitmap(bitmap)
            //跳转新闻页
            holder.main.setOnClickListener {
                val intent = Intent(context, NewsActivity::class.java)
                intent.putExtra("id", item.id)
                context.startActivity(intent)
            }
        } else if (holder is ViewHolderWithoutImage) {
            holder.title.text = item.title
            holder.type.text = item.type
            holder.from.text = item.writer
            holder.icon.setImageResource(item.icon)
            //跳转新闻页
            holder.main.setOnClickListener {
                val intent = Intent(context, NewsActivity::class.java)
                intent.putExtra("id", item.id)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = items.size
}