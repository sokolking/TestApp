package com.test.testapp.features.main

import com.test.i_comment.validator.FieldValidator
import com.test.testapp.R
import com.test.testapp.base.vm.BaseVm
import com.test.testapp.base.vm.LoadingState
import com.test.testapp.features.search.SearchRoute

class MainVm : BaseVm<MainState>() {

    override val state: MainState = MainState()

    private var lowerBound: String = ""
    private var upperBound: String = ""

    fun changeLowerBound(newLowerBound: String) {
        lowerBound = newLowerBound
    }

    fun changeUpperBound(newUpperBound: String) {
        upperBound = newUpperBound
    }

    fun onSearchButtonClicked() {
        state.showLowerError.clear()
        state.showLowerError.clear()
        state.loading.post(LoadingState.Loading)
        if (!FieldValidator.validate(lowerBound)) {
            state.showLowerError.post(Pair(true, R.string.main_bound_error))
            state.loading.post(LoadingState.None)
            return
        }
        if (!FieldValidator.validate(upperBound)) {
            state.showUpperError.post(Pair(true, R.string.main_bound_error))
            state.loading.post(LoadingState.None)
            return
        }
        if (!FieldValidator.validateLower(lowerBound, upperBound)) {
            state.showLowerError.post(Pair(true, R.string.main_lower_bound_error))
            state.loading.post(LoadingState.None)
            return
        }
        if (!FieldValidator.validateUpper(upperBound, lowerBound)) {
            state.showUpperError.post(Pair(true, R.string.main_upper_bound_error))
            state.loading.post(LoadingState.None)
            return
        }
        state.loading.post(LoadingState.None)
        router.addScreen(SearchRoute(lowerBound.toInt(), upperBound.toInt()).withDefaultAnim())
    }

}