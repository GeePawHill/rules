package org.geepawhill.rules.ui

import javafx.scene.Parent
import javafx.scene.control.TableView
import org.geepawhill.rules.domain.Rule
import org.geepawhill.rules.domain.Rulebase
import tornadofx.*

class ExcludedRuleView(private val base: Rulebase, private val book: RulebookModel, val rule: RuleModel) : View() {

    private lateinit var excluded: TableView<Rule>

    override val root: Parent =
            vbox {
                label("Excluded Rules")
                excluded = tableview(book.excludedProperty) {
                    multiSelect(true)
                    readonlyColumn("Name", Rule::name)
                    readonlyColumn("Description", Rule::description)
                    bindSelected(rule)
                }
            }

    val canInclude = excluded.isSelectedAndFocused()

    init {
//        book.itemProperty.addListener { _, _, after ->
//            excluded.items = base.excluded(after)
//        }
    }

    fun resetSelection(rules: List<Rule>) {
        excluded.selectionModel.clearSelection()
//        excluded.items = base.excluded(book.item)
        rules.forEach { excluded.selectionModel.select(it) }
        excluded.requestFocus()
    }

    fun grabSelection(): List<Rule> = excluded.grabSelection()
}



