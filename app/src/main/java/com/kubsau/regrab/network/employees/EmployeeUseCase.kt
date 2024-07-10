package com.kubsau.regrab.network.employees

import com.kubsau.regrab.model.Employee
import com.kubsau.regrab.network.State
import kotlinx.coroutines.flow.Flow

interface EmployeeUseCase {

    fun getEmployeeById(employeeId: Int): Flow<State<Employee>>
}
