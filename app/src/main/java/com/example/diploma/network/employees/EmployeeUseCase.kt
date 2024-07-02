package com.example.diploma.network.employees

import com.example.diploma.model.Employee
import com.example.diploma.network.State
import kotlinx.coroutines.flow.Flow

interface EmployeeUseCase {

    fun getEmployeeById(employeeId: Int): Flow<State<Employee>>
}
