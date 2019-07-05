package org.geepawhill.rules.ui

import javafx.geometry.Orientation
import javafx.scene.Parent
import org.geepawhill.rules.domain.Rule
import org.geepawhill.rules.domain.Rulebase
import org.geepawhill.rules.domain.Rulebook
import tornadofx.*

class MainRulesView() : View() {
    private val base = Rulebase()
    val newBook = Rulebook("Name", "Description", mutableListOf<Rule>().observable(), base.rules)

    val book = RulebookModel()

    private val ruleSelectionView = RuleSelectionView(book)

    override val root: Parent =
            vbox {
                toolbar {
                    button("Show domain") {
                        action {
                            println("Model")
                            book.includedProperty.value.forEach {
                                println("\t${it.name}")
                            }
                            println("Domain")
                            newBook.included.forEach {
                                println("\t${it.name}")
                            }
                        }
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
            if ((i % 2) == 0) newBook.included.add(rule)
            base.rules += rule
        }
        book.item = newBook
    }
}