package com.example.f1app2.presentation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.f1app2.R
import com.example.f1app2.domain.model.DriverStanding

class DriversAdapter(
    private val context: Context,
    private val onItemClick: (DriverStanding) -> Unit
) : RecyclerView.Adapter<DriversAdapter.DriverViewHolder>() {

    private var drivers = listOf<DriverStanding>()

    fun submitList(newDrivers: List<DriverStanding>) {
        drivers = newDrivers
        notifyDataSetChanged()
    }

    inner class DriverViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.driver_card)
        val helmetImage: ImageView = view.findViewById(R.id.driver_helmet)
        val nameText: TextView = view.findViewById(R.id.driver_name)
        val pointsText: TextView = view.findViewById(R.id.driver_points)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.driver_list_item, parent, false)
        return DriverViewHolder(view)
    }

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        val driverStanding = drivers[position]
        val driver = driverStanding.driver

        val teamName = driverStanding.team?.teamName ?: ""
        val nameParts = driver.name.trim().split(" ")
        val filteredName = if (nameParts.size > 3) {
            "${nameParts.first()} ${nameParts.last()}"
        } else {
            driver.name
        }

        val surnameParts = driver.surname.trim().split(" ")
        val filteredSurname = if (surnameParts.size > 2) {
            surnameParts[1]
        } else {
            driver.surname
        }

        val fullName = "$filteredName $filteredSurname"
        val points = driverStanding.points

        holder.nameText.text = "${position + 1}. $fullName"
        holder.pointsText.text = "$points pts"

        val helmetResourceName = "${driverStanding.driverId}_helmet"
        val helmetResId = context.resources.getIdentifier(
            helmetResourceName, "drawable", context.packageName
        )
        if (helmetResId != 0) {
            holder.helmetImage.setImageResource(helmetResId)
        } else {
            holder.helmetImage.setImageDrawable(null)
        }

        val teamColor = getTeamColor(teamName)
        holder.cardView.setCardBackgroundColor(teamColor)

        holder.itemView.setOnClickListener {
            Log.d("DRIVER_LOG", "${position + 1}. $fullName $points pts Team: $teamName")
            onItemClick(driverStanding)
        }
    }

    override fun getItemCount() = drivers.size

    private fun getTeamColor(teamName: String): Int {
        return when (teamName.lowercase()) {
            "mclaren formula 1 team", "mclaren" -> ContextCompat.getColor(context, R.color.mclaren_color)
            "scuderia ferrari", "ferrari" -> ContextCompat.getColor(context, R.color.ferrari_color)
            "mercedes formula 1 team", "mercedes" -> ContextCompat.getColor(context, R.color.mercedes_color)
            "red bull racing" -> ContextCompat.getColor(context, R.color.red_bull_color)
            "williams racing" -> ContextCompat.getColor(context, R.color.williams_color)
            "rb f1 team" -> ContextCompat.getColor(context, R.color.racing_bulls_color)
            "haas f1 team" -> ContextCompat.getColor(context, R.color.haas_color)
            "sauber f1 team" -> ContextCompat.getColor(context, R.color.sauber_color)
            "aston martin f1 team" -> ContextCompat.getColor(context, R.color.aston_martin_color)
            "alpine f1 team" -> ContextCompat.getColor(context, R.color.alpine_color)
            else -> ContextCompat.getColor(context, android.R.color.white)
        }
    }
}
