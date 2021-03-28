package com.test.testapp.base.fragment

import androidx.fragment.app.BackstackAccessor
import androidx.fragment.app.Fragment

object FragmentDestroyChecker {

    fun isDestroying(component: Fragment): Boolean = check(component)

    private fun check(fragment: Fragment): Boolean {
        val activity = fragment.activity
        if (activity != null) {
            if (activity.isChangingConfigurations) {
                return false
            }
            if (activity.isFinishing) {
                return true
            }
        }

        if (BackstackAccessor.isFragmentOnBackStack(fragment)) {
            return false
        }

        var anyParentIsRemoving = false

        var parent = fragment.parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }

        return fragment.isRemoving || anyParentIsRemoving
    }
}