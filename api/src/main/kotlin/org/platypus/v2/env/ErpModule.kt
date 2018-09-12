package org.platypus.v2.env

import org.platypus.v2.model.BaseModel
import org.platypus.v2.module.PlatypusModule

internal object ErpModule : PlatypusModule {
    override val moduleName: String = "platypus-erp-module"
    override val depends: MutableSet<PlatypusModule> = HashSet()
    override val models: MutableSet<BaseModel<*>> = HashSet()
    val loadedModule: MutableSet<PlatypusModule> = HashSet()
}
