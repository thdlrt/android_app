package com.example.pa

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DatabaseHelper private constructor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MyDatabase.db"
        private const val DATABASE_VERSION = 1

        @Volatile
        private var INSTANCE: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DatabaseHelper(context).also { INSTANCE = it }
            }
    }

    private val createHistory = "create table History (" +
            "id integer primary key autoincrement, " +
            "title text, " +
            "time text)"

    private val createWeb = "CREATE TABLE Web (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "url TEXT," +
            "title TEXT," +
            "scrollPosition INTEGER," +
            "thumbnail TEXT)"

    private val createNews = "CREATE TABLE News (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "smallPix TEXT," +
            "bigPix TEXT," +
            "title TEXT," +
            "content TEXT," +
            "icon INTEGER," +
            "type TEXT," +
            "writer TEXT)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createHistory)
        db.execSQL(createWeb)
        db.execSQL(createNews)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrade
    }

    //操作
    //搜索历史记录
    fun insertHistory(title: String) {
        val db = writableDatabase
        val currentTime = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()).format(Date())
        val values = ContentValues().apply {
            put("title", title)
            put("time", currentTime)
        }
        db.insert("History", null, values)
    }
    fun getAllHistory(): ArrayList<HistoryAdapter.History> {
        val db = readableDatabase
        val historyList = ArrayList<HistoryAdapter.History>()
        val cursor = db.query("History", null, null, null, null, null, "time DESC")

        if (cursor.moveToFirst()) {
            do {
                val titleIndex = cursor.getColumnIndex("title")
                val timeIndex = cursor.getColumnIndex("time")
                val idIndex = cursor.getColumnIndex("id")
                if (titleIndex != -1 && timeIndex != -1) {
                    val id = cursor.getInt(idIndex)
                    val title = cursor.getString(titleIndex)
                    val time = cursor.getString(timeIndex)
                    val history = HistoryAdapter.History(id, title, time)
                    historyList.add(history)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return historyList
    }
    fun deleteHistory(id: Int) {
        val db = writableDatabase
        db.delete("History", "id = ?", arrayOf(id.toString()))
    }
    fun clearHistory() {
        val db = writableDatabase
        db.delete("History", null, null)
    }
    //浏览器窗口
    fun updateWeb(id: Int, url: String, title: String, scrollPosition: Int, thumbnail: String) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put("url", url)
            put("title", title)
            put("scrollPosition", scrollPosition)
            put("thumbnail", thumbnail)
        }
        db.update("Web", contentValues, "id = ?", arrayOf(id.toString()))
    }

    fun deleteWeb(id: Int) {
        val db = writableDatabase
        db.delete("Web", "id = ?", arrayOf(id.toString()))
    }

    fun insertWeb(url: String, title: String, scrollPosition: Int, thumbnail: String):Int {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put("url", url)
            put("title", title)
            put("scrollPosition", scrollPosition)
            put("thumbnail", thumbnail)
        }
        return db.insert("Web", null, contentValues).toInt()
    }

    fun getAllWebs(): List<Web> {
        val db = readableDatabase
        val webs = ArrayList<Web>()
        val cursor = db.query("Web", null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val idIndex = cursor.getColumnIndex("id")
                val urlIndex = cursor.getColumnIndex("url")
                val titleIndex = cursor.getColumnIndex("title")
                val scrollPositionIndex = cursor.getColumnIndex("scrollPosition")
                val thumbnailIndex = cursor.getColumnIndex("thumbnail")
                if (idIndex != -1 && urlIndex != -1 && titleIndex != -1 && scrollPositionIndex != -1 && thumbnailIndex != -1) {
                    val id = cursor.getInt(idIndex)
                    val url = cursor.getString(urlIndex)
                    val title = cursor.getString(titleIndex)
                    val scrollPosition = cursor.getInt(scrollPositionIndex)
                    val thumbnail = cursor.getString(thumbnailIndex)
                    val web = Web(id, url, title, scrollPosition, thumbnail)
                    webs.add(web)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return webs
    }
    fun getWebById(id: Int): Web? {
        val db = readableDatabase
        val cursor = db.query(
            "Web",
            null,
            "id = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var web: Web? = null
        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex("id")
            val urlIndex = cursor.getColumnIndex("url")
            val titleIndex = cursor.getColumnIndex("title")
            val scrollPositionIndex = cursor.getColumnIndex("scrollPosition")
            val thumbnailIndex = cursor.getColumnIndex("thumbnail")
            if (idIndex != -1 && urlIndex != -1 && titleIndex != -1 && scrollPositionIndex != -1 && thumbnailIndex != -1) {
                val id = cursor.getInt(idIndex)
                val url = cursor.getString(urlIndex)
                val title = cursor.getString(titleIndex)
                val scrollPosition = cursor.getInt(scrollPositionIndex)
                val thumbnail = cursor.getString(thumbnailIndex)
                web = Web(id, url, title, scrollPosition, thumbnail)
            }
        }
        cursor.close()
        return web
    }
    fun clearWeb() {
        val db = writableDatabase
        db.delete("Web", null, null)
    }
    //新闻系统
    fun insertNews(news: News) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put("smallPix", news.smallPix)
            put("bigPix", news.bigPix)
            put("title", news.title)
            put("content", news.content)
            put("icon", news.icon)
            put("type", news.type)
            put("writer", news.writer)
        }
        db.insert("News", null, contentValues)
    }

    fun getAllNews(): ArrayList<News> {
        val db = readableDatabase
        val newsList = ArrayList<News>()
        val cursor = db.query("News", null, null, null, null, null, "id DESC")

        if (cursor.moveToFirst()) {
            do {
                val idIndex = cursor.getColumnIndex("id")
                val smallPixIndex = cursor.getColumnIndex("smallPix")
                val bigPixIndex = cursor.getColumnIndex("bigPix")
                val titleIndex = cursor.getColumnIndex("title")
                val contentIndex = cursor.getColumnIndex("content")
                val iconIndex = cursor.getColumnIndex("icon")
                val typeIndex = cursor.getColumnIndex("type")
                val writerIndex = cursor.getColumnIndex("writer")

                if (idIndex != -1 && smallPixIndex != -1 && bigPixIndex != -1 && titleIndex != -1 && contentIndex != -1 && iconIndex != -1 && typeIndex != -1 && writerIndex != -1) {
                    val id = cursor.getInt(idIndex)
                    val smallPix = cursor.getString(smallPixIndex)
                    val bigPix = cursor.getString(bigPixIndex)
                    val title = cursor.getString(titleIndex)
                    val content = cursor.getString(contentIndex)
                    val icon = cursor.getInt(iconIndex)
                    val type = cursor.getString(typeIndex)
                    val writer = cursor.getString(writerIndex)
                    val news = News(id, smallPix, bigPix, title, content, icon, type, writer)
                    newsList.add(news)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return newsList
    }
    fun getNewsById(id: Int): News? {
        val db = readableDatabase
        val cursor = db.query("News", null, "id = ?", arrayOf(id.toString()), null, null, null)

        var news: News? = null
        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex("id")
            val smallPixIndex = cursor.getColumnIndex("smallPix")
            val bigPixIndex = cursor.getColumnIndex("bigPix")
            val titleIndex = cursor.getColumnIndex("title")
            val contentIndex = cursor.getColumnIndex("content")
            val iconIndex = cursor.getColumnIndex("icon")
            val typeIndex = cursor.getColumnIndex("type")
            val writerIndex = cursor.getColumnIndex("writer")

            if (idIndex != -1 && smallPixIndex != -1 && bigPixIndex != -1 && titleIndex != -1 && contentIndex != -1 && iconIndex != -1 && typeIndex != -1 && writerIndex != -1) {
                val id = cursor.getInt(idIndex)
                val smallPix = cursor.getString(smallPixIndex)
                val bigPix = cursor.getString(bigPixIndex)
                val title = cursor.getString(titleIndex)
                val content = cursor.getString(contentIndex)
                val icon = cursor.getInt(iconIndex)
                val type = cursor.getString(typeIndex)
                val writer = cursor.getString(writerIndex)
                news = News(id, smallPix, bigPix, title, content, icon, type, writer)
            }
        }
        cursor.close()
        return news
    }
}




