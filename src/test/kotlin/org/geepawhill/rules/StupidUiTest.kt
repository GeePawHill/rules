package org.geepawhill.rules

import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.layout.VBox
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.testfx.api.FxRobot
import org.testfx.api.FxToolkit
import tornadofx.*


abstract class StupidUiTest {

    private var endOfBeforeEach = false
    lateinit var robot: FxRobot
    lateinit var container: Parent
    lateinit var scene: Scene

    abstract fun beforeUi(): Parent

    @BeforeEach
    fun beforeEach() {
        FxToolkit.registerPrimaryStage()
        robot = FxRobot()
        container = beforeUi()
        scene = Scene(container)
        FxToolkit.setupScene { scene }
        FxToolkit.showStage()
        endOfBeforeEach = true
    }

    inline fun <reified T : Node> FxRobot.byTypeAndId(id: String): T {
        return lookup<Node> { it.id == id }.query() as T
    }

    inline fun <reified T : Node> byId(id: String): T {
        afterBeforeEachRequired()
        return robot.lookup<Node> { it.id == id }.query() as T
    }

    inline fun <reified T : Node> byPredicate(noinline pred: (node: Node) -> Boolean): T {
        afterBeforeEachRequired()
        return robot.lookup<Node> { pred(it) }.query() as T
    }

    fun afterBeforeEachRequired() {
        if (!endOfBeforeEach) throw RuntimeException("You can only use byXxxx methods in the @Test text itself.")
    }
}

@Disabled("Testing UI's this way sucks.")
class AnotherTest : StupidUiTest() {
    lateinit var button: Button
    lateinit var editor: TextField
    lateinit var label: Label

    override fun beforeUi() = VBox().apply {
        button = button("ClickMe!") {
            action {
                text = "Clicked!"
            }
        }
        editor = textfield("Start")
        label("Label") {
            id = "TheLabel"
        }
    }

    @Test
    fun `find by id`() {
        label = byId("TheLabel")
    }

    @Test
    fun `find by predicate`() {
        label = byPredicate { it is Label }
    }

    @Test
    fun `clicking button changes its text`() {
        robot.clickOn(button)
        assertThat(button.text).isEqualTo("Clicked!")
    }

    @Test
    fun `changing text changes it`() {
        robot.clickOn(editor)
        robot.type(KeyCode.X, 5)
        assertThat(editor.text).isEqualTo("Startxxxxx")
    }
}