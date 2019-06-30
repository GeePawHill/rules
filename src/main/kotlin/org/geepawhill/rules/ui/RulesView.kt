package org.geepawhill.rules.ui

import javafx.beans.binding.BooleanBinding
import org.geepawhill.rules.domain.Rule

interface RulesView {
    val isSelectedAndFocused: BooleanBinding
    val canRaise: BooleanBinding
    val canLower: BooleanBinding
    fun resetSelection(rules: List<Rule>)
    fun grabSelection(): List<Rule>
}