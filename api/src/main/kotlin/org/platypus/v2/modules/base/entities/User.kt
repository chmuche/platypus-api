package org.platypus.v2.modules.base.entities

import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.modules.base.models.Users
import org.platypus.v2.record.bag.Bag
import org.platypus.v2.record.one.ImmutableRecordField
import org.platypus.v2.record.one.Record
import org.platypus.v2.record.one.RecordBuilder
import org.platypus.v2.record.one.RecordField
import org.platypus.v2.record.repo.RecordRepository
import org.platypus.v2.record.repo.RecordRepositoryImpl

typealias UserRepository = RecordRepository<Users>
//typealias UserData = DataRef<Users>
typealias User = Record<Users>
typealias UserBag = Bag<Users>

val PlatypusEnvironment.users: UserRepository
    get() = RecordRepositoryImpl(this, Users)

//User Entity IModelField
val ImmutableRecordField<Users>.name by Users.name
val ImmutableRecordField<Users>.password by Users.password
val ImmutableRecordField<Users>.locale by Users.locale
val ImmutableRecordField<Users>.groups by Users.groups

var RecordBuilder<Users>.name by Users.name
var RecordBuilder<Users>.locale by Users.locale
var RecordBuilder<Users>.password by Users.password
val RecordBuilder<Users>.groups by Users.groups


//User Entity Method
//fun User.hasGroup(g: GroupData): Boolean = Users.hasGroup.call(this, g)
//
//fun User.hasGroups(groups: Collection<GroupData>, all: Boolean = true): Boolean = Users.hasGroups.call(this, HasGroupsParam(groups, all))
//
//fun User.login(): UserLoginSuccess = Users.login.call(this).result
//fun User.hashPassword(): String? = Users.hashPassword.call(this)