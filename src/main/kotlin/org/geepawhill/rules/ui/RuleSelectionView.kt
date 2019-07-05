package org.geepawhill.rules.ui

import javafx.beans.binding.BooleanBinding
import javafx.event.EventTarget
import javafx.scene.Parent
import org.geepawhill.rules.domain.Rule
import tornadofx.*

class RuleSelectionView(private val book: RulebookModel) : View() {

    val rule = RuleModel()

    private val includedRuleView = IncludedRuleView(book, rule)
    private val excludedRuleView = ExcludedRuleView(book, rule)

    override val root: Parent =
            hbox {
                this += includedRuleView
                vbox {
                    region { minHeight = 50.0 }
                    toolbutton("<", excludedRuleView.isSelectedAndFocused) {
                        moveRule(excludedRuleView, includedRuleView) { include(it) }
                    }
                    toolbutton(">", includedRuleView.isSelectedAndFocused) {
                        moveRule(includedRuleView, excludedRuleView) { exclude(it) }
                    }
                    region { minHeight = 50.0 }
                    toolbutton("^", includedRuleView.canRaise) {
                        moveRule(includedRuleView, includedRuleView) { raise(it) }
                    }
                    toolbutton("v", includedRuleView.canLower) {
                        moveRule(includedRuleView, includedRuleView) { lower(it) }
                    }
                }
                this += excludedRuleView
            }

    private fun EventTarget.toolbutton(text: String, enable: BooleanBinding, action: () -> Unit) {
        button(text) {
            isFocusTraversable = false
            action {
                action()
            }
            enableWhen(enable)
        }
    }

    private fun moveRule(fromView: RulesView, toView: RulesView, move: RulebookModel.(List<Rule>) -> Unit) {
        val toChange = fromView.grabSelection()
        book.move(toChange)
        toView.resetSelection(toChange)
    }
}
