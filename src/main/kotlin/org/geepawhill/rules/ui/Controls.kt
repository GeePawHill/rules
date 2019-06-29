package org.geepawhill.rules.ui

import javafx.scene.control.TableView
import org.geepawhill.rules.domain.Rule
import tornadofx.*

fun TableView<Rule>.grabSelection(): List<Rule> {
    val result = selectionModel.selectedItems.toList()
    selectionModel.clearSelection()
    return result
}

fun <T> TableView<T>.isSelectedAndFocused() = booleanBinding(
        focusedProperty(),
        selectionModel.selectedItemProperty())
{
    focusedProperty().value && selectionModel.selectedIndices.isNotEmpty()
}