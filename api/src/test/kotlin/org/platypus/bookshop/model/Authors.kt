package org.platypus.bookshop.model

import org.platypus.v2.model.Model

object Authors : Model<Authors>("bookshop.author"){

    val name = buildInName{
        required = true
    }
}