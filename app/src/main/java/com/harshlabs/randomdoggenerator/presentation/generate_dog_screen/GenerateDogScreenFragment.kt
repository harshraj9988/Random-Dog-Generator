package com.harshlabs.randomdoggenerator.presentation.generate_dog_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.harshlabs.randomdoggenerator.R
import com.harshlabs.randomdoggenerator.databinding.FragmentGenerateDogScreenBinding
import com.harshlabs.randomdoggenerator.presentation.utils.UiState
import com.harshlabs.randomdoggenerator.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenerateDogScreenFragment : Fragment() {

    private lateinit var binding: FragmentGenerateDogScreenBinding
    private val viewModel: GenerateDogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenerateDogScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnClickListeners()
        setupObservers()
    }

    private fun setupOnClickListeners() {
        binding.generateButton.setOnClickListener {
            viewModel.generateImage()
        }

        binding.backBtn.setOnClickListener {
            viewModel.goToHomeScreen()
        }
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when(uiState) {
                is UiState.ShowData -> {
                    binding.imageView.setImageBitmap(uiState.data)
                }

                is UiState.ShowToast -> {
                    context?.showToast(uiState.text)
                }

                is UiState.HomeScreen -> {
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }

                else -> { /*do nothing*/ }
            }
        }
    }
}