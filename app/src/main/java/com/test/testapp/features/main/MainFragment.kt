package com.test.testapp.features.main

import android.os.Bundle
import com.test.base.ext.clearInsetsListener
import com.test.testapp.R
import com.test.testapp.base.fragment.BaseFragment
import com.test.testapp.base.properties.bind
import com.test.testapp.base.properties.bindLoader
import com.test.testapp.ext.afterTextChanged
import com.test.testapp.ext.hideKeyboard
import com.test.testapp.ext.initDefaultInsetsFragment
import com.test.testapp.ext.showTextInputError
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val vm: MainVm by viewModel()

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
            bindLoader(loading, v_loading)

        }
    }

    private fun initListeners() {

    }

    private fun initInsets() {
        initDefaultInsetsFragment(v_root)
    }
}