package com.example.diploma.network.employees

import com.example.diploma.model.Employee
import com.example.diploma.network.ErrorDomain
import com.slack.eithernet.ApiResult

interface EmployeeRepository {

    suspend fun getEmployeeById(employeeId: Int): ApiResult<Employee, ErrorDomain>
}
