package com.example.pa

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import com.bumptech.glide.Glide

class UserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //加载头像
        val imageView: ImageView = view.findViewById(R.id.avatar)
        Glide.with(this).load(R.drawable.avatar).into(imageView)

        val edit:ImageView = view.findViewById(R.id.edit)
        edit.setOnClickListener {
            val intent = Intent(getActivity(), EditActivity::class.java)
            startActivity(intent)
        }

    }

}