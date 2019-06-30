package org.geepawhill.rules.ui

import javafx.beans.property.SimpleStringProperty
import javafx.scene.Parent
import javafx.scene.control.TableView
import org.geepawhill.rules.domain.Rule
import tornadofx.*

class IncludedRuleView(private val book: RulebookModel, val rule: RuleModel) : View() {

    private lateinit var table: TableView<Rule>

    override val root: Parent =
            vbox {
                label("Included Rules")
                table = tableview(book.includedProperty) {
                    multiSelect(true)
                    column<Rule, String>("Sequence") {
                        SimpleStringProperty(book.includedProperty.value.indexOf(it.value).toString())
                    }
                    readonlyColumn("Name", Rule::name)
                    readonlyColumn("Description", Rule::description)
                    bindSelected(rule)
                }
            }

    val isSelectedAndFocused = table.isSelectedAndFocused()

    val canRaise = booleanBinding(
            table.focusedProperty(),
            table.selectionModel.selectedItemProperty())
    {
        table.focusedProperty().value && !table.selectionModel.selectedIndices.contains(0)
    }

    val canLower = booleanBinding(
            table.focusedProperty(),
            table.selectionModel.selectedItemProperty())
    {
        table.focusedProperty().value && !table.selectionModel.selectedIndices.contains(table.items.size - 1)
    }

    fun resetSelection(rules: List<Rule>) = table.resetSelection(rules)
    fun grabSelection() = table.grabSelection()

}


