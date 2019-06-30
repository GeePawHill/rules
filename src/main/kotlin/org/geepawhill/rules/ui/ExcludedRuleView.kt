package org.geepawhill.rules.ui

import javafx.scene.Parent
import org.geepawhill.rules.domain.Rule
import tornadofx.*

class ExcludedRuleView(book: RulebookModel, val rule: RuleModel) : RulesView, View() {

    private val baseView = RulesViewBase(book.excludedProperty, rule) {
        readonlyColumn("Name", Rule::name)
        readonlyColumn("Description", Rule::description)
    }

    override val root: Parent = baseView.root
    override val isSelectedAndFocused = baseView.isSelectedAndFocused
    override val canRaise = baseView.canRaise
    override val canLower = baseView.canLower
    override fun resetSelection(rules: List<Rule>) = baseView.resetSelection(rules)
    override fun grabSelection() = baseView.grabSelection()
}



