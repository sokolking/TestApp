package com.test.testapp.features.other

import android.os.Bundle
import com.test.base.ext.clearInsetsListener
import com.test.testapp.R
import com.test.testapp.base.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_other.*
import org.koin.android.viewmodel.ext.android.viewModel

class OtherFragment : BaseFragment(R.layout.fragment_other) {

    private val vm: OtherVm by viewModel()

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

    }
}