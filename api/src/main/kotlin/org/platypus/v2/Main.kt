package org.platypus.v2

import org.platypus.v2.modules.base.BaseModule
import org.platypus.v2.modules.base.models.Users
import org.platypus.v2.server.PlatypusServer

fun main(args: Array<String>) {
    val server = PlatypusServer.install(BaseModule)
    server.inManagedTransaction {
        it.executor.nativeWithNb("INSERT INTO res_users (NAME, PASSWORD, LOCALE) VALUES ('ALEXIS', 'ALEXIS', 'fr_FR')")
        it.executor.nativeWithNb("INSERT INTO res_users (NAME, PASSWORD, LOCALE) VALUES ('GAO', 'GAO', 'fr_FR')")
    }

    Users.createComplexeSelect{ builder ->

        builder.with("query1"){

        }
    }
}