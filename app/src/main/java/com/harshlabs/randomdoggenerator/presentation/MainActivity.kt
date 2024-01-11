package com.harshlabs.randomdoggenerator.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
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

        val imgv = findViewById<ImageView>(R.id.imgv)
        val dwn = findViewById<Button>(R.id.btn_dwn)

        dwn.setOnClickListener {
            myViewModel.loadImage()
        }

        myViewModel.bitmap.observe(this) {
            it?.let {
                imgv.setImageBitmap(it)
            }
        }
    }
}