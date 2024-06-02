package com.example.diploma.network

import com.example.diploma.network.calladapter.NetworkResponse
import com.example.diploma.network.models.ResponseFail
import com.example.diploma.network.models.ResponseSuccess
import com.example.diploma.network.models.account.Account
import com.example.diploma.network.models.back.BackVersion
import com.example.diploma.network.models.discipline.Discipline
import com.example.diploma.network.models.filter.Filter
import com.example.diploma.network.models.journal.Journal
import com.example.diploma.network.models.student.X
import com.example.diploma.network.models.work.Work
import com.example.diploma.network.models.worktype.WorkType
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

typealias Response<T> = NetworkResponse<ResponseSuccess<T>, ResponseFail<T>>

interface Api {
    @GET("/")
    suspend fun checkUrl(): BackVersion

    @POST("/auth")
    @FormUrlEncoded
    suspend fun auth(@FieldMap authInfo: Map<String, String>): Response<Account>

    @GET("/works/latest")
    suspend fun journals(
        @QueryMap searchParams: Map<String, Int>,
        @Query("offset") offset: Int
    ): Response<Journal>

    @GET("/works/latest/filters/worktypes")
    suspend fun filterWorkTypes(): Response<List<Filter>>

    @GET("/works/latest/filters/disciplines")
    suspend fun filterDisciplines(): Response<List<Filter>>

    @GET("/works/latest/filters/employees")
    suspend fun filterEmployees(): Response<List<Filter>>

    @GET("/works/latest/filters/groups")
    suspend fun filterGroups(): Response<List<Filter>>

    @GET("/disciplines/{id}")
    suspend fun discipline(@Path("id") disciplineId: Int): Response<Discipline>

    @GET("/worktypes/{id}")
    suspend fun workTypes(@Path("id") workTypeId: Int): Response<WorkType>

    @GET("/students/{id}")
    suspend fun student(@Path("id") studentID: Int): Response<X>

    @FormUrlEncoded
    @POST("/works")
    suspend fun workRegistration(
        @FieldMap map: Map<String, String>
    ): Response<Work>
}