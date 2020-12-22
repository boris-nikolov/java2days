package com.bnikolov.java2daysdemo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnikolov.java2daysdemo.enum.ErrorCode
import com.bnikolov.java2daysdemo.network.manager.RequestManager
import com.bnikolov.java2daysdemo.network.model.PullRequest
import com.bnikolov.java2daysdemo.util.Event
import dagger.hilt.android.qualifiers.ActivityContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PullRequestsRepository @Inject constructor(
    @ActivityContext private val context: Context,
    private val requestManager: RequestManager
) : BaseRepository() {

    private val pullRequestsMutableLiveData = MutableLiveData<Event<List<PullRequest?>?>>()

    val pullRequestsLiveData: LiveData<Event<List<PullRequest?>?>> = pullRequestsMutableLiveData

    fun getPullRequests(repoName: String?) {
        if (repoName.isNullOrEmpty() || !checkConnected(context)) return

        try {
            val request = requestManager.getPullRequests(repoName)
            request.enqueue(object : Callback<Array<PullRequest>> {

                override fun onResponse(
                    call: Call<Array<PullRequest>>,
                    response: Response<Array<PullRequest>>
                ) {
                    if (response.isSuccessful) {
                        pullRequestsMutableLiveData.value =
                            Event(response.body()?.asList())
                    } else {
                        handleStatusCode(
                            context,
                            ErrorCode.fromCode(response.code())
                        )
                    }
                }

                override fun onFailure(call: Call<Array<PullRequest>>, t: Throwable) {
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