package com.example.f1app2.presentation.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.f1app2.R
import com.example.f1app2.domain.model.ConstructorStanding

class TeamsAdapter(
    private val context: Context,
    private val onItemClick: (ConstructorStanding) -> Unit
) : RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>() {

    private var teams = listOf<ConstructorStanding>()

    fun submitList(newTeams: List<ConstructorStanding>) {
        teams = newTeams
        notifyDataSetChanged()
    }

    inner class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTeamName: TextView = view.findViewById(R.id.tvTeamName)
        val tvTeamPoints: TextView = view.findViewById(R.id.tvTeamPoints)
        val tvTeamBg: FrameLayout = view.findViewById(R.id.team_background)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.team_list_item, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = teams[position]

        val displayName = formatTeamName(team.team.teamName)
        holder.tvTeamName.text = "${position + 1}. $displayName"
        holder.tvTeamPoints.text = "${team.points} pts"

        val bgColor = getTeamColor(team.team.teamName)
        val background = holder.tvTeamBg.background as GradientDrawable
        background.setColor(bgColor)

        holder.itemView.setOnClickListener {
            Log.d("TEAM_CLICK", "Clicked on team: ${team.teamId}")
            onItemClick(team)
        }
    }

    override fun getItemCount() = teams.size

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
            else -> ContextCompat.getColor(context, R.color.red_main)
        }
    }

    private fun formatTeamName(originalName: String): String {
        var name = originalName
            .replace("Formula 1 Team", "", ignoreCase = true)
            .replace("F1 Team", "", ignoreCase = true)
            .trim()

        if (name.equals("RB", ignoreCase = true)) {
            name = "Racing Bulls"
        }

        return name
    }
}
