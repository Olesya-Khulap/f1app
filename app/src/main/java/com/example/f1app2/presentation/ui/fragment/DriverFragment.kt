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
import com.example.f1app2.databinding.FragmentDriverBinding
import com.example.f1app2.presentation.adapter.ImageSliderAdapter
import com.example.f1app2.presentation.viewmodel.DriverViewModel
import com.example.f1app2.presentation.state.UiState
import com.example.f1app2.domain.model.DriverStanding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import android.text.SpannableStringBuilder
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.widget.TextView

@AndroidEntryPoint
class DriverFragment : Fragment() {

    private var _binding: FragmentDriverBinding? = null
    private val binding get() = _binding!!

    private val args: DriverFragmentArgs by navArgs()
    private val viewModel: DriverViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDriverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadDriver(args.driverId)
        observeViewModel()

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.driverState.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            bindDriver(state.data)
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

    private fun bindDriver(driverStanding: DriverStanding) {
        val driver = driverStanding.driver

        val images = listOfNotNull(
            getResId("${driverStanding.driverId}_1"),
            getResId("${driverStanding.driverId}_2")
        ).ifEmpty {
            listOf(android.R.drawable.ic_menu_gallery)
        }

        binding.driverImageSlider.adapter = ImageSliderAdapter(requireContext(), images)

        val flagRes = getResId(driver.nationality.lowercase().replace(" ", ""))
        if (flagRes != 0) {
            binding.driverFlag.setImageResource(flagRes)
        } else {
            binding.driverFlag.setImageDrawable(null)
        }

        binding.driverFullName.text = "${driver.name} ${driver.surname}"

        val red = ContextCompat.getColor(requireContext(), R.color.red_main)
        val blue = ContextCompat.getColor(requireContext(), R.color.dark_blue_main)

        setSpannableText(binding.tvNumber, "Number: ", driver.number.toString(), red, blue)
        setSpannableText(binding.tvBirthday, "Birthday: ", driver.birthday, red, blue)
        setSpannableText(binding.tvTeam, "Team: ", driverStanding.team?.teamName ?: "-", red, blue)
        setSpannableText(binding.tvPoints, "Points in 2025: ", driverStanding.points.toString(), red, blue)
        setSpannableText(binding.tvWins, "Wins in 2025: ", driverStanding.wins.toString(), red, blue)
        setSpannableText(binding.tvPosition, "Position in 2025: ", driverStanding.position.toString(), red, blue)
    }

    private fun getResId(name: String): Int =
        resources.getIdentifier(name, "drawable", requireContext().packageName)

    private fun setSpannableText(textView: TextView, label: String, value: String, valColor: Int, labelColor: Int) {
        val sb = SpannableStringBuilder(label + value)
        sb.setSpan(ForegroundColorSpan(labelColor), 0, label.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        sb.setSpan(ForegroundColorSpan(valColor), label.length, label.length + value.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = sb
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
