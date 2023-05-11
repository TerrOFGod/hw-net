package com.example.data.managers

import android.security.ConfirmationAlreadyPresentingException
import com.example.core.dtos.auth.Result
import com.example.core.dtos.auth.LoginUserItem
import com.example.core.dtos.auth.RegisterUserItem
import com.example.core.errors.UnreachableError
import com.example.core.managers.UserManager
import com.example.data.api.UsersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

class UserManagerImpl @Inject constructor(
    private val usersApi: UsersApi
) : UserManager {
    override suspend fun register(username: String,email: String, password: String, confirm: String): Result<String, String> {
        val body = RegisterUserItem(username, email, password, confirm)
        val response = usersApi.register(body)

        if (response.isSuccessful) {
            return Result.success(response.body()!!)
        }

        if (response.code() == 400) {
            val validationResponse = response.errorBody()
            return Result.failure(validationResponse.toString())
        }

        throw UnreachableError()
    }

    override suspend fun login(email: String, password: String, remember: Boolean): Result<String, String> {
        val body = LoginUserItem(email, password, remember)
        val response = usersApi.login(body)

        if (response.isSuccessful) {
            return Result.success(response.body()!!)
        }

        if (response.code() == 400) {
            val validationResponse = response.errorBody()
            return Result.failure(validationResponse.toString())
        }

        throw UnreachableError()
    }

}

@Module
@InstallIn(SingletonComponent::class)
object UserManagerModule {
    @Provides
    @Singleton
    fun provideUserManager(usersApi: UsersApi): UserManager {
        return UserManagerImpl(usersApi)
    }
}
