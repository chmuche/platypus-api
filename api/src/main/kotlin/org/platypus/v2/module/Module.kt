package org.platypus.v2.module

import org.platypus.v2.model.BaseModel

interface PlatypusModule{
    val depends:Set<PlatypusModule>
    val moduleName:String
    val models:Set<BaseModel<*>>

}