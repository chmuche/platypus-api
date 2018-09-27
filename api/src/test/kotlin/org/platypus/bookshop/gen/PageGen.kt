package org.platypus.bookshop.gen

import org.platypus.bookshop.model.Authors
import org.platypus.bookshop.model.Pages
import org.platypus.bookshop.model.Books
import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.record.bag.Bag
import org.platypus.v2.record.one.MutableRecordBuilder
import org.platypus.v2.record.one.Record
import org.platypus.v2.record.one.RecordBuilder
import org.platypus.v2.record.repo.RecordRepository
import org.platypus.v2.record.repo.RecordRepositoryImpl

typealias PageRepository = RecordRepository<Pages>
typealias Page = Record<Pages>
typealias PageBag = Bag<Pages>
val PlatypusEnvironment.Page: PageRepository
    get() = RecordRepositoryImpl(this, Pages)

//Record Field
val Page.name by Pages.number
val Page.content by Pages.content
val Page.book by Pages.book
val Page.chapter by Pages.chapter
val Page.lines by Pages.lines

val RecordBuilder<Pages>.number by Pages.number
var MutableRecordBuilder<Pages>.number by Pages.number

val RecordBuilder<Pages>.content by Pages.content
var MutableRecordBuilder<Pages>.content by Pages.content

val RecordBuilder<Pages>.book by Pages.book
var MutableRecordBuilder<Pages>.book by Pages.book

val RecordBuilder<Pages>.chapter by Pages.chapter
var MutableRecordBuilder<Pages>.chapter by Pages.chapter

val RecordBuilder<Pages>.lines by Pages.lines
