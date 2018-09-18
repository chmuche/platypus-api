package org.platypus.v2.modules.base.entities

import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.modules.base.models.Groups
import org.platypus.v2.modules.base.models.Users
import org.platypus.v2.record.bag.Bag
import org.platypus.v2.record.one.ImmutableRecordField
import org.platypus.v2.record.one.Record
import org.platypus.v2.record.one.RecordBuilder
import org.platypus.v2.record.one.RecordField
import org.platypus.v2.record.repo.RecordRepository
import org.platypus.v2.record.repo.RecordRepositoryImpl

typealias GroupsRepository = RecordRepository<Groups>
//typealias UserData = DataRef<Users>
typealias Groups = Record<Groups>
typealias GroupsBag = Bag<Groups>

val PlatypusEnvironment.groups: GroupsRepository
    get() = RecordRepositoryImpl(this, Groups)

//User Entity IModelField
val ImmutableRecordField<Groups>.name by Groups.name
val ImmutableRecordField<Groups>.users by Groups.users

var RecordBuilder<Groups>.name by Groups.name
var RecordBuilder<Groups>.users by Groups.users


//User Entity Method
//fun User.hasGroup(g: GroupData): Boolean = Users.hasGroup.call(this, g)
//
//fun User.hasGroups(groups: Collection<GroupData>, all: Boolean = true): Boolean = Users.hasGroups.call(this, HasGroupsParam(groups, all))
//
//fun User.login(): UserLoginSuccess = Users.login.call(this).result
//fun User.hashPassword(): String? = Users.hashPassword.call(this)