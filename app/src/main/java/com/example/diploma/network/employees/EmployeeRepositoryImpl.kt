package com.example.diploma.network.employees

import com.example.diploma.common.mapDefault
import com.example.diploma.model.Employee
import com.example.diploma.network.ErrorDomain
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmployeeRepositoryImpl(private val service: EmployeesService) : EmployeeRepository {

    override suspend fun getEmployeeById(employeeId: Int): ApiResult<Employee, ErrorDomain> =
        withContext(Dispatchers.IO) { service.getEmployeeById(employeeId).mapDefault() }
}
