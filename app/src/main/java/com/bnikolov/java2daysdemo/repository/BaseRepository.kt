package com.bnikolov.java2daysdemo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnikolov.java2daysdemo.R
import com.bnikolov.java2daysdemo.enum.ErrorCode
import com.bnikolov.java2daysdemo.network.manager.ConnectivityManager
import com.bnikolov.java2daysdemo.network.model.Error
import com.bnikolov.java2daysdemo.util.Event

abstract class BaseRepository {

    private val errorMessageMutableLiveData = MutableLiveData<Event<Error?>?>()

    val errorMessageLiveData: LiveData<Event<Error?>?> = errorMessageMutableLiveData

    protected fun checkConnected(context: Context): Boolean {
        return if (!ConnectivityManager.isConnected(context)) {
            handleStatusCode(
                context,
                ErrorCode.ERROR_CODE_NO_INTERNET_CONNECTION
            )
            false
        } else true
    }

    protected fun handleStatusCode(
        context: Context,
        error: ErrorCode
    ) {
        return when (error) {
            ErrorCode.ERROR_CODE_NO_INTERNET_CONNECTION -> {
                errorMessageMutableLiveData.value =
                    Event(
                        Error(
                            error.code,
                            context.getString(R.string.error_no_internet_connection)
                        )
                    )
            }
            ErrorCode.UNAUTHORIZED -> {
                errorMessageMutableLiveData.value = getDefaultErrorEvent(context)
            }
            ErrorCode.CONFLICT -> {
                errorMessageMutableLiveData.value = getDefaultErrorEvent(context)
            }
            else -> {
                errorMessageMutableLiveData.value = getDefaultErrorEvent(context)
            }
        }
    }

    private fun getDefaultErrorEvent(context: Context) =
        Event(
            Error(
                ErrorCode.ERROR_CODE_GENERAL.code,
                context.getString(R.string.general_error_message)
            )
        )
}