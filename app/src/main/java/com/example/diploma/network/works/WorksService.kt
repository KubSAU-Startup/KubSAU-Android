package com.example.diploma.network.works

import com.example.diploma.model.Filter
import com.example.diploma.model.GetLatestWorksResponse
import com.example.diploma.model.Work
import com.example.diploma.network.ApiError
import com.example.diploma.network.ApiResponse
import com.slack.eithernet.ApiResult
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

typealias Response<T> = ApiResult<ApiResponse<T>, ApiError>

interface WorksService {

    @GET("/works/latest")
    suspend fun getLatestWorks(
        @Query("offset") offset: Int?,
        @QueryMap searchParams: Map<String, Int>,
    ): Response<GetLatestWorksResponse>

    @GET("/works/latest/filters/worktypes")
    suspend fun getWorkTypesFilters(): Response<List<Filter>>

    @GET("/works/latest/filters/disciplines")
    suspend fun getDisciplinesFilters(): Response<List<Filter>>

    @GET("/works/latest/filters/employees")
    suspend fun getEmployeesFilters(): Response<List<Filter>>

    @GET("/works/latest/filters/groups")
    suspend fun getGroupsFilters(): Response<List<Filter>>

    @FormUrlEncoded
    @POST("/works")
    suspend fun registerNewWork(@FieldMap map: Map<String, String>): Response<Work>
}
