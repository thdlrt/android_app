package com.example.pa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.pa.databinding.FragmentUserBinding
import com.google.android.material.tabs.TabLayout
import de.hdodenhof.circleimageview.CircleImageView

class UserFragment : Fragment() {
    lateinit private var view_t:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_t=view

        val edit:ImageView = view.findViewById(R.id.edit)
        edit.setOnClickListener {
            val intent = Intent(getActivity(), EditActivity::class.java)
            startActivity(intent)
        }
        val viewPager: ViewPager = view.findViewById(R.id.viewPager)
        val tabLayout: TabLayout = view.findViewById(R.id.tabLayout)

        // 设置ViewPager的适配器
        viewPager.adapter = MyPagerAdapter(childFragmentManager)

        // 将TabLayout与ViewPager关联
        tabLayout.setupWithViewPager(viewPager)
    }
    inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> WebFavoriteFragment()
                1 -> NewsFavoriteFragment()
                2 -> VideoFavoriteFragment()
                else -> throw IllegalArgumentException("Invalid position")
            }
        }

        override fun getCount(): Int {
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "网页"
                1 -> "新闻"
                2 -> "视频"
                else -> null
            }
        }
    }
    override fun onResume() {
        super.onResume()
        //个人信息初始化
        val prefs = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        if (!prefs.contains("name")){
            val editor = prefs.edit()
            editor.putString("avatar","android.resource://${requireActivity().packageName}/${R.raw.avatar}")
            editor.putString("name","thdlrt")
            editor.putString("sex","男")
            editor.putString("date","2003-01-28")
            editor.putString("sig","~thdlrt~")
            editor.apply()
        }
        //个人信息加载
        val bitmap = loadBitmap(requireActivity(),prefs.getString("avatar","")!!)
        view_t.findViewById<CircleImageView>(R.id.avatar).setImageBitmap(bitmap)
        view_t.findViewById<TextView>(R.id.username).text = prefs.getString("name","")!!
        //收藏夹加载

    }
}