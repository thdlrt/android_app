package com.example.pa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HomeFragment : Fragment() {
    var newsList:ArrayList<News> = ArrayList()
    lateinit var view_t:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_t=view
        //点击绑定
        val editText:EditText = view.findViewById(R.id.editText2)
        editText.setOnClickListener {
            val intent = Intent(getActivity(), SearchActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        refresh()
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }
    fun refresh(){
        val dbHelper: DatabaseHelper = DatabaseHelper.getInstance(requireActivity())
        newsList = dbHelper.getAllNews()
        //装载新闻
        val recyclerView: RecyclerView = view_t.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = NewsAdapter(newsList,view_t.getContext())
        recyclerView.adapter = adapter
    }
}