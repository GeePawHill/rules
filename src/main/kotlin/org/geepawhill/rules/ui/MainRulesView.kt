package org.geepawhill.rules.ui

import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Orientation
import javafx.scene.Parent
import org.geepawhill.rules.domain.Rule
import org.geepawhill.rules.domain.Rulebase
import org.geepawhill.rules.domain.Rulebook
import tornadofx.*

class MainRulesView() : View() {
    private val book = Rulebook("Name", "Description", mutableListOf<Rule>().observable())
    private val bookProperty = SimpleObjectProperty(Rulebook("Name", "Description", mutableListOf<Rule>().observable()))
    private val rulebase = Rulebase()

    private val ruleSelectionView = RuleSelectionView(rulebase, bookProperty)

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
        for (i in 1..10) {
            val rule = Rule("Rule $i", "Description $i")
            rulebase.rules += rule
            if ((i % 2) == 0) book.rules.add(rule)
        }
        bookProperty.value = book
    }
}