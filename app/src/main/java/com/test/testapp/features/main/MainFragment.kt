package com.test.testapp.features.main

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.base.ext.clearInsetsListener
import com.test.testapp.R
import com.test.testapp.base.fragment.BaseFragment
import com.test.testapp.base.properties.bind
import com.test.testapp.base.properties.bindLoader
import com.test.testapp.ext.initDefaultInsetsFragment
import com.test.testapp.features.main.adapter.ExplorerAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val vm: MainVm by viewModel()

    private var explorerAdapter: ExplorerAdapter? = null

    override fun afterCreate(savedInstanceState: Bundle?, recreated: Boolean) {
        initInsets()
        initListeners()
        bindProperties()
        rv_data.initCommentsAdapter()
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
            bind(explorers) {
                explorerAdapter?.showExplorers(it)
            }
        }
    }

    private fun RecyclerView.initCommentsAdapter() {
        explorerAdapter = ExplorerAdapter()
        val manager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        layoutManager = manager
        adapter = explorerAdapter
    }

    private fun initListeners() {

    }

    private fun initInsets() {
        initDefaultInsetsFragment(v_root)
    }
}