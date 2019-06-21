package org.geepawhill.rules.ui

import javafx.scene.Parent
import javafx.scene.control.TableView
import org.geepawhill.rules.domain.Rule
import org.geepawhill.rules.domain.Rulebase
import tornadofx.*

class RuleSelectionView(val base: Rulebase, val book: RulebookModel) : View() {

    val rule = RuleModel()

    private lateinit var included: TableView<Rule>
    private lateinit var excluded: TableView<Rule>

    override val root: Parent =
            hbox {
                included = tableview {
                    multiSelect(true)
                    readonlyColumn("Name", Rule::name)
                    readonlyColumn("Description", Rule::description)
                    bindSelected(rule)
                }
                vbox {
                    button("<") {
                        action { includeRule() }
                    }
                    button(">") {
                        action { excludeRule() }
                    }
                }
                excluded = tableview {
                    multiSelect(true)
                    readonlyColumn("Name", Rule::name)
                    readonlyColumn("Description", Rule::description)
                    bindSelected(rule)
                }
            }


    init {
        book.itemProperty.addListener { _, _, after ->
            included.items = after.rules
            excluded.items = base.excluded(after)
        }
    }

    private fun excludeRule() {
        shiftBetweenLists(included, excluded) { rule -> book.item.rules -= rule }
    }

    private fun includeRule() {
        shiftBetweenLists(excluded, included) { rule -> book.item.rules += rule }
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
