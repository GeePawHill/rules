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

        model.lower(listOf(firstRule))
        assertThat(model.rules).containsExactly(secondRule, firstRule)
    }

    @Test
    fun `lowering is noop if single rule is last`() {
        model.rules.add(firstRule)
        model.rules.add(secondRule)

        model.lower(listOf(secondRule))
        assertThat(model.rules).containsExactly(firstRule, secondRule)
    }

    @Test
    fun `lower is noop if multiple rule includes last`() {
        model.rules.add(firstRule)
        model.rules.add(secondRule)
        model.rules.add(thirdRule)

        model.lower(listOf(firstRule, thirdRule))
        assertThat(model.rules).containsExactly(firstRule, secondRule, thirdRule)
    }

    @Test
    fun `lower is noop if multiple rule includes last, input order insignificant`() {
        model.rules.add(firstRule)
        model.rules.add(secondRule)
        model.rules.add(thirdRule)

        model.lower(listOf(thirdRule, firstRule))
        assertThat(model.rules).containsExactly(firstRule, secondRule, thirdRule)
    }

    @Test
    fun `valid straight sequence lowers priority for all`() {
        model.rules.add(firstRule)
        model.rules.add(secondRule)
        model.rules.add(thirdRule)

        model.lower(listOf(firstRule, secondRule))
        assertThat(model.rules).containsExactly(thirdRule, firstRule, secondRule)
    }

    @Test
    fun `valid non-straight sequence lowers priority for all`() {
        model.rules.add(firstRule)
        model.rules.add(secondRule)
        model.rules.add(thirdRule)
        model.rules.add(fourthRule)

        model.lower(listOf(firstRule, thirdRule))
        assertThat(model.rules).containsExactly(secondRule, firstRule, fourthRule, thirdRule)
    }

    @Test
    fun `raises one rules priority`() {
        model.rules.add(firstRule)
        model.rules.add(secondRule)

        model.raise(listOf(secondRule))
        assertThat(model.rules).containsExactly(secondRule, firstRule)
    }

    @Test
    fun `raising is noop if single rule is first`() {
        model.rules.add(firstRule)
        model.rules.add(secondRule)

        model.raise(listOf(firstRule))
        assertThat(model.rules).containsExactly(firstRule, secondRule)
    }

    @Test
    fun `raise is noop if multiple rule includes first`() {
        model.rules.add(firstRule)
        model.rules.add(secondRule)
        model.rules.add(thirdRule)

        model.raise(listOf(firstRule, thirdRule))
        assertThat(model.rules).containsExactly(firstRule, secondRule, thirdRule)
    }

    @Test
    fun `raise is noop if multiple rule includes first, input order does not matter`() {
        model.rules.add(firstRule)
        model.rules.add(secondRule)
        model.rules.add(thirdRule)

        model.raise(listOf(thirdRule, firstRule))
        assertThat(model.rules).containsExactly(firstRule, secondRule, thirdRule)
    }

    @Test
    fun `valid straight sequence raises priority for all`() {
        model.rules.add(firstRule)
        model.rules.add(secondRule)
        model.rules.add(thirdRule)

        model.raise(listOf(secondRule, thirdRule))
        assertThat(model.rules).containsExactly(secondRule, thirdRule, firstRule)
    }

    @Test
    fun `valid non-straight sequence raises priority for all`() {
        model.rules.add(firstRule)
        model.rules.add(secondRule)
        model.rules.add(thirdRule)
        model.rules.add(fourthRule)

        model.raise(listOf(secondRule, fourthRule))
        assertThat(model.rules).containsExactly(secondRule, firstRule, fourthRule, thirdRule)
    }

}