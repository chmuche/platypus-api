package org.platypus.v2.modules.base.models

import org.platypus.v2.model.ModelMany2Many
import org.platypus.v2.model.many2manyLink

val ModelMany2Many.userGroupRel by Users many2manyLink Groups