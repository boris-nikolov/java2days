package com.bnikolov.java2daysdemo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnikolov.java2daysdemo.enum.ErrorCode
import com.bnikolov.java2daysdemo.network.manager.RequestManager
import com.bnikolov.java2daysdemo.network.model.Error
import com.bnikolov.java2daysdemo.network.model.Repository
import com.bnikolov.java2daysdemo.util.Event
import dagger.hilt.android.qualifiers.ActivityContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GithubRepoRepository @Inject constructor(
    @ActivityContext private val context: Context,
    private val requestManager: RequestManager
) : BaseRepository() {

    private val repositoriesMutableLiveData = MutableLiveData<Event<List<Repository?>?>>()

    private val errorMessageMutableLiveData = MutableLiveData<Event<Error?>?>()

    val repositoriesLiveData: LiveData<Event<List<Repository?>?>> = repositoriesMutableLiveData

    val errorMessageLiveData: LiveData<Event<Error?>?> = errorMessageMutableLiveData

    fun getRepositories() {
        if (!checkConnected(context, errorMessageMutableLiveData)) return

        try {
            val request = requestManager.getRepositories()
            request.enqueue(object : Callback<Array<Repository>> {

                override fun onResponse(
                    call: Call<Array<Repository>>,
                    response: Response<Array<Repository>>
                ) {
                    if (response.isSuccessful) {
                        repositoriesMutableLiveData.value =
                            Event(response.body()?.asList())
                    } else {
                        handleStatusCode(
                            context,
                            ErrorCode.fromCode(response.code()),
                            errorMessageMutableLiveData
                        )
                    }
                }

                override fun onFailure(call: Call<Array<Repository>>, t: Throwable) {
                    t.printStackTrace()
                    handleStatusCode(
                        context,
                        ErrorCode.ERROR_CODE_GENERAL,
                        errorMessageMutableLiveData
                    )
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
            handleStatusCode(context, ErrorCode.ERROR_CODE_GENERAL, errorMessageMutableLiveData)
        }
    }
}