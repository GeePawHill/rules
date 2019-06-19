package org.geepawhill.rules.domain

import tornadofx.*

class Rulebase {
    val rules = mutableListOf<Rule>()
    val books = mutableListOf<Rulebook>()

    fun excluded(book: Rulebook) = rules.filter { !book.rules.contains(it) }.toMutableList().observable()
}