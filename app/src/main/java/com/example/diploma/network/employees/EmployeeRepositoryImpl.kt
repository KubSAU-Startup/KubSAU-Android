package com.example.diploma.network.employees

import com.example.diploma.common.foldOnSuccess
import com.example.diploma.model.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmployeeRepositoryImpl(private val service: EmployeesService) : EmployeeRepository {

    override suspend fun getEmployeeById(employeeId: Int): Employee? = withContext(Dispatchers.IO) {
        service.getEmployeeById(employeeId).foldOnSuccess()
    }
}
