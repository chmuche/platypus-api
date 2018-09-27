package org.platypus.bookshop

import org.platypus.bookshop.gen.books
import org.platypus.v2.server.PlatypusServer

fun main(args: Array<String>) {
    val server = PlatypusServer.install(BookShopModule)
    server.inManagedEnvironment {
//        book1Data.create(it)

    }
}