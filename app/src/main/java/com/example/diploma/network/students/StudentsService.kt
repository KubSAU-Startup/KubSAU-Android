package com.example.diploma.network.students

import com.example.diploma.model.Student
import com.example.diploma.network.ApiError
import com.example.diploma.network.ApiResponse
import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path

interface StudentsService {

    @GET("/students/{id}")
    suspend fun getStudentById(@Path("id") studentId: Int): ApiResult<ApiResponse<Student>, ApiError>
}
