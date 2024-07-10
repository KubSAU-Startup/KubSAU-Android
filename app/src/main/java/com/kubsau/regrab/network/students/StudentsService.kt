package com.kubsau.regrab.network.students

import com.kubsau.regrab.model.Student
import com.kubsau.regrab.network.ErrorModel
import com.kubsau.regrab.network.ResponseModel
import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path

interface StudentsService {

    @GET("/students/{id}")
    suspend fun getStudentById(@Path("id") studentId: Int): ApiResult<ResponseModel<Student>, ErrorModel>
}
