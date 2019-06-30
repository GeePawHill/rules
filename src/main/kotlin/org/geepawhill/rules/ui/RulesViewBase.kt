package org.geepawhill.rules.ui

import javafx.beans.property.Property
import javafx.collections.ObservableList
import javafx.scene.Parent
import javafx.scene.control.TableView
import org.geepawhill.rules.domain.Rule
import tornadofx.*

class RulesViewBase(rules: Property<ObservableList<Rule>>, val rule: RuleModel, columns: (TableView<Rule>) -> Unit) : View(), RulesView {

    private lateinit var table: TableView<Rule>

    override val root: Parent =
            vbox {
                label("Included Rules")
                table = tableview(rules) {
                    multiSelect(true)
                    columns(this)
                    bindSelected(rule)
                }
            }

    override val isSelectedAndFocused = table.isSelectedAndFocused()
    override fun resetSelection(rules: List<Rule>) = table.resetSelection(rules)
    override fun grabSelection() = table.grabSelection()

    override val canRaise = booleanBinding(
            table.focusedProperty(),
            table.selectionModel.selectedItemProperty())
    {
        table.focusedProperty().value && !table.selectionModel.selectedIndices.contains(0)
    }

    override val canLower = booleanBinding(
            table.focusedProperty(),
            table.selectionModel.selectedItemProperty())
    {
        table.focusedProperty().value && !table.selectionModel.selectedIndices.contains(table.items.size - 1)
    }


}


