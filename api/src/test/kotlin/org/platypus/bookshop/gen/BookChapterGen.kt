package org.platypus.bookshop.gen

import org.platypus.bookshop.model.Authors
import org.platypus.bookshop.model.BookChapters
import org.platypus.bookshop.model.Books
import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.record.bag.Bag
import org.platypus.v2.record.one.MutableRecordBuilder
import org.platypus.v2.record.one.Record
import org.platypus.v2.record.one.RecordBuilder
import org.platypus.v2.record.repo.RecordRepository
import org.platypus.v2.record.repo.RecordRepositoryImpl

typealias BookChapterRepository = RecordRepository<BookChapters>
typealias BookChapter = Record<BookChapters>
typealias BookChapterBag = Bag<BookChapters>
val PlatypusEnvironment.bookChapter: BookChapterRepository
    get() = RecordRepositoryImpl(this, BookChapters)

//Record Field
val BookChapter.name by BookChapters.name
val BookChapter.pages by BookChapters.pages

val RecordBuilder<BookChapters>.name by BookChapters.name
var MutableRecordBuilder<BookChapters>.name by BookChapters.name

val RecordBuilder<BookChapters>.pages by BookChapters.pages