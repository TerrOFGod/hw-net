package com.example.collectit.screens.account.login

import android.content.SharedPreferences
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectit.infrastructure.AppState
import com.example.collectit.infrastructure.AppStateMachine
import com.example.core.Constants
import com.example.core.managers.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userManager: UserManager,
    private val sp: SharedPreferences,
    private val machine: AppStateMachine
) : ViewModel() {
    fun login(email: String, password: String, remember: Boolean) {
        viewModelScope.launch {

            val response = userManager.login(email, password, remember)

            if (response.success) {
                sp.edit()
                    .putString(Constants.SharedPreferences.ACCESS_TOKEN, response.value!!)
                    .apply()
                machine.currentState.value = AppState.Home;
            }
        }
    }
}