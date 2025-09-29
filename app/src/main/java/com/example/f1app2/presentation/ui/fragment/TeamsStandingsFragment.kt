package com.example.f1app2.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.f1app2.R
import com.example.f1app2.databinding.FragmentTeamsStandingsBinding
import com.example.f1app2.presentation.adapter.TeamsAdapter
import com.example.f1app2.presentation.viewmodel.TeamsStandingsViewModel
import com.example.f1app2.presentation.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeamsStandingsFragment : Fragment() {

    private var _binding: FragmentTeamsStandingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TeamsStandingsViewModel by viewModels()
    private lateinit var teamsAdapter: TeamsAdapter

    companion object {
        private const val TAG = "TeamsStandingsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView called")
        _binding = FragmentTeamsStandingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated called")

        setupRecyclerView()
        observeViewModel()

        binding.backButton.setOnClickListener {
            Log.d(TAG, "Back button clicked")
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        Log.d(TAG, "Setting up recycler view")
        teamsAdapter = TeamsAdapter(requireContext()) { constructorStanding ->
            Log.d(TAG, "Team clicked: ${constructorStanding.teamId}")
            val bundle = bundleOf("teamId" to constructorStanding.teamId)
            findNavController().navigate(R.id.action_teamsStandingsFragment_to_teamFragment, bundle)
        }

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = teamsAdapter
        }
        Log.d(TAG, "Recycler view setup complete")
    }

    private fun observeViewModel() {
        Log.d(TAG, "Starting to observe ViewModel")
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.teamsState.collect { state ->
                    Log.d(TAG, "State changed: ${state::class.simpleName}")
                    when (state) {
                        is UiState.Loading -> {
                            Log.d(TAG, "Loading state - showing progress bar")
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recycler.visibility = View.GONE
                        }
                        is UiState.Success -> {
                            Log.d(TAG, "Success state - got ${state.data.constructors_championship.size} teams")
                            binding.progressBar.visibility = View.GONE
                            binding.recycler.visibility = View.VISIBLE

                            state.data.constructors_championship.forEachIndexed { index, team ->
                                Log.d(TAG, "Team $index: ${team.team.teamName} - ${team.points} points")
                            }

                            teamsAdapter.submitList(state.data.constructors_championship)
                        }
                        is UiState.Error -> {
                            Log.e(TAG, "Error state: ${state.message}")
                            binding.progressBar.visibility = View.GONE
                            binding.recycler.visibility = View.VISIBLE
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
        Log.d(TAG, "onDestroyView called")
        _binding = null
    }
}
