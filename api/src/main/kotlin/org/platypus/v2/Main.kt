package org.platypus.v2

//import org.platypus.v2.modules.base.entities.users
import org.platypus.v2.model.BaseModel
import org.platypus.v2.modules.base.BaseModule
import org.platypus.v2.modules.base.entities.groups
import org.platypus.v2.modules.base.entities.locale
import org.platypus.v2.modules.base.entities.name
import org.platypus.v2.modules.base.entities.password
import org.platypus.v2.modules.base.entities.users
import org.platypus.v2.modules.base.models.Groups.users
import org.platypus.v2.modules.base.models.Users.locale
import org.platypus.v2.modules.base.models.Users.password
import org.platypus.v2.record.bag.Bag
import org.platypus.v2.record.bag.BagRecordImpl
import org.platypus.v2.record.one.BagBuilder
import org.platypus.v2.record.one.Record
import org.platypus.v2.record.one.RecordBuilder
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
        val userBuilder = it.users.newBuilder {
            locale = "fr_FR"
            name = "Damien"
            password = "Damien"
        }

        userBuilder.password = "Fred"

        val user = it.users.store(userBuilder)

        val groupBuilder = it.groups.newBuilder {
//            Mode operator
            name = "Groups"
            name = null
//            Mode Method
//            name.set("Groups")
//            name.set(null)


//            Valable seulement pour les Many2Many et One2Many
//            Mode operator
            users += user // Ajout au exitant
            users = bagBuilderOf(user) // Remplace les exstant avec user
            users -= user //Supprime des liaison existantes user
            users = bagBuilderOf() //Supprime toutes les liaisons existantes

//            Mode method certaine method seront Dispo que pour les create et d'autre pour l'update
            users.add(user) // Ajout au exitant
            users.onlyKeep(user) // Ne garde user et supprime les existants
            users.remove(user) // Supprime la liaison avec user
            users.removeAll() // Supprime toutes les liaisons
            users.replaceWith(user) //Remplace les liaison existantes
            // Pareil que users.removeAll() + users.add(user) savhant que la derniere method est réelement appliqué

//            Derniere option Builder de creation Mode operator avec uquement += et =
//            Et Builder d'update Uniquement le Mode method



        }
        val group = it.groups.store(groupBuilder)

        println(user.id)
        println(group.id)

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