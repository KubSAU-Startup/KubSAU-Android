package com.example.diploma.network

import com.example.diploma.network.calladapter.NetworkResponse
import com.example.diploma.network.models.ResponseFail
import com.example.diploma.network.models.ResponseSuccess
import com.example.diploma.network.models.account.Account
import com.example.diploma.network.models.account.AccountInfo
import com.example.diploma.network.models.discipline.Discipline
import com.example.diploma.network.models.filter.Filter
import com.example.diploma.network.models.journal.Journal
import com.example.diploma.network.models.student.Student
import com.example.diploma.network.models.work.Work
import com.example.diploma.network.models.worktype.WorkType
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

typealias Response<T> = NetworkResponse<ResponseSuccess<T>, ResponseFail<T>>

interface Api {
    @GET("auth")
    suspend fun auth(@QueryMap authInfo: Map<String, String>): Response<Account>

    @GET("account")
    suspend fun accountInfo(): Response<AccountInfo>

    @GET("/journals")
    suspend fun journals(@QueryMap filters: Map<String, Int>): Response<Journal>

    @GET("/journals/filters")
    suspend fun filters(): Response<Filter>

    @GET("/disciplines/{id}")
    suspend fun discipline(@Path("id") disciplineId: Int): Response<Discipline>

    @GET("/worktypes/{id}")
    suspend fun workTypes(@Path("id") workTypeId: Int): Response<WorkType>

    @GET("/students/{id}")
    suspend fun student(@Path("id") studentID: Int): Response<Student>

    @FormUrlEncoded
    @POST("/works")
    suspend fun workRegistration(
        @Field("disciplineId") disciplineId: Int,
        @Field("studentId") studentId: Int,
        @Field("registrationDate") registrationDate: Int,
        @Field("title") title: String?
    ): Response<Work>
}