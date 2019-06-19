package org.geepawhill.rules.ui

import org.geepawhill.rules.domain.Rule
import tornadofx.*

class RuleModel : ItemViewModel<Rule>() {
    val nameProperty = bind(Rule::name)
    val descriptionProperty = bind(Rule::description)
}