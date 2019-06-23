package org.geepawhill.rules.ui

import org.geepawhill.rules.domain.Rule
import org.geepawhill.rules.domain.Rulebook
import tornadofx.*
import java.util.Collections.sort

class RulebookModel : ItemViewModel<Rulebook>() {
    val nameProperty = bind(Rulebook::name)
    val descriptionProperty = bind(Rulebook::description)
    val rulesProperty = bind(Rulebook::rules)

    val rules by rulesProperty

    fun lowerPriority(toLower: List<Rule>) {
        sort(toLower) { first, second ->
            rules.indexOf(second) - rules.indexOf(first)
        }
        if (toLower.first() == rules.last()) return
        toLower.forEach {
            val index = rules.indexOf(it)
            if (index < rules.size - 1) {
                rules.remove(it)
                rules.add(index + 1, it)
            }
        }
    }

    fun raisePriority(toRaise: List<Rule>) {
        if (toRaise.contains(rules.first())) return
        toRaise.forEach {
            val index = rules.indexOf(it)
            rules.remove(it)
            rules.add(index - 1, it)
        }
    }

}
