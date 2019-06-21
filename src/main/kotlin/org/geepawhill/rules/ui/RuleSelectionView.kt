package org.geepawhill.rules.ui

import javafx.beans.property.ObjectProperty
import javafx.scene.Parent
import javafx.scene.control.TableView
import org.geepawhill.rules.domain.Rule
import org.geepawhill.rules.domain.Rulebase
import org.geepawhill.rules.domain.Rulebook
import tornadofx.*

class RuleSelectionView(val base: Rulebase, bookProperty: ObjectProperty<Rulebook>) : View() {

    private val book by bookProperty
    val rule = RuleModel()

    lateinit var included: TableView<Rule>
    lateinit var excluded: TableView<Rule>

    override val root: Parent =
            hbox {
                included = tableview(book.rules) {
                    readonlyColumn("Name", Rule::name)
                    readonlyColumn("Description", Rule::description)
                    bindSelected(rule)
                }
                vbox {
                    label("Tools")
                }
                excluded = tableview(base.excluded(book)) {
                    readonlyColumn("Name", Rule::name)
                    readonlyColumn("Description", Rule::description)
                    bindSelected(rule)
                }
            }

    init {
        bookProperty.addListener { _, _, after ->
            included.items = after.rules
            excluded.items = base.excluded(after)
        }
    }

}
