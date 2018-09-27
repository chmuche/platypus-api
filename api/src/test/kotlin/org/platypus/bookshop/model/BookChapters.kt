package org.platypus.bookshop.model

import org.platypus.v2.model.Model

object BookChapters : Model<BookChapters>("bookshop.book.chapter") {

    val name = buildInName()

    val pages = one2many("pages", { Pages.chapter })
}