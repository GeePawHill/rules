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
        val toExclude = included.selectionModel.selectedItems.toList()
        excluded.selectionModel.clearSelection()
        toExclude.forEach {
            book.item.rules -= it
            excluded.items = base.excluded(book.item)
            excluded.selectionModel.select(it)
            excluded.requestFocus()
        }
    }


    private fun includeRule() {
        val toInclude = excluded.selectionModel.selectedItems.toList()
        included.selectionModel.clearSelection()
        toInclude.forEach {
            book.item.rules += it
            excluded.items = base.excluded(book.item)
            included.selectionModel.select(it)
            included.requestFocus()
        }
    }

}
