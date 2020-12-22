package com.bnikolov.java2daysdemo.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.bnikolov.java2daysdemo.data.local.LocalRepositoryDataSource
import com.bnikolov.java2daysdemo.db.model.RepositoryRealm
import com.bnikolov.java2daysdemo.enum.ErrorCode
import com.bnikolov.java2daysdemo.network.manager.RequestManager
import com.bnikolov.java2daysdemo.network.model.Repository
import dagger.hilt.android.qualifiers.ActivityContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GithubRepoRepository @Inject constructor(
    @ActivityContext private val context: Context,
    private val requestManager: RequestManager,
    private val localRepositoryDataSource: LocalRepositoryDataSource
) : BaseRepository() {

    val repositoriesLiveData: LiveData<List<RepositoryRealm>> =
        localRepositoryDataSource.getRepositories()

    fun getRepositories() {
        try {
            if (!checkConnected(context)) return

            Log.e("MY_TEST_LOG", "Getting repositories from remote source")
            val request = requestManager.getRepositories()
            request.enqueue(object : Callback<Array<Repository>> {
                override fun onResponse(
                    call: Call<Array<Repository>>,
                    response: Response<Array<Repository>>
                ) {
                    if (response.isSuccessful) {
                        localRepositoryDataSource.saveRepositories(
                            response.body()?.asList()
                        )
                    } else {
                        handleStatusCode(
                            context,
                            ErrorCode.fromCode(response.code())
                        )
                    }
                }

                override fun onFailure(call: Call<Array<Repository>>, t: Throwable) {
                    t.printStackTrace()
                    handleStatusCode(
                        context,
                        ErrorCode.ERROR_CODE_GENERAL
                    )
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            handleStatusCode(context, ErrorCode.ERROR_CODE_GENERAL)
        }
    }
}