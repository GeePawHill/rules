package org.geepawhill.rules.ui

import javafx.scene.Parent
import org.geepawhill.rules.domain.Rulebase
import tornadofx.*

class RuleSelectionView(base: Rulebase, val book: RulebookModel) : View() {

    val rule = RuleModel()

    private val includedRuleView = IncludedRuleView(base, book, rule)
    private val excludedRuleView = ExcludedRuleView(base, book, rule)

    override val root: Parent =
            hbox {
                this += includedRuleView
                vbox {
                    region { minHeight = 50.0 }
                    button("<") {
                        isFocusTraversable = false
                        action { include() }
                        enableWhen(excludedRuleView.canInclude)
                    }
                    button(">") {
                        isFocusTraversable = false
                        action { exclude() }
                        enableWhen { includedRuleView.canExclude }
                    }
                    region { minHeight = 50.0 }
                    button("^") {
                        isFocusTraversable = false
                        action { raise() }
                        enableWhen { includedRuleView.canRaise }
                    }
                    button("v") {
                        isFocusTraversable = false
                        action { lower() }
                        enableWhen { includedRuleView.canLower }
                    }
                }
                this += excludedRuleView
            }

    private fun exclude() {
        val toMove = includedRuleView.grabSelection()
        book.exclude(toMove)
        excludedRuleView.resetSelection(toMove)
    }

    private fun include() {
        val toMove = excludedRuleView.grabSelection()
        book.include(toMove)
        excludedRuleView.resetSelection(emptyList())
        includedRuleView.resetSelection(toMove)
    }

    private fun raise() {
        val toRaise = includedRuleView.grabSelection()
        book.raise(toRaise)
        includedRuleView.resetSelection(toRaise)
    }

    private fun lower() {
        val toLower = includedRuleView.grabSelection()
        book.lower(toLower)
        includedRuleView.resetSelection(toLower)
    }
}
