package com.test.testapp.features.search

import android.os.Bundle
import com.test.navigation.route.FragmentRoute

class SearchRoute(lowerBound: Int, upperBound: Int) : FragmentRoute({
    SearchFragment().apply {
        arguments = Bundle().apply {
            putInt(ARG_LOWER, lowerBound)
            putInt(ARG_UPPER, upperBound)
        }
    }
}
) {
    companion object {
        private const val ARG_LOWER = "arg_lower"
        private const val ARG_UPPER = "arg_upper"
        fun fetchArgLowerBound(args: Bundle): Int = args.getInt(ARG_LOWER, 0)
        fun fetchArgUpperBound(args: Bundle): Int = args.getInt(ARG_UPPER, 0)
    }
}