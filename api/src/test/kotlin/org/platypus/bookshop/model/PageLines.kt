package org.platypus.bookshop.model

import org.platypus.v2.model.Model

object PageLines : Model<PageLines>("bookshop.book.page.lines") {

    val name = buildInName {
        required = true
    }
    val number = integer("number") {
        required = true
    }
    val page = many2one("page", Pages)
}