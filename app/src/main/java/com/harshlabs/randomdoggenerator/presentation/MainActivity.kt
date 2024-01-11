package com.harshlabs.randomdoggenerator.presentation

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.harshlabs.randomdoggenerator.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv = findViewById<TextView>(R.id.my_tv)
        myViewModel.data.observe(this) {
            tv.text = it.toString()
        }

        tv.setOnClickListener { myViewModel.getImage() }
    }
}