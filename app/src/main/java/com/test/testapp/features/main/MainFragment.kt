package com.test.testapp.features.main

import android.os.Bundle
import com.test.base.ext.clearInsetsListener
import com.test.testapp.R
import com.test.testapp.base.fragment.BaseFragment
import com.test.testapp.base.properties.bind
import com.test.testapp.base.properties.bindLoader
import com.test.testapp.ext.afterTextChanged
import com.test.testapp.ext.hideKeyboard
import com.test.testapp.ext.initDefaultInsetesFragment
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
            bind(showLowerError) {
                til_lower_bound.showTextInputError(it.first, it.second)
            }
            bind(showUpperError) {
                til_upper_bound.showTextInputError(it.first, it.second)
            }
        }
    }

    private fun initListeners() {
        et_lower_bound.afterTextChanged(til_lower_bound) {
            vm.changeLowerBound(it?.toString() ?: "")
        }
        et_upper_bound.afterTextChanged(til_upper_bound) {
            vm.changeUpperBound(it?.toString() ?: "")
        }
        btn_search.setOnClickListener {
            hideKeyboard()
            vm.onSearchButtonClicked()
        }
    }

    private fun initInsets() {
        initDefaultInsetesFragment(v_root)
    }
}