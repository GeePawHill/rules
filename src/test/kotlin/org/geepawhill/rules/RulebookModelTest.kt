package org.geepawhill.rules

import org.assertj.core.api.Assertions.assertThat
import org.geepawhill.rules.domain.Rule
import org.geepawhill.rules.ui.RulebookModel
import org.junit.jupiter.api.Test


class RulebookModelTest {
    val model = RulebookModel()
    val firstRule = Rule("1", "1")
    val secondRule = Rule("2", "2")
    val thirdRule = Rule("3", "3")
    val fourthRule = Rule("4", "4")


    @Test
    fun `lowers one rules priority`() {
        model.rules.add(firstRule)
        model.rules.add(secondRule)

        model.lowerPriority(listOf(firstRule))
        assertThat(model.rules).containsExactly(secondRule, firstRule)
    }

    @Test
    fun `noop if single rule is last`() {
        model.rules.add(firstRule)
        model.rules.add(secondRule)

        model.lowerPriority(listOf(secondRule))
        assertThat(model.rules).containsExactly(firstRule, secondRule)

    }

    @Test
    fun `valid straight sequence lowers priority`() {
        model.rules.add(firstRule)
        model.rules.add(secondRule)
        model.rules.add(thirdRule)

        model.lowerPriority(listOf(firstRule, secondRule))
        assertThat(model.rules).containsExactly(thirdRule, firstRule, secondRule)
    }

    @Test
    fun `valid non-straight sequence lowers priority`() {
        model.rules.add(firstRule)
        model.rules.add(secondRule)
        model.rules.add(thirdRule)
        model.rules.add(fourthRule)

        model.lowerPriority(listOf(firstRule, thirdRule))
        assertThat(model.rules).containsExactly(secondRule, firstRule, fourthRule, thirdRule)
    }

}