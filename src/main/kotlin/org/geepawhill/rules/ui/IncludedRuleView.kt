package org.geepawhill.rules.ui

import javafx.beans.property.SimpleStringProperty
import javafx.scene.Parent
import org.geepawhill.rules.domain.Rule
import tornadofx.*

class IncludedRuleView(private val book: RulebookModel, val rule: RuleModel) : View(), RulesView {

    private val baseView = RulesViewBase(book.includedProperty, rule) {
        column<Rule, String>("Sequence") {
            SimpleStringProperty(book.includedProperty.value.indexOf(it.value).toString())
        }
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


