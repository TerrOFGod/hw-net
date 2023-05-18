package com.example.collectit.screens.account.login

import android.content.SharedPreferences
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.MutableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectit.infrastructure.AppState
import com.example.collectit.infrastructure.AppStateMachine
import com.example.core.Constants
import com.example.core.managers.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userManager: UserManager,
    private val sp: SharedPreferences,
    private val machine: AppStateMachine,
    private val constants: MutableLiveData<Boolean>
) : ViewModel() {
    private val _success: MutableLiveData<Boolean> = MutableLiveData(false)
    val success: LiveData<Boolean> get() = _success
    fun login(email: String, password: String, remember: Boolean) {
        viewModelScope.launch{
            val response = userManager.login(email, password, remember)

            if (response.success) {
                sp.edit()
                    .putString(Constants.SharedPreferences.ACCESS_TOKEN, response.value!!)
                    .apply()
                constants.value = true
                _success.value = true
                machine.currentState.value = AppState.Home;
            }
        }
    }
}