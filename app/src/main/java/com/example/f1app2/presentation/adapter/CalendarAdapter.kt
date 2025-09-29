package com.example.f1app2.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.f1app2.R
import com.example.f1app2.domain.model.Race

class CalendarAdapter(
    private val context: Context,
    private val races: List<Race>,
    private val onItemClick: (Race) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.race_image)
        val tName: TextView = view.findViewById(R.id.race_name)
        val tTrack: TextView = view.findViewById(R.id.circuit_name)
        val tDates: TextView = view.findViewById(R.id.race_dates)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_list_item, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val race = races[position]

        holder.tName.text = renameRace(race.raceName)
        holder.tTrack.text = race.circuit.circuitName

        val dateRange = if (race.schedule.fp1 != null && race.schedule.race != null) {
            formatDateRange(race.schedule.fp1!!.date, race.schedule.race!!.date)
        } else {
            "Dates TBD"
        }
        holder.tDates.text = dateRange

        val cityKey = race.circuit.city.replace(" ", "").lowercase()
        val imageResId = context.resources.getIdentifier(cityKey, "drawable", context.packageName)
        if (imageResId != 0) {
            holder.image.setImageResource(imageResId)
        } else {
            holder.image.setImageResource(android.R.drawable.ic_menu_gallery)
        }

        holder.itemView.setOnClickListener { onItemClick(race) }
    }

    override fun getItemCount() = races.size

    private fun formatDateRange(start: String, end: String): String {
        val startParts = start.split("-")
        val endParts = end.split("-")
        val day1 = startParts[2].toInt()
        val day2 = endParts[2].toInt()
        val month = getMonthName(endParts[1].toInt())
        return "$day1-$day2 $month"
    }

    private fun getMonthName(month: Int): String {
        return when (month) {
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "July"
            8 -> "August"
            9 -> "September"
            10 -> "October"
            11 -> "November"
            12 -> "December"
            else -> ""
        }
    }

    private fun renameRace(original: String): String {
        return when (original) {
            "Louis Vuitton Australian Grand Prix 2025" -> "Australian Grand Prix 2025"
            "Heineken Chinese Grand Prix 2025" -> "Chinese Grand Prix 2025"
            "Lenovo Japanese Grand Prix 2025" -> "Japanese Grand Prix 2025"
            "Bahrain Grand Prix 2025" -> "Bahrain Grand Prix 2025"
            "STC Saudi Arabian Grand Prix 2025" -> "Saudi Arabian Grand Prix 2025"
            "CRYPTO.COM Miami Grand Prix 2025" -> "Miami Grand Prix 2025"
            "AWS Gran Premio del Made in Italy e Dell'Emilia-Romagna 2025" -> "Imola Grand Prix 2025"
            "Grand Prix de Monaco 2025" -> "Monaco Grand Prix 2025"
            "Aramco Gran Premio de España 2025" -> "Spanish Grand Prix 2025"
            "Pirelli Grand Prix Du Canada 2025" -> "Canadian Grand Prix 2025"
            "MSC Cruises Austrian Grand Prix 2025" -> "Austrian Grand Prix 2025"
            "Qatar Airways British Grand Prix 2025" -> "British Grand Prix 2025"
            "Belgian Grand Prix 2025" -> "Belgian Grand Prix 2025"
            "Lenovo Hungarian Grand Prix 2025" -> "Hungarian Grand Prix 2025"
            "Heineken Dutch Grand Prix 2025" -> "Dutch Grand Prix 2025"
            "Pirelli Gran Premio d'Italia 2025" -> "Italian Grand Prix 2025"
            "Qatar Airways Azerbaijan Grand Prix 2025" -> "Azerbaijan Grand Prix 2025"
            "Singapore Airlines Singapore Grand Prix 2025" -> "Singapore Grand Prix 2025"
            "MSC Cruises United States Grand Prix 2025" -> "United States Grand Prix 2025"
            "Gran Premio de La Ciudad de México 2025" -> "Mexican Grand Prix 2025"
            "MSC Cruises Grande Prêmio de São Paulo 2025" -> "Brazilian Grand Prix 2025"
            "Heineken Las Vegas Grand Prix 2025" -> "Las Vegas Grand Prix 2025"
            "Qatar Airways Qatar Grand Prix 2025" -> "Qatar Grand Prix 2025"
            "Etihad Airways Abu Dhabi Grand Prix 2025" -> "Abu Dhabi Grand Prix 2025"
            else -> original
        }
    }
}
