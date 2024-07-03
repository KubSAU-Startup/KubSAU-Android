package com.kubsau.regrab.network.employees

import com.kubsau.regrab.model.Employee
import com.kubsau.regrab.network.ErrorDomain
import com.slack.eithernet.ApiResult

interface EmployeeRepository {

    suspend fun getEmployeeById(employeeId: Int): ApiResult<Employee, ErrorDomain>
}
