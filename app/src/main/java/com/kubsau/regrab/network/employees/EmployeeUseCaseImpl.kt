package com.kubsau.regrab.network.employees

import com.kubsau.regrab.common.mapToState
import com.kubsau.regrab.model.Employee
import com.kubsau.regrab.network.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EmployeeUseCaseImpl(private val repository: EmployeeRepository) : EmployeeUseCase {

    override fun getEmployeeById(employeeId: Int): Flow<State<Employee>> = flow {
        emit(State.Loading)

        val newState = repository.getEmployeeById(employeeId).mapToState()
        emit(newState)
    }
}
