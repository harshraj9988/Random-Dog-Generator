package com.harshlabs.randomdoggenerator.presentation.recently_generated_dogs_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.harshlabs.randomdoggenerator.R

class RecentlyGeneratedDogsScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recently_generated_dogs_screen, container, false)
    }

}