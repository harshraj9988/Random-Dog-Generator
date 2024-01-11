package com.harshlabs.randomdoggenerator.presentation.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.harshlabs.randomdoggenerator.R
import com.harshlabs.randomdoggenerator.databinding.FragmentHomeScreenBinding
import com.harshlabs.randomdoggenerator.presentation.utils.UiState

class HomeScreenFragment : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel: HomeScreenViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnClickListeners()
        setupObservers()
    }

    private fun setupOnClickListeners() {
        binding.generateDogBtn.setOnClickListener {
            viewModel.goToGenerateDogScreen()
        }

        binding.recentlyGeneratedDogBtn.setOnClickListener {
            viewModel.goToRecentlyGeneratedDogScreen()
        }
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) {uiState ->
            when(uiState) {
                is UiState.GenerateDogScreen -> {
                    findNavController().navigate(R.id.action_homeScreenFragment_to_generateDogScreenFragment)
                    viewModel.resetUiState()
                }

                is UiState.RecentlyGeneratedDogScreen -> {
                    findNavController().navigate(R.id.action_homeScreenFragment_to_recentlyGeneratedDogsScreenFragment)
                    viewModel.resetUiState()
                }

                else -> { /*do nothing*/ }
            }
        }
    }
}