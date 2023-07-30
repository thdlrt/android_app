package com.example.pa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NewsFavoriteFragment : Fragment() {
    private var newsFList = ArrayList<NewsFavorite>()
    lateinit private var view_t:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_t = view
    }

    override fun onResume() {
        super.onResume()
        val recyclerview: RecyclerView = view_t.findViewById(R.id.recyclerView)
        recyclerview.layoutManager = LinearLayoutManager(requireActivity())
        val dbHelper = DatabaseHelper.getInstance(requireActivity())
        newsFList = dbHelper.getAllNewsFavorites()
        recyclerview.adapter = NewsFAdapter(newsFList,requireActivity())
    }
}