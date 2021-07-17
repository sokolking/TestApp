package com.test.testapp.features.search

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.base.ext.clearInsetsListener
import com.test.testapp.R
import com.test.testapp.base.fragment.BaseFragment
import com.test.testapp.base.properties.bind
import com.test.testapp.base.properties.bindLoader
import com.test.testapp.ext.initDefaultInsetsFragment
import com.test.testapp.features.search.adapter.CommentAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchFragment : BaseFragment(R.layout.fragment_search) {

    private var commentAdapter: CommentAdapter? = null

    private var isLoading = false

    private val vm: SearchVm by viewModel {
        parametersOf(
            SearchRoute.fetchArgLowerBound(requireArguments()),
            SearchRoute.fetchArgUpperBound(requireArguments())
        )
    }

    override fun afterCreate(savedInstanceState: Bundle?, recreated: Boolean) {
        initInsets()
        initListeners()
        bindProperties()
        rv_comments.initCommentsAdapter()
        vm.afterCreate()
    }

    override fun onDestroyView() {
        v_root.clearInsetsListener()
        super.onDestroyView()
    }

    override fun onShow() {
        initInsets()
    }

    override fun onHide() {
        v_root.clearInsetsListener()
    }

    private fun bindProperties() {
        with(vm.state) {
            bindLoader(loading, v_loading)
            bind(comments) {
                commentAdapter?.showComments(it)
                isLoading = false
            }
        }
    }

    private fun RecyclerView.initCommentsAdapter() {
        commentAdapter = CommentAdapter()
        val manager = LinearLayoutManager(context)
        layoutManager = manager
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = manager.childCount
                    val totalItemCount = manager.itemCount
                    val pastVisiblesItems = manager.findFirstVisibleItemPosition()

                    if (!isLoading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            isLoading = true
                            vm.getData()
                        }
                    }
                }
            }
        }
        addOnScrollListener(scrollListener)
        adapter = commentAdapter
    }

    private fun initListeners() {

    }

    private fun initInsets() {
        initDefaultInsetsFragment(v_root)
    }
}