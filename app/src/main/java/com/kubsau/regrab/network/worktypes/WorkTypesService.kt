package com.kubsau.regrab.network.worktypes

import com.kubsau.regrab.network.ErrorModel
import com.kubsau.regrab.network.ResponseModel
import com.kubsau.regrab.model.WorkType
import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path

interface WorkTypesService {

    @GET("/worktypes/{id}")
    suspend fun getWorkTypeById(@Path("id") workTypeId: Int): ApiResult<ResponseModel<WorkType>, ErrorModel>
}
