package com.example.diploma.network.students

import com.example.diploma.model.Student
import com.example.diploma.network.ErrorModel
import com.example.diploma.network.ResponseModel
import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path

interface StudentsService {

    @GET("/students/{id}")
    suspend fun getStudentById(@Path("id") studentId: Int): ApiResult<ResponseModel<Student>, ErrorModel>
}
