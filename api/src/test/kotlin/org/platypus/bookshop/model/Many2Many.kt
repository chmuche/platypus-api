package org.platypus.bookshop.model

import org.platypus.v2.model.ModelMany2Many
import org.platypus.v2.model.many2manyLink

val ModelMany2Many.otherAuthors by Books many2manyLink Authors