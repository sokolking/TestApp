package com.test.testapp.features.other

import com.test.i_comment.repository.CommentRepository
import com.test.testapp.base.vm.BaseVm
import kotlinx.coroutines.launch

class OtherVm(private val commentRepository: CommentRepository) : BaseVm<OtherState>() {

    override val state: OtherState = OtherState()

    fun afterCreate() {
        launch {
            commentRepository.getComments(1)
        }
    }

}