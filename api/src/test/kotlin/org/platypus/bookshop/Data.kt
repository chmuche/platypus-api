package org.platypus.bookshop

import org.platypus.bookshop.gen.Page
import org.platypus.bookshop.gen.author
import org.platypus.bookshop.gen.book
import org.platypus.bookshop.gen.bookChapter
import org.platypus.bookshop.gen.books
import org.platypus.bookshop.gen.chapter
import org.platypus.bookshop.gen.content
import org.platypus.bookshop.gen.isbn
import org.platypus.bookshop.gen.mainAuthor
import org.platypus.bookshop.gen.name
import org.platypus.bookshop.gen.number
import org.platypus.bookshop.gen.synopsys
import org.platypus.v2.env.PlatypusEnvironment
import java.util.*
import kotlin.math.absoluteValue

data class BookData(val nbChapter: Int, val nbPage: IntRange)

private val randomLine = arrayOf("Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        "Ut volutpat augue quis dapibus luctus",
        "Cras eleifend purus vitae lacinia facilisis",
        "Integer commodo ultricies rhoncus",
        "Sed sem tortor, hendrerit eget imperdiet sit amet, molestie sit amet augue",
        "Cras venenatis sem et metus ultrices, at facilisis lorem suscipit",
        "Mauris blandit interdum facilisis",
        "Vestibulum at urna facilisis lectus convallis aliquet",
        "Proin orci enim, malesuada et purus sit amet, commodo posuere ipsum",
        "Morbi sodales, nibh id porttitor ornare, justo magna suscipit ipsum, id condimentum nulla diam sit amet diam",
        "Duis finibus egestas ligula, ut ultrices arcu ultrices a",
        "Suspendisse vestibulum vestibulum accumsan",
        "Cras sit amet urna tempor, elementum mauris non, vulputate mauris",
        "Nulla nec cursus diam",
        "Phasellus erat turpis, elementum vitae mattis vitae, tincidunt vel neque",
        "Vestibulum nisl urna, lacinia sit amet turpis quis, tristique molestie dolor",
        "Suspendisse potenti",
        "Ut at molestie ex, a viverra mi",
        "Mauris egestas ex at dui lacinia, id vestibulum libero imperdiet",
        "Pellentesque semper, justo nec efficitur fermentum, nibh dui blandit neque, ut viverra libero enim sed tellus",
        "Cras rutrum nisl et lacinia lobortis",
        "Sed vehicula tristique mauris, vitae luctus orci pulvinar quis",
        "Quisque congue lorem ac dui auctor cursus",
        "Sed lacinia maximus magna, ut interdum ligula ornare id",
        "Duis rutrum ligula ac fermentum efficitur",
        "Morbi sed dui velit",
        "Interdum et malesuada fames ac ante ipsum primis in faucibus",
        "Cras quis nisl iaculis, sodales neque id, blandit lacus",
        "Maecenas consequat malesuada lectus in molestie",
        "Donec lacinia efficitur elementum",
        "Curabitur bibendum risus eu ultrices vestibulum",
        "Donec eu commodo nisl",
        "Morbi augue ex, accumsan vel est quis, luctus ultricies lectus",
        "Proin aliquet enim id mauris venenatis pharetra",
        "Curabitur pretium lectus a orci placerat, non laoreet ex aliquet")

val book1Data = createData { env ->
    for (bookNum in 1..20) {
        val author = env.author.store(env.author.builderToStore {
            it.externalRef = "author_$bookNum"
            it.name = "Author $bookNum"
        })
        val book = env.books.store(env.books.builderToStore {
            it.externalRef = "book_$bookNum"
            it.name = "Book $bookNum"
            it.isbn = "ISBN $bookNum"
            it.synopsys = """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
Ut volutpat augue quis dapibus luctus.
Cras eleifend purus vitae lacinia facilisis.
Integer commodo ultricies rhoncus.
Sed sem tortor, hendrerit eget imperdiet sit amet, molestie sit amet augue.
Cras venenatis sem et metus ultrices, at facilisis lorem suscipit.
Mauris blandit interdum facilisis. Vestibulum at urna facilisis lectus convallis aliquet.
Proin orci enim, malesuada et purus sit amet, commodo posuere ipsum. Morbi sodales, nibh id porttitor ornare, justo magna suscipit ipsum, id condimentum nulla diam sit amet diam.
Duis finibus egestas ligula, ut ultrices arcu ultrices a.
Suspendisse vestibulum vestibulum accumsan.
"""
            it.mainAuthor = author
        })
        for (chapterNum in 0..10) {
            val chapter = env.bookChapter.store(env.bookChapter.builderToStore {
                it.externalRef = "book_${bookNum}_chapter_$chapterNum"
                it.name = "Chapter $chapterNum of the Book $bookNum"
            })
            for (page in 1..25) {
                env.Page.store(env.Page.builderToStore {
                    it.externalRef = "book_${bookNum}_page_${(chapterNum * 25) + page + 1}"
                    it.number = page
                    it.chapter = chapter
                    it.book = book
                    it.content = randomLine[(Random().nextInt() % randomLine.size).absoluteValue]
                })
            }
        }
    }


}

interface DataFactory {

    fun create(env: PlatypusEnvironment)
}

class DataFactoryImpl(private val data: (PlatypusEnvironment) -> Unit) : DataFactory {
    override fun create(env: PlatypusEnvironment) = data(env)
}

fun createData(data: (PlatypusEnvironment) -> Unit): DataFactory = DataFactoryImpl(data)