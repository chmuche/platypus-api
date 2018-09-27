package org.platypus.bookshop.gen

import org.platypus.bookshop.model.Authors
import org.platypus.bookshop.model.PageLines
import org.platypus.bookshop.model.Books
import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.record.bag.Bag
import org.platypus.v2.record.one.MutableRecordBuilder
import org.platypus.v2.record.one.Record
import org.platypus.v2.record.one.RecordBuilder
import org.platypus.v2.record.repo.RecordRepository
import org.platypus.v2.record.repo.RecordRepositoryImpl

typealias PageLineRepository = RecordRepository<PageLines>
typealias PageLine = Record<PageLines>
typealias PageLineBag = Bag<PageLines>
val PlatypusEnvironment.PageLine: PageLineRepository
    get() = RecordRepositoryImpl(this, PageLines)

//Record Field
val PageLine.number by PageLines.number
val PageLine.name by PageLines.name
val PageLine.page by PageLines.page

val RecordBuilder<PageLines>.number by PageLines.number
var MutableRecordBuilder<PageLines>.number by PageLines.number

val RecordBuilder<PageLines>.name by PageLines.name
var MutableRecordBuilder<PageLines>.name by PageLines.name

val RecordBuilder<PageLines>.page by PageLines.page
var MutableRecordBuilder<PageLines>.page by PageLines.page
