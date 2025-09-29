package com.example.f1app2.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.f1app2.R
import com.example.f1app2.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "MainFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView called")
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        Log.d(TAG, "binding created successfully")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated called")

        try {
            setupClickListeners()
            Log.d(TAG, "click listeners setup successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up click listeners", e)
        }
    }

    private fun setupClickListeners() {
        binding.wdc.setOnClickListener {
            Log.d(TAG, "WDC clicked")
            findNavController().navigate(R.id.action_mainFragment_to_driversStandingsFragment)
        }

        binding.wcc.setOnClickListener {
            Log.d(TAG, "WCC clicked")
            findNavController().navigate(R.id.action_mainFragment_to_teamsStandingsFragment)
        }

        binding.calendar.setOnClickListener {
            Log.d(TAG, "Calendar clicked")
            findNavController().navigate(R.id.action_mainFragment_to_calendarFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView called")
        _binding = null
    }
}
