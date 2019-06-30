package org.geepawhill.rules.ui

import javafx.beans.InvalidationListener
import javafx.collections.ObservableList
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

fun <T> ObservableList<T>.addInvalidationListener(op: () -> Unit) {
    addListener(InvalidationListener { op() })
}
