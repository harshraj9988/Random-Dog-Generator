package com.harshlabs.randomdoggenerator.presentation.recently_generated_dogs_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.harshlabs.randomdoggenerator.R
import com.harshlabs.randomdoggenerator.databinding.FragmentRecentlyGeneratedDogsScreenBinding
import com.harshlabs.randomdoggenerator.presentation.utils.UiState
import com.harshlabs.randomdoggenerator.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentlyGeneratedDogsScreenFragment : Fragment() {

    private lateinit var binding: FragmentRecentlyGeneratedDogsScreenBinding
    private val viewModel: RecentlyGeneratedDogsViewModel by viewModels()
    private lateinit var adapter: DogImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecentlyGeneratedDogsScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DogImageAdapter()
        binding.recyclerView.layoutManager = CustomLinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        setupOnClickListeners()
        setupObservers()
    }

    private fun setupOnClickListeners() {
        binding.clearDogsButton.setOnClickListener {
            viewModel.deleteAllPhotos()
        }

        binding.backBtn.setOnClickListener {
            viewModel.goToHomeScreen()
        }
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when(uiState) {
                is UiState.ShowData -> {
                    adapter.submitList(uiState.data)
                    adapter.notifyDataSetChanged()
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