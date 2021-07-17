package com.test.testapp.enum

import com.test.common.ExplorerTabItem
import com.test.testapp.R

enum class ExplorerTabs(val explorerTabItem: ExplorerTabItem) {
    FIND_JOBS(
        ExplorerTabItem(
            R.drawable.ic_find_jobs,
            R.drawable.ic_find_jobs_selected,
            R.string.find_jobs_title,
            false
        )
    ),
    HELP_OTHERS(
        ExplorerTabItem(
            R.drawable.ic_help_others,
            R.drawable.ic_help_others_selected,
            R.string.help_others_title,
            true
        )
    ),
    FIND_CANDIDATES(
        ExplorerTabItem(
            R.drawable.ic_find_candidates,
            R.drawable.ic_find_candidates_selected,
            R.string.find_candidates_title,
            false
        )
    )
}