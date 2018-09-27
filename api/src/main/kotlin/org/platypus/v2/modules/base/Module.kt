package org.platypus.v2.modules.base

import org.platypus.v2.model.BaseModel
import org.platypus.v2.module.PlatypusModule
import org.platypus.v2.modules.base.models.Groups
import org.platypus.v2.modules.base.models.Users

object BaseModule : PlatypusModule {
    override val depends: Set<PlatypusModule> = emptySet()
    override val moduleName: String = "base"
    override val models: Set<BaseModel<*>> = setOf(Users, Groups)
}
