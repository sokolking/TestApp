package com.test.testapp.features.main

import com.test.domain.api.comments.ExplorerData
import com.test.testapp.R
import com.test.testapp.base.vm.BaseVm
import com.test.testapp.base.vm.LoadingState
import java.util.*

class MainVm : BaseVm<MainState>() {

    override val state: MainState = MainState()

    private val explorerData = mutableListOf<ExplorerData>()

    fun afterCreate() {
        getData(10)
    }

    private fun getData(number: Int) {
        state.loading.post(LoadingState.Loading)
        state.loading.post(LoadingState.None)
        for (i in 0..number) {
            explorerData.add(
                ExplorerData(
                    R.drawable.girl,
                    "Gabriela is interested in",
                    "Flutter Developer...",
                    "London",
                    "Remotely",
                    "€ 400 /day",
                    "100%"
                )
            )
        }
        state.explorers.clear()
        state.explorers.post(explorerData)
    }

    fun onItemSelected() {
        getData((10..20).random())
    }

}