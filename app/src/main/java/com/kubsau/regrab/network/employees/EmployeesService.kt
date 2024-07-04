package com.kubsau.regrab.network.employees

import com.kubsau.regrab.model.Employee
import com.kubsau.regrab.network.ErrorModel
import com.kubsau.regrab.network.ResponseModel
import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeesService {

    @GET("employees/{id}")
    suspend fun getEmployeeById(@Path("id") employeeId: Int): ApiResult<ResponseModel<Employee>, ErrorModel>
}
