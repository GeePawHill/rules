package org.geepawhill.rules.ui

import javafx.geometry.Orientation
import javafx.scene.Parent
import org.geepawhill.rules.domain.Rule
import org.geepawhill.rules.domain.Rulebase
import org.geepawhill.rules.domain.Rulebook
import tornadofx.*

class MainRulesView() : View() {
    private val base = Rulebase()
    val newBook = Rulebook("Name", "Description", mutableListOf<Rule>().observable())

    val book = RulebookModel()

    private val ruleSelectionView = RuleSelectionView(base, book)

    override val root: Parent =
            vbox {
                toolbar {
                    button("Show domain") {
                        action {
                            println("Model")
                            book.rulesProperty.value.forEach {
                                println("\t${it.name}")
                            }
                            println("Domain")
                            newBook.rules.forEach {
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
            base.rules += rule
            if ((i % 2) == 0) newBook.rules.add(rule)
        }
        book.item = newBook
    }
}