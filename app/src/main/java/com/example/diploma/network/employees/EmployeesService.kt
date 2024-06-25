package com.example.diploma.network.employees

import com.example.diploma.model.Employee
import com.example.diploma.network.ApiError
import com.example.diploma.network.ApiResponse
import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeesService {

    @GET("employees/{id}")
    suspend fun getEmployeeById(@Path("id") employeeId: Int): ApiResult<ApiResponse<Employee>, ApiError>
}
