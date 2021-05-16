package com.thepyprogrammer.ktlib.math.types

import com.thepyprogrammer.ktlib.math.`**`
import com.thepyprogrammer.ktlib.math.orderOfMagnitude
import com.thepyprogrammer.ktlib.math.round
import kotlin.math.roundToInt

data class StandardNotation(var base: Double, var power: Int = 0): Number() {
    constructor(base: Number): this(base.toDouble())

    init {
        val power = base.orderOfMagnitude
        this.power += power
        this.base /= 10 `**` power
    }


    override fun toByte(): Byte = toInt().toByte()
    override fun toChar(): Char = toDouble().toChar()

    override fun toDouble() = base * (10 `**` power)

    override fun toFloat() = toDouble().toFloat()

    override fun toInt(): Int = toDouble().roundToInt()

    override fun toLong(): Long = toInt().toLong()

    override fun toShort(): Short = toInt().toShort()

    override fun toString(): String = "$base × 10^$power"

    infix fun roundToSF(sf: Int) =
        if(sf <= -1) this
        else StandardNotation(base.round(sf - 1), power)

}
