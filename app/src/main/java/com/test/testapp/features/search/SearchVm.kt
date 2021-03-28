package com.test.testapp.features.search

import com.test.domain.api.comments.Comment
import com.test.i_comment.repository.CommentRepository
import com.test.testapp.base.vm.BaseVm
import com.test.testapp.base.vm.LoadingState
import kotlinx.coroutines.launch

class SearchVm(
    private val lowerBound: Int,
    private val upperBound: Int,
    private val commentRepository: CommentRepository
) : BaseVm<SearchState>() {

    override val state: SearchState = SearchState()

    private var page = 0
    private val comments = mutableListOf<Comment>()

    init {
        page = lowerBound / 5
    }

    fun afterCreate() {
        if (page == 0) {
            page = 1
        }
        getData()
    }

    fun getData() {
        if (page * 5 <= (upperBound / 5 + 1) * 5) {
            state.loading.post(LoadingState.Loading)
            launch {
                commentRepository.getComments(page).handleResult {
                    state.loading.post(LoadingState.None)
                    page += 1
                    comments.addAll(it?.filter { it.id ?: 0 in lowerBound..upperBound }
                        ?: listOf())
                    state.comments.clear()
                    state.comments.post(comments)
                    if (comments.size < 6 && upperBound - lowerBound >= 6) {
                        getData()
                    }
                }
            }
        }
    }

}