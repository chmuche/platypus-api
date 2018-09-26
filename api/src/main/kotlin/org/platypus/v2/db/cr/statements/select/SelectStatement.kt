package org.platypus.v2.db.cr.statements.select

import org.platypus.v2.db.QueryBuilder
import org.platypus.v2.db.predicate.Predicate
import org.platypus.v2.model.AliasImpl
import org.platypus.v2.modules.base.BaseModule
import org.platypus.v2.modules.base.models.Groups.name
import org.platypus.v2.modules.base.models.Users
import org.platypus.v2.server.PlatypusServer


fun main(args: Array<String>) {
    val sever = PlatypusServer.install(BaseModule)
    sever.inManagedEnvironment { env ->
        val r: SelectModelWhereBuilder<Users>.(Users) -> Predicate = {
            it.name.eq("TOTO").and(it.partner.join { partner }.predicate { it.name.contains("845") })
        }
        val wherePart = SelectModelWhereBuilderImpl(AliasImpl(Users, "main_table"))
        val predicate  = wherePart.r(Users)

        println(wherePart.FROM(env.cr.dialect, predicate))
//        println(predicate.toSql(env.cr.dialect, QueryBuilder(false)))
    }
}

