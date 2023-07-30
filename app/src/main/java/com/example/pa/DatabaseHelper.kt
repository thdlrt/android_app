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

    val createVideo = "CREATE TABLE videos (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "title TEXT," +
            "pix_src TEXT," +
            "video_src TEXT," +
            "time TEXT," +
            "tag TEXT)"

    val createWebpagesTable = """
    CREATE TABLE Webpages (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        url TEXT,
        title TEXT
    )
    """
    val createNewsTable = """
    CREATE TABLE NewsFavorites (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        newsId INTEGER,
        title TEXT
    )
    """
    val createVideosTable = """
    CREATE TABLE VideoFavorites (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        videoId INTEGER,
        title TEXT
    )
    """
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createHistory)
        db.execSQL(createWeb)
        db.execSQL(createNews)
        db.execSQL(createVideo)
        db.execSQL(createWebpagesTable)
        db.execSQL(createNewsTable)
        db.execSQL(createVideosTable)
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
    //视频库
    fun getVideoById(id: Int): Video? {
        val db = readableDatabase
        val cursor = db.query("videos", null, "id=?", arrayOf(id.toString()), null, null, null)
        return cursor.use {
            if (it.moveToFirst()) {
                val titleIndex = it.getColumnIndex("title")
                val pixSrcIndex = it.getColumnIndex("pix_src")
                val videoSrcIndex = it.getColumnIndex("video_src")
                val timeIndex = it.getColumnIndex("time")
                val tagIndex = it.getColumnIndex("tag")

                if (titleIndex != -1 && pixSrcIndex != -1 && videoSrcIndex != -1 && timeIndex != -1 && tagIndex != -1) {
                    val title = it.getString(titleIndex)
                    val pixSrc = it.getString(pixSrcIndex)
                    val videoSrc = it.getString(videoSrcIndex)
                    val time = it.getString(timeIndex)
                    val tagString = it.getString(tagIndex)
                    val tags = tagString.split(",").map { it.trim() } as ArrayList<String>
                    Video(id, title, pixSrc, videoSrc, time, tags)
                } else {
                    null
                }
            } else {
                null
            }
        }
    }

    fun getAllVideos(): ArrayList<Video> {
        val videos = ArrayList<Video>()
        val db = readableDatabase
        val cursor = db.query("videos", null, null, null, null, null, null)
        cursor.use {
            val idIndex = it.getColumnIndex("id")
            val titleIndex = it.getColumnIndex("title")
            val pixSrcIndex = it.getColumnIndex("pix_src")
            val videoSrcIndex = it.getColumnIndex("video_src")
            val timeIndex = it.getColumnIndex("time")
            val tagIndex = it.getColumnIndex("tag")

            if (idIndex != -1 && titleIndex != -1 && pixSrcIndex != -1 && videoSrcIndex != -1 && timeIndex != -1 && tagIndex != -1) {
                while (it.moveToNext()) {
                    val id = it.getInt(idIndex)
                    val title = it.getString(titleIndex)
                    val pixSrc = it.getString(pixSrcIndex)
                    val videoSrc = it.getString(videoSrcIndex)
                    val time = it.getString(timeIndex)
                    val tagString = it.getString(tagIndex)
                    val tags = tagString.split(",").map { it.trim() } as ArrayList<String>
                    videos.add(Video(id, title, pixSrc, videoSrc, time, tags))
                }
            }
        }
        return videos
    }
    fun insertVideo(video: Video) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put("title", video.title)
            put("pix_src", video.pix_src)
            put("video_src", video.video_src)
            put("time", video.time)
            put("tag", video.tag.joinToString(",")) // 将标签列表转换为逗号分隔的字符串
        }
        db.insert("videos", null, contentValues)
    }
    fun searchVideosByTitle(titleKeyword: String): ArrayList<Video> {
        val videos = ArrayList<Video>()
        val db = readableDatabase
        val selection = "title LIKE ?"
        val selectionArgs = arrayOf("%$titleKeyword%")
        val cursor = db.query("videos", null, selection, selectionArgs, null, null, null)
        cursor.use {
            val idIndex = it.getColumnIndex("id")
            val titleIndex = it.getColumnIndex("title")
            val pixSrcIndex = it.getColumnIndex("pix_src")
            val videoSrcIndex = it.getColumnIndex("video_src")
            val timeIndex = it.getColumnIndex("time")
            val tagIndex = it.getColumnIndex("tag")

            if (idIndex != -1 && titleIndex != -1 && pixSrcIndex != -1 && videoSrcIndex != -1 && timeIndex != -1 && tagIndex != -1) {
                while (it.moveToNext()) {
                    val id = it.getInt(idIndex)
                    val title = it.getString(titleIndex)
                    val pixSrc = it.getString(pixSrcIndex)
                    val videoSrc = it.getString(videoSrcIndex)
                    val time = it.getString(timeIndex)
                    val tagString = it.getString(tagIndex)
                    val tags = tagString.split(",").map { it.trim() } as ArrayList<String>
                    videos.add(Video(id, title, pixSrc, videoSrc, time, tags))
                }
            }
        }
        return videos
    }
    //收藏夹系统
    fun getAllWebpages(): ArrayList<Webpage> {
        val webpages = ArrayList<Webpage>()
        val db = readableDatabase
        val cursor = db.query("Webpages", null, null, null, null, null, null)
        cursor.use {
            while (it.moveToNext()) {
                val urlIndex = it.getColumnIndex("url")
                val titleIndex = it.getColumnIndex("title")
                if (urlIndex != -1 && titleIndex != -1) {
                    val url = it.getString(urlIndex)
                    val title = it.getString(titleIndex)
                    webpages.add(Webpage(url, title))
                }
            }
        }
        return webpages
    }
    fun getAllNewsFavorites(): ArrayList<NewsFavorite> {
        val newsFavorites = ArrayList<NewsFavorite>()
        val db = readableDatabase
        val cursor = db.query("NewsFavorites", null, null, null, null, null, null)

        cursor.use {
            while (it.moveToNext()) {
                val idIndex = it.getColumnIndex("newsId")
                val titleIndex = it.getColumnIndex("title")
                if (idIndex != -1 && titleIndex != -1) {
                    val id = it.getInt(idIndex)
                    val title = it.getString(titleIndex)
                    newsFavorites.add(NewsFavorite(id, title))
                }
            }
        }
        return newsFavorites
    }
    fun getAllVideoFavorites(): ArrayList<VideoFavorite> {
        val videoFavorites = ArrayList<VideoFavorite>()
        val db = readableDatabase
        val cursor = db.query("VideoFavorites", null, null, null, null, null, null)

        cursor.use {
            while (it.moveToNext()) {
                val idIndex = it.getColumnIndex("videoId")
                val titleIndex = it.getColumnIndex("title")
                if (idIndex != -1 && titleIndex != -1) {
                    val id = it.getInt(idIndex)
                    val title = it.getString(titleIndex)
                    videoFavorites.add(VideoFavorite(id, title))
                }
            }
        }
        return videoFavorites
    }
    fun insertWebpage(webpage: Webpage) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("url", webpage.url)
            put("title", webpage.title)
        }
        db.insert("Webpages", null, values)
    }
    fun insertNewsFavorite(newsFavorite: NewsFavorite) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("newsId", newsFavorite.newsId)
            put("title", newsFavorite.title)
        }
        db.insert("NewsFavorites", null, values)
    }
    fun insertVideoFavorite(videoFavorite: VideoFavorite) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("videoId", videoFavorite.videoId)
            put("title", videoFavorite.title)
        }
        db.insert("VideoFavorites", null, values)
    }
    fun deleteWebpageByUrl(url: String) {
        val db = writableDatabase
        db.delete("Webpages", "url = ?", arrayOf(url))
    }
    fun deleteNewsFavoriteById(newsId: Int) {
        val db = writableDatabase
        db.delete("NewsFavorites", "newsId = ?", arrayOf(newsId.toString()))
    }
    fun deleteVideoFavoriteById(videoId: Int) {
        val db = writableDatabase
        db.delete("VideoFavorites", "videoId = ?", arrayOf(videoId.toString()))
    }
    fun isWebpageUrlExists(url: String): Boolean {
        val db = readableDatabase
        val cursor = db.query("Webpages", arrayOf("url"), "url = ?", arrayOf(url), null, null, null)
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }
    fun isNewsIdExists(newsId: Int): Boolean {
        val db = readableDatabase
        val cursor = db.query("NewsFavorites", arrayOf("newsId"), "newsId = ?", arrayOf(newsId.toString()), null, null, null)
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }
    fun isVideoIdExists(videoId: Int): Boolean {
        val db = readableDatabase
        val cursor = db.query("VideoFavorites", arrayOf("videoId"), "videoId = ?", arrayOf(videoId.toString()), null, null, null)
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }
}






