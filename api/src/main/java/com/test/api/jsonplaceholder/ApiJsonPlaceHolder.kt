package com.test.api.jsonplaceholder

import com.test.domain.api.comments.Comment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiJsonPlaceHolder {

    @GET("/posts/{page}/comments")
    fun getComments(@Path("page") page: Int): Call<List<Comment>?>

}