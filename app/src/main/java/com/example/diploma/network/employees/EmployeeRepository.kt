package com.example.diploma.network.employees

import com.example.diploma.model.Employee

interface EmployeeRepository {

    suspend fun getEmployeeById(employeeId: Int): Employee?
}
