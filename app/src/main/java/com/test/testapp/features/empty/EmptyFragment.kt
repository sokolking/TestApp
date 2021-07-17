package com.test.testapp.features.empty

import android.os.Bundle
import com.test.base.ext.clearInsetsListener
import com.test.testapp.R
import com.test.testapp.base.fragment.BaseFragment
import com.test.testapp.ext.initDefaultInsetsFragment
import kotlinx.android.synthetic.main.fragment_empty.*
import org.koin.android.viewmodel.ext.android.viewModel

class EmptyFragment : BaseFragment(R.layout.fragment_empty) {

    private val vm: EmptyVm by viewModel()

    override fun afterCreate(savedInstanceState: Bundle?, recreated: Boolean) {
        initInsets()
        initListeners()
        bindProperties()
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

        }
    }

    private fun initListeners() {

    }

    private fun initInsets() {
        initDefaultInsetsFragment(v_root)
    }
}