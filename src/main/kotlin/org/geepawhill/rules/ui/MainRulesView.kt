package org.geepawhill.rules.ui

import javafx.scene.Parent
import org.geepawhill.rules.domain.Rule
import tornadofx.*

class MainRulesView : View() {


    override val root: Parent = vbox {
        toolbar {
            button("New Rule")
        }
    }


}