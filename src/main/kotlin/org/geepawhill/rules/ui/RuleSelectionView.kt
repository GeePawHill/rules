package org.geepawhill.rules.ui

import javafx.beans.property.ObjectProperty
import javafx.scene.Parent
import org.geepawhill.rules.domain.Rule
import org.geepawhill.rules.domain.Rulebase
import org.geepawhill.rules.domain.Rulebook
import tornadofx.*

class RuleSelectionView(val base: Rulebase, bookProperty: ObjectProperty<Rulebook>) : View() {

    private val book by bookProperty
    val rule = RuleModel()

    override val root: Parent =
            hbox {
                tableview(book.rules) {
                    readonlyColumn("Name", Rule::name)
                    readonlyColumn("Description", Rule::description)
                    bindSelected(rule)
                    bookProperty.addListener { _, _, after ->
                        items = after.rules
                    }
                }
                vbox {
                    label("Tools")
                }
                tableview(base.excluded(book)) {
                    readonlyColumn("Name", Rule::name)
                    readonlyColumn("Description", Rule::description)
                    bindSelected(rule)
                    bookProperty.addListener { _, _, after ->
                        items = base.excluded(after)
                    }
                }

            }

    init {
    }

}
