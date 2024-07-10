package com.kubsau.regrab.ui.screens.auth

import com.kubsau.regrab.common.storage.AccountConfig
import com.kubsau.regrab.common.storage.NetworkConfig
import com.kubsau.regrab.network.State
import com.kubsau.regrab.network.account.AccountUseCase
import com.kubsau.regrab.network.auth.AuthUseCase
import com.kubsau.regrab.network.employees.EmployeeUseCase
import com.kubsau.regrab.network.listenValue
import com.kubsau.regrab.network.processState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

class LoginUseCaseImpl(
    private val authUseCase: AuthUseCase,
    private val accountUseCase: AccountUseCase,
    private val employeeUseCase: EmployeeUseCase
) : LoginUseCase {

    // looks like abomination but it's alright
    override fun getSessionInfo(
        login: String,
        password: String
    ): Flow<State<LoginInfo>> = channelFlow {
        send(State.Loading)

        coroutineScope {
            authUseCase.getSessionInfo(login, password)
                .listenValue { state ->
                    state.processState(
                        error = { error -> send(error) },
                        success = { sessionInfo ->
                            val token = sessionInfo.token
                            val departmentId = sessionInfo.departmentIds.first()

                            NetworkConfig.token = token

                            accountUseCase.getAccountInfo()
                                .listenValue { state ->
                                    state.processState(
                                        error = { error ->
                                            send(error)
                                            NetworkConfig.token = ""
                                        },
                                        success = { accountInfo ->
                                            if (accountInfo.type != 3) {
                                                NetworkConfig.token = ""
                                                send(
                                                    State.Success(
                                                        LoginInfo(
                                                            wrongType = true,
                                                            token = "",
                                                            departmentId = -1,
                                                            departmentName = ""
                                                        )
                                                    )
                                                )
                                            } else {
                                                val departmentName =
                                                    accountInfo.departments.first().title

                                                employeeUseCase.getEmployeeById(accountInfo.employeeId)
                                                    .listenValue { state ->
                                                        state.processState(
                                                            error = { error ->
                                                                send(error)
                                                                NetworkConfig.token = ""
                                                            },
                                                            success = { employee ->
                                                                AccountConfig.fullName =
                                                                    with(employee) {
                                                                        middleName?.let {
                                                                            "%s %s %s".format(
                                                                                lastName,
                                                                                firstName,
                                                                                middleName
                                                                            )
                                                                        } ?: "%s %s".format(
                                                                            lastName,
                                                                            firstName
                                                                        )
                                                                    }

                                                                send(
                                                                    State.Success(
                                                                        LoginInfo(
                                                                            wrongType = false,
                                                                            token = token,
                                                                            departmentId = departmentId,
                                                                            departmentName = departmentName
                                                                        )
                                                                    )
                                                                )
                                                            }
                                                        )
                                                    }
                                            }
                                        }
                                    )
                                }
                        }
                    )
                }
        }
    }
}

data class LoginInfo(
    val wrongType: Boolean,
    val token: String,
    val departmentId: Int,
    val departmentName: String
)
