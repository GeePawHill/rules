package org.geepawhill.rules.ui

import javafx.scene.Parent
import javafx.scene.control.TableView
import org.geepawhill.rules.domain.Rule
import tornadofx.*

class ExcludedRuleView(private val book: RulebookModel, val rule: RuleModel) : View() {

    private lateinit var table: TableView<Rule>

    override val root: Parent =
            vbox {
                label("Excluded Rules")
                table = tableview(book.excludedProperty) {
                    multiSelect(true)
                    readonlyColumn("Name", Rule::name)
                    readonlyColumn("Description", Rule::description)
                    bindSelected(rule)
                }
            }

    val isSelectedAndFocused = table.isSelectedAndFocused()

    fun resetSelection(rules: List<Rule>) = table.resetSelection(rules)
    fun grabSelection(): List<Rule> = table.grabSelection()
}



