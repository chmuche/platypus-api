package org.platypus.v2.modules.base.models

import org.platypus.v2.model.Model

/**
 * This model don't have a generated entity but an hand coded one to fit to some feature the platypus framework need
 */
object Groups : Model<Groups>("res.groups") {

    val name = buildInName {
        label = "Login"
    }
    val users = many2manyR("users", { userGroupRel })
}