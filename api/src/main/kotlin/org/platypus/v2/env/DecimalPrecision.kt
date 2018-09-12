package org.platypus.v2.env

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

/**
 * The class decimal precision is needed by some
 */
data class DecimalPrecision(
        val precision: Int = 10,
        val scale: Int = 5,
        val roundingMode: RoundingMode = RoundingMode.UP
)


object DecimalPrecisions {

}

fun DecimalPrecision.toMathContext(): MathContext = MathContext(precision, roundingMode)

fun BigDecimal.applyPrecision(precision: DecimalPrecision = DecimalPrecision()): BigDecimal = this.setScale(precision.scale, precision.roundingMode)


interface UIWidget