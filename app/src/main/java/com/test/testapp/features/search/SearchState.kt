package com.test.testapp.features.search

import com.test.domain.api.comments.Comment
import com.test.testapp.base.properties.ListBindableProperty
import com.test.testapp.base.vm.BaseVmState

class SearchState : BaseVmState() {
    val comments = ListBindableProperty<Comment>()
}