package org.platypus.v2.modules.base.models

import org.platypus.v2.model.Model
import java.util.*

object Users : Model<Users>("res.users") {

    override val modelLabel = "Users"

    val name = buildInName {
        label = "Login"
    }
    val password = string("password") {
        label = "Password"
    }
    val locale = string("locale") {
        label = "The User Language"
        required = true
        defaultValueFun = { Locale.US.displayName }
    }

    val groups = many2many("groups", { userGroupRel })
    val partner = many2one("partner", Users)
}