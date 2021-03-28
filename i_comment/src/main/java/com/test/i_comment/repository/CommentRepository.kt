package com.test.i_comment.repository

import com.test.api.JsonPlaceHolderldApi
import com.test.base.coroutines.RequestResult
import com.test.domain.api.comments.Comment
import com.test.i_comment.GetCommentsException

class CommentRepository(private val api: JsonPlaceHolderldApi) {

    fun getComments(page: Int): RequestResult<List<Comment>?> {
        val response = api.getComments(page).execute()
        val body = response.body()
        val success = response.isSuccessful
        return if (success) {
            RequestResult.Success(body)
        } else {
            RequestResult.Error(GetCommentsException(Throwable(response.errorBody()?.string())))
        }
    }
}