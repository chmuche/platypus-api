package org.platypus.bookshop.model

import org.platypus.v2.model.Model

object Books : Model<Books>("bookshop.book") {

    val name = buildInName {
        required = true
    }

    val mainAuthor = many2one("mainAuthor", Authors) {
        required = true
    }

    val participent = many2many("participent", { otherAuthors })

    val isbn = string("isbn") {
        required = true
    }

    val synopsys = text("synopsis")

    val dateEdited = date("dateEdited")

    val image = binary("image")

    val pages = one2many("pages", { Pages.book })


}