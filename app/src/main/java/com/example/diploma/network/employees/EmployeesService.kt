package com.example.diploma.network.employees

import com.example.diploma.model.Employee
import com.example.diploma.network.ErrorModel
import com.example.diploma.network.ResponseModel
import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeesService {

    @GET("employees/{id}")
    suspend fun getEmployeeById(@Path("id") employeeId: Int): ApiResult<ResponseModel<Employee>, ErrorModel>
}
