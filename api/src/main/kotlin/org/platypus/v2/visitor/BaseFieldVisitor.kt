package org.platypus.v2.visitor

import org.platypus.v2.model.field.classic.BinaryField
import org.platypus.v2.model.field.classic.BooleanField
import org.platypus.v2.model.field.classic.DateField
import org.platypus.v2.model.field.classic.DateTimeField
import org.platypus.v2.model.field.classic.DecimalField
import org.platypus.v2.model.field.classic.IntField
import org.platypus.v2.model.field.reference.Many2ManyField
import org.platypus.v2.model.field.reference.Many2OneField
import org.platypus.v2.model.field.reference.One2ManyField
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

interface BaseFieldVisitor<PARAM, RETURN> {
    fun visit(field: StringField<*>, p: PARAM): RETURN
    fun visit(field: DateField<*>, p: PARAM): RETURN
    fun visit(field: DateTimeField<*>, p: PARAM): RETURN
    fun visit(field: TimeField<*>, p: PARAM): RETURN
    fun visit(field: BooleanField<*>, p: PARAM): RETURN
    fun visit(field: TextField<*>, p: PARAM): RETURN
    fun visit(field: DecimalField<*>, p: PARAM): RETURN
    fun visit(field: IntField<*>, p: PARAM): RETURN
    fun visit(field: BinaryField<*>, p: PARAM): RETURN
    fun visit(field: One2ManyField<*, *>, p: PARAM): RETURN
    fun visit(field: Many2OneField<*, *>, p: PARAM): RETURN
    fun visit(field: Many2ManyField<*, *>, p: PARAM): RETURN
    fun visit(field: SelectionField<*, *>, p: PARAM): RETURN
    fun visit(field: IdField<*>, p: PARAM): RETURN
    fun visit(field: CreateDateField<*>, p: PARAM): RETURN
    fun visit(field: CreateUserField<*>, p: PARAM): RETURN
    fun visit(field: ExternalRefField<*>, p: PARAM): RETURN
    fun visit(field: WriteDateField<*>, p: PARAM): RETURN
    fun visit(field: WriteUserField<*>, p: PARAM): RETURN
}