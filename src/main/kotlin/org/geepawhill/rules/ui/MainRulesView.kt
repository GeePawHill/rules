package org.geepawhill.rules.ui

import javafx.geometry.Orientation
import javafx.scene.Parent
import org.geepawhill.rules.domain.Rule
import org.geepawhill.rules.domain.Rulebase
import org.geepawhill.rules.domain.Rulebook
import tornadofx.*

class MainRulesView() : View() {
    private val base = Rulebase()

    val book = RulebookModel()

    private val ruleSelectionView = RuleSelectionView(base, book)

    override val root: Parent =
            vbox {
                toolbar {
                    button("New Rule") {
                    }
                }
                splitpane(Orientation.VERTICAL) {
                    this += ruleSelectionView
                    this += RuleDetailView(ruleSelectionView.rule)
                }
            }

    init {
        val newBook = Rulebook("Name", "Description", mutableListOf<Rule>().observable())
        for (i in 1..10) {
            val rule = Rule("Rule $i", "Description $i")
            base.rules += rule
            if ((i % 2) == 0) newBook.rules.add(rule)
        }
        book.item = newBook
    }
}