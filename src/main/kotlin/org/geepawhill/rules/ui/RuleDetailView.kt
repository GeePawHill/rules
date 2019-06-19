package org.geepawhill.rules.ui

import tornadofx.*

class RuleDetailView(private val rule: RuleModel) : View() {
    override val root = vbox {
        label(rule.nameProperty)
        label(rule.descriptionProperty)
    }
}
