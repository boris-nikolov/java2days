package com.bnikolov.java2daysdemo.network

import android.content.Context
import com.bnikolov.java2daysdemo.BuildConfig
import com.bnikolov.java2daysdemo.network.service.RepositoryService
import com.bnikolov.java2daysdemo.network.util.signWithToken
import com.bnikolov.java2daysdemo.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRepositoryService(
        @ApplicationContext ctx: Context
    ): RepositoryService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
            .create(RepositoryService::class.java)
    }

    private fun getClient(): OkHttpClient {
        val client = OkHttpClient.Builder()

        val tokenHeaderInterceptor = getTokenHeaderInterceptor()
        client.addInterceptor(tokenHeaderInterceptor)

        return client.build()
    }

    private fun getTokenHeaderInterceptor(): Interceptor =
        object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val token = Constants.GITHUB_TOKEN
                if (token.isEmpty()) return chain.proceed(chain.request())

                val signedRequest = chain.request().signWithToken(token)

                return chain.proceed(signedRequest)
            }
        }
}