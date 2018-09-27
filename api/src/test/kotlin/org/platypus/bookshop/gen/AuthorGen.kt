package org.platypus.bookshop.gen

import org.platypus.bookshop.model.Authors
import org.platypus.bookshop.model.Books
import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.record.bag.Bag
import org.platypus.v2.record.one.MutableRecordBuilder
import org.platypus.v2.record.one.Record
import org.platypus.v2.record.one.RecordBuilder
import org.platypus.v2.record.repo.RecordRepository
import org.platypus.v2.record.repo.RecordRepositoryImpl

typealias AuthorRepository = RecordRepository<Authors>
typealias Author = Record<Authors>
typealias AuthorBag = Bag<Authors>
val PlatypusEnvironment.author: AuthorRepository
    get() = RecordRepositoryImpl(this, Authors)

//Record Field
val Author.name by Authors.name

val RecordBuilder<Authors>.name by Authors.name
var MutableRecordBuilder<Authors>.name by Authors.name