package org.geepawhill.rules.ui

import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleStringProperty
import javafx.scene.Parent
import javafx.scene.control.TableView
import org.geepawhill.rules.domain.Rule
import org.geepawhill.rules.domain.Rulebase
import tornadofx.*

class IncludedRuleView(val base: Rulebase, val book: RulebookModel, val rule: RuleModel) : View() {

    lateinit var included: TableView<Rule>


    override val root: Parent =
            vbox {
                label("Included Rules")
                included = tableview(book.rulesProperty) {
                    multiSelect(true)
                    column<Rule, String>("Sequence") {
                        SimpleStringProperty(book.rulesProperty.value.indexOf(it.value).toString())
                    }
                    readonlyColumn("Name", Rule::name)
                    readonlyColumn("Description", Rule::description)
                    bindSelected(rule)
                }
            }

    val canExclude = Bindings.and(
            included.focusedProperty(),
            Bindings.notEqual(included.selectionModel.selectedItems.sizeProperty, 0)
    )

    val canRaise = booleanBinding(
            included.focusedProperty(),
            included.selectionModel.selectedItemProperty())
    {
        included.focusedProperty().value && !included.selectionModel.selectedIndices.contains(0)
    }

    val canLower = booleanBinding(
            included.focusedProperty(),
            included.selectionModel.selectedItemProperty())
    {
        included.focusedProperty().value && !included.selectionModel.selectedIndices.contains(included.items.size - 1)
    }

    fun grabSelection(): List<Rule> {
        val result = included.selectionModel.selectedItems.toList()
        included.selectionModel.clearSelection()
        return result
    }

    fun resetSelection(rules: List<Rule>) {
        included.selectionModel.clearSelection()
        rules.forEach { included.selectionModel.select(it) }
        included.requestFocus()
    }

}
