package org.geepawhill.rules.ui

import javafx.scene.Parent
import javafx.scene.control.TableView
import org.geepawhill.rules.domain.Rule
import org.geepawhill.rules.domain.Rulebase
import tornadofx.*

class RuleSelectionView(val base: Rulebase, book: RulebookModel) : View() {

    val rule = RuleModel()

    lateinit var included: TableView<Rule>
    lateinit var excluded: TableView<Rule>

    override val root: Parent =
            hbox {
                included = tableview {
                    readonlyColumn("Name", Rule::name)
                    readonlyColumn("Description", Rule::description)
                    bindSelected(rule)
                }
                vbox {
                    label("Tools")
                }
                excluded = tableview {
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

}
