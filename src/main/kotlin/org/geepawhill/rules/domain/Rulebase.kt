package org.geepawhill.rules.domain

import tornadofx.*

class Rulebase {
    val rules = observableList<Rule>()
    val books = observableList<Rulebook>()

    fun excluded(book: Rulebook) = rules.filter { !book.included.contains(it) }.toMutableList().observable()
}