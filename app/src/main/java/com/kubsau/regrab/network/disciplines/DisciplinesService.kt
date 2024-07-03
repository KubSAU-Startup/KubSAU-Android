package com.kubsau.regrab.network.disciplines

import com.kubsau.regrab.network.ErrorModel
import com.kubsau.regrab.network.ResponseModel
import com.kubsau.regrab.model.Discipline
import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path

interface DisciplinesService {

    @GET("/disciplines/{id}")
    suspend fun getDisciplineById(@Path("id") disciplineId: Int): ApiResult<ResponseModel<Discipline>, ErrorModel>
}
