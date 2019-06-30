package org.geepawhill.rules.ui

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
                included = tableview(book.includedProperty) {
                    multiSelect(true)
                    column<Rule, String>("Sequence") {
                        SimpleStringProperty(book.includedProperty.value.indexOf(it.value).toString())
                    }
                    readonlyColumn("Name", Rule::name)
                    readonlyColumn("Description", Rule::description)
                    bindSelected(rule)
                }
            }

    val canExclude = included.isSelectedAndFocused()

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

    fun resetSelection(rules: List<Rule>) {
        included.selectionModel.clearSelection()
        rules.forEach { included.selectionModel.select(it) }
        included.requestFocus()
    }

    fun grabSelection() = included.grabSelection()

}


