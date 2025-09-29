package com.example.f1app2.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.f1app2.databinding.FragmentDriversStandingsBinding
import com.example.f1app2.presentation.adapter.DriversAdapter
import com.example.f1app2.presentation.viewmodel.DriversStandingsViewModel
import com.example.f1app2.presentation.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DriversStandingsFragment : Fragment() {

    private var _binding: FragmentDriversStandingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DriversStandingsViewModel by viewModels()
    private lateinit var driversAdapter: DriversAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDriversStandingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        driversAdapter = DriversAdapter(requireContext()) { driverStanding ->
            val action = DriversStandingsFragmentDirections
                .actionDriversStandingsFragmentToDriverFragment(driverStanding.driverId)
            findNavController().navigate(action)
        }

        binding.driversRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = driversAdapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.driversState.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.driversRecycler.visibility = View.GONE
                        }
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.driversRecycler.visibility = View.VISIBLE
                            driversAdapter.submitList(state.data.drivers_championship)
                        }
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "Loading error: ${state.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
