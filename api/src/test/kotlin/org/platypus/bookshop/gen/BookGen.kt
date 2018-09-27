package org.platypus.bookshop.gen

import org.platypus.bookshop.model.Books
import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.record.bag.Bag
import org.platypus.v2.record.one.MutableRecordBuilder
import org.platypus.v2.record.one.Record
import org.platypus.v2.record.one.RecordBuilder
import org.platypus.v2.record.repo.RecordRepository
import org.platypus.v2.record.repo.RecordRepositoryImpl

typealias BooksRepository = RecordRepository<Books>
typealias Book = Record<Books>
typealias BooksBag = Bag<Books>
val PlatypusEnvironment.books: BooksRepository
    get() = RecordRepositoryImpl(this, Books)

//Record Field
val Book.name by Books.name
val Book.dateEdited by Books.dateEdited
val Book.mainAuthor by Books.mainAuthor
val Book.participent by Books.participent
val Book.isbn by Books.isbn
val Book.synopsys by Books.synopsys
val Book.image by Books.image
val Book.pages by Books.pages

val RecordBuilder<Books>.name by Books.name
var MutableRecordBuilder<Books>.name by Books.name

val RecordBuilder<Books>.dateEdited by Books.dateEdited
var MutableRecordBuilder<Books>.dateEdited by Books.dateEdited

val RecordBuilder<Books>.mainAuthor by Books.mainAuthor
var MutableRecordBuilder<Books>.mainAuthor by Books.mainAuthor

val RecordBuilder<Books>.participent by Books.participent

val RecordBuilder<Books>.isbn by Books.isbn
var MutableRecordBuilder<Books>.isbn by Books.isbn

val RecordBuilder<Books>.synopsys by Books.synopsys
var MutableRecordBuilder<Books>.synopsys by Books.synopsys

val RecordBuilder<Books>.image by Books.image
var MutableRecordBuilder<Books>.image by Books.image

val RecordBuilder<Books>.pages by Books.pages

