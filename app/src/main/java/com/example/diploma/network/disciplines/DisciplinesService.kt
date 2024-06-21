package com.example.diploma.network.disciplines

import com.example.diploma.network.ApiError
import com.example.diploma.network.ApiResponse
import com.example.diploma.model.Discipline
import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path

interface DisciplinesService {

    @GET("/disciplines/{id}")
    suspend fun getDisciplineById(@Path("id") disciplineId: Int): ApiResult<ApiResponse<Discipline>, ApiError>
}
