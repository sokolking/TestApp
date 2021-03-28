package com.test.i_comment

import com.test.base.coroutines.RequestResult
import com.test.base.coroutines.mapError
import com.test.domain.api.comments.Comment
import com.test.i_comment.repository.CommentRepository

class CommentInteractor(private val repository: CommentRepository) {

    suspend fun getComments(page: Int): RequestResult<List<Comment>?> =
        repository.getComments(page)
            .mapError {
                GetCommentsException(it)
            }
}

data class GetCommentsException(override val cause: Throwable?) : Throwable()