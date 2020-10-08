package com.battagliandrea.galleryappandroid.di.module.framework

import android.content.Context
import com.battagliandrea.galleryappandroid.BuildConfig
import com.battagliandrea.galleryappandroid.data.networking.RedditApiContract
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
open class RetrofitModule {


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //          OKHTTP
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //          RETROFIT
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) =
            createRetrofit(gson, okHttpClient, BuildConfig.apiBaseUrl)


    @Provides
    @Singleton
    open fun provideApi(retrofit: Retrofit, context: Context): RedditApiContract = retrofit.create(
        RedditApiContract::class.java)


    private fun createRetrofit(gson: Gson,
                               okHttpClient: OkHttpClient,
                               endpoint: String): Retrofit {

        return Retrofit.Builder()
                .baseUrl(endpoint)
                .client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }
}
