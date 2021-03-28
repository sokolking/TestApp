package com.test.api

import com.test.api.jsonplaceholder.ApiJsonPlaceHolder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

interface JsonPlaceHolderldApi : ApiJsonPlaceHolder {
    companion object {
        fun create(baseUrl: String, client: OkHttpClient = OkHttpClient()): JsonPlaceHolderldApi {
            val apiFilters: ApiJsonPlaceHolder = Retrofit
                .Builder()
                .client(client)
                .baseUrl(baseUrl.trimEnd('/').plus('/'))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(ApiGsonHolder.gson))
                .build()
                .create()

            return object : JsonPlaceHolderldApi, ApiJsonPlaceHolder by apiFilters {}
        }
    }
}