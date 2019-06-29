package org.geepawhill.rules.ui

import javafx.beans.binding.Bindings
import javafx.scene.Parent
import javafx.scene.control.TableView
import org.geepawhill.rules.domain.Rule
import org.geepawhill.rules.domain.Rulebase
import tornadofx.*

class ExcludedRuleView(val base: Rulebase, val book: RulebookModel, val rule: RuleModel) : View() {

    lateinit var excluded: TableView<Rule>


    override val root: Parent =
            vbox {
                label("Excluded Rules")
                excluded = tableview {
                    multiSelect(true)
                    readonlyColumn("Name", Rule::name)
                    readonlyColumn("Description", Rule::description)
                    bindSelected(rule)
                }
            }

    val canInclude = Bindings.and(
            excluded.focusedProperty(),
            Bindings.notEqual(excluded.selectionModel.selectedItems.sizeProperty, 0)
    )

    init {
        book.itemProperty.addListener { _, _, after ->
            excluded.items = base.excluded(after)
        }
    }

    fun resetSelection(rules: List<Rule>) {
        excluded.selectionModel.clearSelection()
        excluded.items = base.excluded(book.item)
        rules.forEach { excluded.selectionModel.select(it) }
        excluded.requestFocus()
    }

    fun grabSelection(): List<Rule> {
        val result = excluded.selectionModel.selectedItems.toList()
        excluded.selectionModel.clearSelection()
        return result
    }

}
