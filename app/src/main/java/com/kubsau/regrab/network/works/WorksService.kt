package com.kubsau.regrab.network.works

import com.kubsau.regrab.model.Filter
import com.kubsau.regrab.model.GetLatestWorksResponse
import com.kubsau.regrab.model.Work
import com.kubsau.regrab.network.ErrorModel
import com.kubsau.regrab.network.ResponseModel
import com.slack.eithernet.ApiResult
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

typealias Response<T> = ApiResult<ResponseModel<T>, ErrorModel>

interface WorksService {

    @GET("/works/latest")
    suspend fun getLatestWorks(
        @Query("offset") offset: Int?,
        @QueryMap searchParams: Map<String, String>,
    ): Response<GetLatestWorksResponse>

    @GET("/works/latest/filters/worktypes")
    suspend fun getWorkTypesFilters(): Response<List<Filter>>

    @GET("/works/latest/filters/disciplines")
    suspend fun getDisciplinesFilters(): Response<List<Filter>>

    @GET("/works/latest/filters/employees")
    suspend fun getEmployeesFilters(@Query("shrinkNames") shrinkNames: Boolean): Response<List<Filter>>

    @GET("/works/latest/filters/groups")
    suspend fun getGroupsFilters(): Response<List<Filter>>

    @FormUrlEncoded
    @POST("/works")
    suspend fun registerNewWork(@FieldMap map: Map<String, String>): Response<Work>
}
