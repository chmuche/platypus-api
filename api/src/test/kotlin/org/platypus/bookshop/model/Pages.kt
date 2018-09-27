package org.platypus.bookshop.model

import org.platypus.v2.model.Model

object Pages : Model<Pages>("bookshop.book.page") {
    val number = integer("number")
    val content = text("content")

    val book = many2one("book", Books)
    val chapter = many2one("chapter", BookChapters)
    val lines = one2many("lines", { PageLines.page })
}