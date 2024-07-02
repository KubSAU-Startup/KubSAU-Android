package com.example.diploma.network.employees

import com.example.diploma.common.mapToState
import com.example.diploma.model.Employee
import com.example.diploma.network.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EmployeeUseCaseImpl(private val repository: EmployeeRepository) : EmployeeUseCase {

    override fun getEmployeeById(employeeId: Int): Flow<State<Employee>> = flow {
        emit(State.Loading)

        val newState = repository.getEmployeeById(employeeId).mapToState()
        emit(newState)
    }
}
