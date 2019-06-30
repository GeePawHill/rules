package org.geepawhill.rules.ui

import org.geepawhill.rules.domain.Rule
import org.geepawhill.rules.domain.Rulebook
import tornadofx.*
import java.util.Collections.sort

class RulebookModel : ItemViewModel<Rulebook>() {
    val nameProperty = bind(Rulebook::name)
    val descriptionProperty = bind(Rulebook::description)

    val includedProperty = bind(Rulebook::included)
    val included by includedProperty

    val excludedProperty = bind(Rulebook::excluded)
    val excluded by excludedProperty


    fun lower(toLower: List<Rule>) {
        sort(toLower) { first, second ->
            included.indexOf(second) - included.indexOf(first)
        }
        if (toLower.first() == included.last()) return
        toLower.forEach {
            val index = included.indexOf(it)
            if (index < included.size - 1) {
                included.remove(it)
                included.add(index + 1, it)
            }
        }
    }

    fun raise(toRaise: List<Rule>) {
        if (toRaise.contains(included.first())) return
        toRaise.forEach {
            val index = included.indexOf(it)
            included.remove(it)
            included.add(index - 1, it)
        }
    }

    fun exclude(toMove: List<Rule>) {
        included.removeAll(toMove)
    }

    fun include(toMove: List<Rule>) {
        included.addAll(toMove)
    }

}
