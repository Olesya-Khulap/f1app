package com.example.f1app2.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.f1app2.R
import com.example.f1app2.databinding.FragmentTeamBinding
import com.example.f1app2.presentation.adapter.ImageSliderAdapter
import com.example.f1app2.presentation.viewmodel.TeamViewModel
import com.example.f1app2.presentation.state.UiState
import com.example.f1app2.domain.usecase.TeamDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan

@AndroidEntryPoint
class TeamFragment : Fragment() {

    private var _binding: FragmentTeamBinding? = null
    private val binding get() = _binding!!

    private val args: TeamFragmentArgs by navArgs()
    private val viewModel: TeamViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadTeam(args.teamId)
        observeViewModel()

        binding.btnStandings.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.teamState.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            bindTeam(state.data)
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

    private fun bindTeam(teamDetails: TeamDetails) {
        val team = teamDetails.team

        binding.teamName.text = team.teamName

        val countryFlagResId = getCountryFlagResource(team.teamNationality)
        if (countryFlagResId != 0) {
            binding.teamCountryFlag.setImageResource(countryFlagResId)
        }

        val redColor = ContextCompat.getColor(requireContext(), R.color.red_main)
        val blueColor = ContextCompat.getColor(requireContext(), R.color.dark_blue_main)

        binding.firstAppearance.text = SpannableString("First appearance: ${team.firstAppeareance}").apply {
            setSpan(ForegroundColorSpan(blueColor), 0, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(redColor), 18, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        binding.constructorsChampionships.text = SpannableString("Constructors Championships: ${team.constructorsChampionships}").apply {
            setSpan(ForegroundColorSpan(blueColor), 0, 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(redColor), 27, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        binding.points2025.text = SpannableString("Points in 2025: ${teamDetails.points}").apply {
            setSpan(ForegroundColorSpan(blueColor), 0, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(redColor), 16, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        val images = listOf(
            getTeamImageResource(team.teamId, 1),
            getTeamImageResource(team.teamId, 2)
        ).filter { it != 0 }

        if (images.isNotEmpty()) {
            binding.teamImageSlider.adapter = ImageSliderAdapter(requireContext(), images)
        }

        binding.driversContainer.removeAllViews()
        val inflater = LayoutInflater.from(requireContext())

        for (driver in teamDetails.drivers.take(2)) {
            val card = inflater.inflate(R.layout.driver_card, binding.driversContainer, false)

            val driverNameText = card.findViewById<android.widget.TextView>(R.id.driver_name)
            val driverSurnameText = card.findViewById<android.widget.TextView>(R.id.driver_surname)
            driverNameText.text = driver.name
            driverSurnameText.text = if (driver.surname == "Kimi Antonelli") "Antonelli" else driver.surname

            val driverImage = card.findViewById<android.widget.ImageView>(R.id.driver_image)
            val imageResName = (driver.driverId?.lowercase() ?: "") + "_1"
            val imageResId = resources.getIdentifier(imageResName, "drawable", requireContext().packageName)
            if (imageResId != 0) {
                driverImage.setImageResource(imageResId)
            }

            card.setOnClickListener {
                if (!driver.driverId.isNullOrEmpty()) {
                    val action = TeamFragmentDirections
                        .actionTeamFragmentToDriverFragment(driver.driverId!!)
                    findNavController().navigate(action)
                }
            }

            binding.driversContainer.addView(card)
        }
    }

    private fun getCountryFlagResource(nationality: String): Int {
        val resName = nationality.lowercase().replace(" ", "")
        return resources.getIdentifier(resName, "drawable", requireContext().packageName)
    }

    private fun getTeamImageResource(teamName: String, number: Int): Int {
        val resName = "${teamName.lowercase().replace(" ", "")}_$number"
        return resources.getIdentifier(resName, "drawable", requireContext().packageName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
