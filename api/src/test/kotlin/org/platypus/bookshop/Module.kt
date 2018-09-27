package org.platypus.bookshop

import org.platypus.bookshop.gen.PageLine
import org.platypus.bookshop.model.Authors
import org.platypus.bookshop.model.BookChapters
import org.platypus.bookshop.model.Books
import org.platypus.bookshop.model.PageLines
import org.platypus.bookshop.model.Pages
import org.platypus.v2.model.BaseModel
import org.platypus.v2.module.PlatypusModule
import org.platypus.v2.modules.base.BaseModule
import org.platypus.v2.modules.base.models.Groups
import org.platypus.v2.modules.base.models.Users

object BookShopModule : PlatypusModule {
    override val depends: Set<PlatypusModule> = setOf(BaseModule)
    override val moduleName: String = "bookshop"
    override val models: Set<BaseModel<*>> = setOf(Books, BookChapters, Authors, Pages, PageLines)
}