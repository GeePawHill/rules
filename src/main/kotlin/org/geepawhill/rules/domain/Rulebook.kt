package org.geepawhill.rules.domain

import javafx.collections.ObservableList
import org.geepawhill.rules.ui.addInvalidationListener
import tornadofx.*

class Rulebook(
        val name: String,
        val description: String,
        val included: ObservableList<Rule>,
        all: ObservableList<Rule>
) {
    val excluded = observableList<Rule>()

    init {
        all.addInvalidationListener { updateExcluded(all) }
        included.addInvalidationListener { updateExcluded(all) }
    }

    private fun updateExcluded(all: ObservableList<Rule>) {
        val newExcluded = all.filter { !included.contains(it) }
        excluded.clear()
        excluded.addAll(newExcluded)
    }
}


