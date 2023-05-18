package com.example.collectit.screens.account.signup

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
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
class SignUpViewModel @Inject constructor(
    private val userManager: UserManager,
    private val sp: SharedPreferences,
    private val machine: AppStateMachine,
    private val constants: MutableLiveData<Boolean>
) : ViewModel() {
    fun register(username: String, email: String, password: String, confirm: String) {
        viewModelScope.launch {

            val response = userManager.register(username, email, password, confirm)

            if (response.success) {
                sp.edit()
                    .putString(Constants.SharedPreferences.ACCESS_TOKEN, response.value!!)
                    .apply()
                constants.value = true
                machine.currentState.value = AppState.Home;
            }
        }
    }
}