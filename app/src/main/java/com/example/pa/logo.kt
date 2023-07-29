package com.example.pa

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class logo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 点击响应
        val weather: ConstraintLayout = view.findViewById(R.id.constraintLayout)
        weather.setOnClickListener {
            val intent = Intent(getActivity(), WetherActivity::class.java)
            startActivity(intent)
        }
        val add:ImageView = view.findViewById(R.id.addImageView)
        add.setOnClickListener {
            val intent = Intent(getActivity(), AddActivity::class.java)
            startActivity(intent)
        }
        //天气逐渐显示动画
        val wether: View = view.findViewById(R.id.constraintLayout)
        wether.alpha = 0f
        val animation = ObjectAnimator.ofFloat(wether, "alpha", 0f, 1f)
        animation.duration = 1000
        animation.start()
    }

}