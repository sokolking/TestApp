package com.test.testapp.base.properties

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.load
import coil.request.ImageRequest
import com.test.testapp.base.vm.LoadingState
import com.test.testapp.base.vm.PullToRefreshState
import com.test.testapp.view.LoadingView
import java.io.File

//region fragment
fun <T> Fragment.bind(property: ObjectBindableProperty<T>, listener: (newValue: T) -> Unit) {
    viewLifecycleOwner
        .lifecycleScope
        .launchWhenStarted {
            property.subscribe {
                listener(it)
            }
        }
}

fun Fragment.bindPullToRefresh(
    property: ObjectBindableProperty<PullToRefreshState>,
    view: SwipeRefreshLayout
) {
    bind(property) {
        view.isEnabled = it.enabled
        view.isRefreshing = it.refreshing
    }
}

fun Fragment.bindEnabled(property: BooleanBindableProperty, view: View) {
    bind(property) {
        view.isEnabled = it
    }
}

fun Fragment.bindLoader(property: ObjectBindableProperty<LoadingState>, view: LoadingView) {
    bind(property) {
        view.isVisible = it !is LoadingState.None
    }
}

fun Fragment.bindText(property: StringBindableProperty, view: TextView) {
    bind(property) {
        view.text = it
    }
}

fun Fragment.bindVisible(property: BooleanBindableProperty, view: View) {
    bind(property) {
        view.isVisible = it
    }
}

fun Fragment.bindImage(
    property: ObjectBindableProperty<File?>,
    view: ImageView,
    builder: ImageRequest.Builder.(File?) -> Unit = {}
) {
    bind(property) {
        val builderWrapper: ImageRequest.Builder.() -> Unit = {
            builder(it)
        }
        view.load(file = it, builder = builderWrapper)
    }
}
//endregion