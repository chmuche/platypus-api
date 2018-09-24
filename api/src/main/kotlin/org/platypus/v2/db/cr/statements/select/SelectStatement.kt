package org.platypus.v2.db.cr.statements.select

import org.platypus.v2.db.predicate.Predicate
import org.platypus.v2.db.predicate.and
import org.platypus.v2.db.predicate.contains
import org.platypus.v2.db.predicate.eq
import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseField
import org.platypus.v2.modules.base.BaseModule
import org.platypus.v2.modules.base.models.Users
import org.platypus.v2.server.PlatypusServer

class SearchRecordImpl<M : BaseModel<M>>(
        val env: PlatypusEnvironment,
        val model: M,
        val fieldToRead: Set<BaseField<M, *>>? = null,
        val where: Predicate? = null,
        val limit: Int? = null,
        val offset: Int? = null,
        val orderby: List<Pair<BaseField<M, *>, Boolean>>? = null
) //: SqlStatement<Bag<M>>(env.cr.dialect, StatementType.SELECT, listOf(model)

fun main(args: Array<String>) {
    val sever = PlatypusServer.install(BaseModule)
    sever.inManagedEnvironment { env ->
        val r: SelectModelWhereBuilder<Users>.(Users) -> Unit = {
            it.name.eq("TOTO").and(it.partner.join { partner }.predicate { name.contains("845") })
        }
    }
}