package com.example.diploma.network.worktypes

import com.example.diploma.network.ErrorModel
import com.example.diploma.network.ResponseModel
import com.example.diploma.model.WorkType
import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path

interface WorkTypesService {

    @GET("/worktypes/{id}")
    suspend fun getWorkTypeById(@Path("id") workTypeId: Int): ApiResult<ResponseModel<WorkType>, ErrorModel>
}
