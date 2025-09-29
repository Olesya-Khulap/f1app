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
import com.example.f1app2.databinding.FragmentCircuitBinding
import com.example.f1app2.presentation.viewmodel.CircuitViewModel
import com.example.f1app2.presentation.state.UiState
import com.example.f1app2.domain.model.Race
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import android.text.SpannableStringBuilder
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.widget.TextView



@AndroidEntryPoint
class CircuitFragment : Fragment() {

    private var _binding: FragmentCircuitBinding? = null
    private val binding get() = _binding!!

    private val args: CircuitFragmentArgs by navArgs()
    private val viewModel: CircuitViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCircuitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadRace(args.raceId)
        observeViewModel()

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.raceState.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            bindRace(state.data)
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

    private fun bindRace(race: Race) {
        binding.circuitTitle.text = renameRace(race.raceName)

        val circuitImageName = "${race.circuit.circuitId}_circuit"
        val imageId = resources.getIdentifier(circuitImageName, "drawable", requireContext().packageName)
        if (imageId != 0) {
            binding.circuitImage.setImageResource(imageId)
        } else {
            binding.circuitImage.setImageResource(android.R.drawable.ic_menu_gallery)
        }

        val redColor = ContextCompat.getColor(requireContext(), R.color.red_main)

        setSpannableText(binding.tvCircuitName, "Circuit: ", race.circuit.circuitName, redColor)
        setSpannableText(binding.tvPlace, "Place: ", "${race.circuit.country}, ${race.circuit.city}", redColor)
        setSpannableText(binding.tvLength, "Circuit length: ", race.circuit.circuitLength.replace("k",""), redColor)
        setSpannableText(binding.tvLaps, "Laps in a race: ", race.laps.toString(), redColor)

        val winner = "${race.winner?.name ?: ""} ${race.winner?.surname ?: ""}".trim()
        setSpannableText(binding.tvWinner, "Winner in 2025: ", winner, redColor)
    }

    private fun setSpannableText(textView: TextView, label: String, value: String, color: Int) {
        val spannable = SpannableStringBuilder(label + value)
        spannable.setSpan(
            ForegroundColorSpan(color),
            label.length,
            label.length + value.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = spannable
    }

    private fun renameRace(original: String): String {
        return when (original) {
            "Louis Vuitton Australian Grand Prix 2025" -> "Australian Grand Prix"
            "Heineken Chinese Grand Prix 2025" -> "Chinese Grand Prix"
            "Lenovo Japanese Grand Prix 2025" -> "Japanese Grand Prix"
            "Bahrain Grand Prix 2025" -> "Bahrain Grand Prix"
            "STC Saudi Arabian Grand Prix 2025" -> "Saudi Arabian Grand Prix"
            "CRYPTO.COM Miami Grand Prix 2025" -> "Miami Grand Prix"
            "AWS Gran Premio del Made in Italy e Dell'Emilia-Romagna 2025" -> "Imola Grand Prix"
            "Grand Prix de Monaco 2025" -> "Monaco Grand Prix"
            "Aramco Gran Premio de España 2025" -> "Spanish Grand Prix"
            "Pirelli Grand Prix Du Canada 2025" -> "Canadian Grand Prix"
            "MSC Cruises Austrian Grand Prix 2025" -> "Austrian Grand Prix"
            "Qatar Airways British Grand Prix 2025" -> "British Grand Prix"
            "Belgian Grand Prix 2025" -> "Belgian Grand Prix"
            "Lenovo Hungarian Grand Prix 2025" -> "Hungarian Grand Prix"
            "Heineken Dutch Grand Prix 2025" -> "Dutch Grand Prix"
            "Pirelli Gran Premio d'Italia 2025" -> "Italian Grand Prix"
            "Qatar Airways Azerbaijan Grand Prix 2025" -> "Azerbaijan Grand Prix"
            "Singapore Airlines Singapore Grand Prix 2025" -> "Singapore Grand Prix"
            "MSC Cruises United States Grand Prix 2025" -> "United States Grand Prix"
            "Gran Premio de La Ciudad de México 2025" -> "Mexican Grand Prix"
            "MSC Cruises Grande Prêmio de São Paulo 2025" -> "Brazilian Grand Prix"
            "Heineken Las Vegas Grand Prix 2025" -> "Las Vegas Grand Prix"
            "Qatar Airways Qatar Grand Prix 2025" -> "Qatar Grand Prix"
            "Etihad Airways Abu Dhabi Grand Prix 2025" -> "Abu Dhabi Grand Prix"
            else -> original
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
