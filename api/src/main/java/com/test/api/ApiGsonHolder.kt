package com.test.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object ApiGsonHolder {

    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()
}