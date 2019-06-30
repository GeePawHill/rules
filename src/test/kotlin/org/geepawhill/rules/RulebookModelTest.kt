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
        model.included.add(firstRule)
        model.included.add(secondRule)

        model.lower(listOf(firstRule))
        assertThat(model.included).containsExactly(secondRule, firstRule)
    }

    @Test
    fun `lowering is noop if single rule is last`() {
        model.included.add(firstRule)
        model.included.add(secondRule)

        model.lower(listOf(secondRule))
        assertThat(model.included).containsExactly(firstRule, secondRule)
    }

    @Test
    fun `lower is noop if multiple rule includes last`() {
        model.included.add(firstRule)
        model.included.add(secondRule)
        model.included.add(thirdRule)

        model.lower(listOf(firstRule, thirdRule))
        assertThat(model.included).containsExactly(firstRule, secondRule, thirdRule)
    }

    @Test
    fun `lower is noop if multiple rule includes last, input order insignificant`() {
        model.included.add(firstRule)
        model.included.add(secondRule)
        model.included.add(thirdRule)

        model.lower(listOf(thirdRule, firstRule))
        assertThat(model.included).containsExactly(firstRule, secondRule, thirdRule)
    }

    @Test
    fun `valid straight sequence lowers priority for all`() {
        model.included.add(firstRule)
        model.included.add(secondRule)
        model.included.add(thirdRule)

        model.lower(listOf(firstRule, secondRule))
        assertThat(model.included).containsExactly(thirdRule, firstRule, secondRule)
    }

    @Test
    fun `valid non-straight sequence lowers priority for all`() {
        model.included.add(firstRule)
        model.included.add(secondRule)
        model.included.add(thirdRule)
        model.included.add(fourthRule)

        model.lower(listOf(firstRule, thirdRule))
        assertThat(model.included).containsExactly(secondRule, firstRule, fourthRule, thirdRule)
    }

    @Test
    fun `raises one rules priority`() {
        model.included.add(firstRule)
        model.included.add(secondRule)

        model.raise(listOf(secondRule))
        assertThat(model.included).containsExactly(secondRule, firstRule)
    }

    @Test
    fun `raising is noop if single rule is first`() {
        model.included.add(firstRule)
        model.included.add(secondRule)

        model.raise(listOf(firstRule))
        assertThat(model.included).containsExactly(firstRule, secondRule)
    }

    @Test
    fun `raise is noop if multiple rule includes first`() {
        model.included.add(firstRule)
        model.included.add(secondRule)
        model.included.add(thirdRule)

        model.raise(listOf(firstRule, thirdRule))
        assertThat(model.included).containsExactly(firstRule, secondRule, thirdRule)
    }

    @Test
    fun `raise is noop if multiple rule includes first, input order does not matter`() {
        model.included.add(firstRule)
        model.included.add(secondRule)
        model.included.add(thirdRule)

        model.raise(listOf(thirdRule, firstRule))
        assertThat(model.included).containsExactly(firstRule, secondRule, thirdRule)
    }

    @Test
    fun `valid straight sequence raises priority for all`() {
        model.included.add(firstRule)
        model.included.add(secondRule)
        model.included.add(thirdRule)

        model.raise(listOf(secondRule, thirdRule))
        assertThat(model.included).containsExactly(secondRule, thirdRule, firstRule)
    }

    @Test
    fun `valid non-straight sequence raises priority for all`() {
        model.included.add(firstRule)
        model.included.add(secondRule)
        model.included.add(thirdRule)
        model.included.add(fourthRule)

        model.raise(listOf(secondRule, fourthRule))
        assertThat(model.included).containsExactly(secondRule, firstRule, fourthRule, thirdRule)
    }

}