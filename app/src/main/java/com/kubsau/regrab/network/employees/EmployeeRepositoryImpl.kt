package com.kubsau.regrab.network.employees

import com.kubsau.regrab.common.mapDefault
import com.kubsau.regrab.model.Employee
import com.kubsau.regrab.network.ErrorDomain
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmployeeRepositoryImpl(private val service: EmployeesService) : EmployeeRepository {

    override suspend fun getEmployeeById(employeeId: Int): ApiResult<Employee, ErrorDomain> =
        withContext(Dispatchers.IO) { service.getEmployeeById(employeeId).mapDefault() }
}
