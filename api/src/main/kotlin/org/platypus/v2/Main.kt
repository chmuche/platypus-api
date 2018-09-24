package org.platypus.v2

//import org.platypus.v2.modules.base.entities.users
import org.platypus.v2.model.BaseModel
import org.platypus.v2.modules.base.BaseModule
import org.platypus.v2.modules.base.entities.groups
import org.platypus.v2.modules.base.entities.locale
import org.platypus.v2.modules.base.entities.name
import org.platypus.v2.modules.base.entities.password
import org.platypus.v2.modules.base.entities.users
import org.platypus.v2.modules.base.models.Groups
import org.platypus.v2.modules.base.models.Groups.users
import org.platypus.v2.modules.base.models.Users.locale
import org.platypus.v2.modules.base.models.Users.password
import org.platypus.v2.record.bag.Bag
import org.platypus.v2.record.bag.BagRecordImpl
import org.platypus.v2.record.one.BagBuilder
import org.platypus.v2.record.one.Record
import org.platypus.v2.record.one.RecordBuilder
import org.platypus.v2.record.one.RecordBuilderToStore
import org.platypus.v2.server.PlatypusServer

fun main(args: Array<String>) {
    val server = PlatypusServer.install(BaseModule)
//    server.inManagedTransaction {
//        val stmt = it.executor.prepareStatement("INSERT INTO res_users (NAME, PASSWORD, LOCALE) VALUES (?, ?, ?)")
//        stmt.setString(1, "Alexis")
//        stmt.setString(2, "Alexis")
//        stmt.setString(3, "Alexis")
//        stmt.addBatch()
//        stmt.setString(1, "Alexis")
//        stmt.setString(2, "Alexis")
//        stmt.setString(3, "Alexis")
//        stmt.addBatch()
//        stmt.setString(1, "Alexis")
//        stmt.setString(2, "Alexis")
//        stmt.setString(3, "Alexis")
//        stmt.addBatch()
//        stmt.setString(1, "Alexis")
//        stmt.setString(2, "Alexis")
//        stmt.setString(3, "Alexis")
//        stmt.addBatch()
//        val rs = stmt.executeBatch()
//        println("IDS $rs")
//    }
    server.inManagedEnvironment {

        val group1 = it.groups.store(it.groups.builderToStore {
            name = "Group1"
        })
        println(group1.id)

        val group2 = it.groups.store(it.groups.builderToStore {
            name = "Group2"
        })
        println(group2.id)

        val userBuilder = it.users.builderToStore {
            locale = "fr_FR"
            name = "Damien"
            password = "Fred2"
            groups.add(group1 + group2)
        }

        val user = it.users.store(userBuilder)
        println(user.id)

//        println(user.name)
//        println(user.password)
//        println(user.locale)
    }
}

fun <M:BaseModel<M>> bagOf(vararg records: Record<M>) : Bag<M>{
    val first = records.first()
    return BagRecordImpl(first.env, first.model, records.map { it.id })
}

fun <M:BaseModel<M>, TM:BaseModel<TM>> RecordBuilder<M>.bagBuilderOf(vararg records: Record<TM>) : BagBuilder<M, TM>{
    TODO()
}