package org.geepawhill.rules.ui

import javafx.scene.Scene
import javafx.stage.Stage
import tornadofx.*

class Client : App() {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.scene = Scene(MainRulesView().root)
        stage.show()
    }
}