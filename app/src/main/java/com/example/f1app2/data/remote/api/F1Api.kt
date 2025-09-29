package com.example.f1app2.data.remote.api

import com.example.f1app2.domain.model.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface F1Api {
    @GET("2025/constructors-championship")
    suspend fun getConstructorStandings(): ConstructorListResponse

    @GET("2025/drivers-championship")
    suspend fun getDriverStandings(): DriverStandingsResponse

    @GET("2025")
    suspend fun getCalendar(): CalendarResponse

    @GET("2025/teams")
    suspend fun getTeams(): TeamsResponse

    @GET("2025/teams/{teamId}/drivers")
    suspend fun getTeamDrivers(@Path("teamId") teamId: String): TeamDriversResponse

    @GET("2025/teams/{teamId}/drivers")
    suspend fun getTeamDriversRaw(@Path("teamId") teamId: String): Response<ResponseBody>
}
