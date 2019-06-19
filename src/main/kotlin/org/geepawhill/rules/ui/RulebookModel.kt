package org.geepawhill.rules.ui

import org.geepawhill.rules.domain.Rulebook
import tornadofx.*

class RulebookModel(item: Rulebook) : ItemViewModel<Rulebook>(item) {
    val nameProperty = bind(Rulebook::name)
    val descriptionProperty = bind(Rulebook::description)
    val rulesProperty = bind(Rulebook::rules)
}
