package org.platypus.v2.db

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.classic.BinaryField
import org.platypus.v2.model.field.classic.BooleanField
import org.platypus.v2.model.field.classic.DateField
import org.platypus.v2.model.field.classic.DateTimeField
import org.platypus.v2.model.field.classic.DecimalField
import org.platypus.v2.model.field.classic.IntField
import org.platypus.v2.model.field.classic.SelectionField
import org.platypus.v2.model.field.classic.StringField
import org.platypus.v2.model.field.classic.TextField
import org.platypus.v2.model.field.classic.TimeField
import org.platypus.v2.model.field.magic.CreateDateField
import org.platypus.v2.model.field.magic.CreateUserField
import org.platypus.v2.model.field.magic.ExternalRefField
import org.platypus.v2.model.field.magic.IdField
import org.platypus.v2.model.field.magic.WriteDateField
import org.platypus.v2.model.field.magic.WriteUserField
import org.platypus.v2.model.field.reference.Many2ManyField
import org.platypus.v2.model.field.reference.Many2OneField
import org.platypus.v2.model.field.reference.One2ManyField
import org.platypus.v2.modules.base.models.Users
import org.platypus.v2.visitor.BaseFieldVisitor

object ReferencedModelFinder : BaseFieldVisitor<Unit, BaseModel<*>?> {

    override fun visit(field: IdField<*>, p: Unit) = null

    override fun visit(field: StringField<*>, p: Unit) = null

    override fun visit(field: DateField<*>, p: Unit) = null

    override fun visit(field: DateTimeField<*>, p: Unit) = null

    override fun visit(field: TimeField<*>, p: Unit) = null

    override fun visit(field: BooleanField<*>, p: Unit) = null

    override fun visit(field: TextField<*>, p: Unit) = null

    override fun visit(field: DecimalField<*>, p: Unit) = null

    override fun visit(field: IntField<*>, p: Unit) = null

    override fun visit(field: BinaryField<*>, p: Unit) = null

    override fun visit(field: One2ManyField<*, *>, p: Unit) = field.targetField().model

    override fun visit(field: Many2OneField<*, *>, p: Unit) = field.target

    override fun visit(field: Many2ManyField<*, *>, p: Unit) = field.target

    override fun visit(field: CreateUserField<*>, p: Unit) = Users

    override fun visit(field: CreateDateField<*>, p: Unit) = Users

    override fun visit(field: SelectionField<*, *>, p: Unit) = null

    override fun visit(field: ExternalRefField<*>, p: Unit) = null

    override fun visit(field: WriteDateField<*>, p: Unit) = null

    override fun visit(field: WriteUserField<*>, p: Unit) = null
}