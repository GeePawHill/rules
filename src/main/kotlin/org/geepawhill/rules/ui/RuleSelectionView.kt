package org.geepawhill.rules.ui

import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.Parent
import javafx.scene.control.TableView
import org.geepawhill.rules.domain.Rule
import org.geepawhill.rules.domain.Rulebase
import tornadofx.*

class RuleSelectionView(val base: Rulebase, val book: RulebookModel) : View() {

    val rule = RuleModel()

    private lateinit var included: TableView<Rule>
    private lateinit var excluded: TableView<Rule>

    private val canLeft = SimpleBooleanProperty(false)
    private val canRight = SimpleBooleanProperty(false)
    private val canUp = SimpleBooleanProperty(false)
    private val canDown = SimpleBooleanProperty(false)
    private val zerothItemSelected = SimpleBooleanProperty(false)
    private val lastItemSelected = SimpleBooleanProperty(false)

    override val root: Parent =
            hbox {
                vbox {
                    label("Included Rules")
                    included = tableview {
                        multiSelect(true)
                        column<Rule, String>("Sequence") {
                            SimpleStringProperty(book.rulesProperty.value.indexOf(it.value).toString())
                        }
                        readonlyColumn("Name", Rule::name)
                        readonlyColumn("Description", Rule::description)
                        bindSelected(rule)
                    }
                }
                vbox {
                    region { minHeight = 50.0 }
                    button("<") {
                        isFocusTraversable = false
                        action { includeRule() }
                        enableWhen(canLeft)
                    }
                    button(">") {
                        isFocusTraversable = false
                        action { excludeRule() }
                        enableWhen { canRight }
                    }
                    region { minHeight = 50.0 }
                    button("^") {
                        isFocusTraversable = false
                        action { raisePriority() }
                        enableWhen { canUp }
                    }
                    button("v") {
                        isFocusTraversable = false
                        action { lowerPriority() }
                        enableWhen { canDown }
                    }
                }
                vbox {
                    label("Excluded Rules")
                    excluded = tableview {
                        multiSelect(true)
                        readonlyColumn("Name", Rule::name)
                        readonlyColumn("Description", Rule::description)
                        bindSelected(rule)
                    }
                }
            }


    init {
        book.itemProperty.addListener { _, _, after ->
            included.items = after.rules
            excluded.items = base.excluded(after)
        }

        canLeft.bind(
                Bindings.and(
                        excluded.focusedProperty(),
                        Bindings.notEqual(excluded.selectionModel.selectedItems.sizeProperty, 0)
                )
        )

        canRight.bind(
                Bindings.and(
                        included.focusedProperty(),
                        Bindings.notEqual(included.selectionModel.selectedItems.sizeProperty, 0)
                )
        )

        canUp.bind(
                Bindings.and(
                        included.focusedProperty(),
                        zerothItemSelected
                )
        )

        canDown.bind(
                Bindings.and(
                        included.focusedProperty(),
                        lastItemSelected
                )
        )

        included.selectionModel.selectedItemProperty().addListener { _, _, _ ->
            zerothItemSelected.value = !included.selectionModel.selectedIndices.contains(0)
            lastItemSelected.value = !included.selectionModel.selectedIndices.contains(included.items.size - 1)
        }
    }

    private fun excludeRule() {
        shiftBetweenLists(included, excluded) { rule -> book.item.rules -= rule }
    }

    private fun includeRule() {
        shiftBetweenLists(excluded, included) { rule -> book.item.rules += rule }
    }

    private fun raisePriority() = changePriority { raisePriority(it) }
    private fun lowerPriority() = changePriority { lowerPriority(it) }

    private fun changePriority(change: RulebookModel.(tochange: List<Rule>) -> Unit) {
        val toChange = included.selectionModel.selectedItems.toList()
        book.change(toChange)
        included.selectionModel.clearSelection()
        toChange.forEach { included.selectionModel.select(it) }
    }

    private fun shiftBetweenLists(fromList: TableView<Rule>, toList: TableView<Rule>, operation: (rule: Rule) -> Unit) {
        val toMove = fromList.selectionModel.selectedItems.toList()
        toList.selectionModel.clearSelection()
        toMove.forEach {
            operation(it)
            excluded.items = base.excluded(book.item)
            toList.selectionModel.select(it)
        }
        toList.requestFocus()
    }

}
