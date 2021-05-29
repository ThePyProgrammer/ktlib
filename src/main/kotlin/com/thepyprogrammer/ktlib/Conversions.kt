package com.thepyprogrammer.ktlib

fun Boolean.toInt(): Int = if(this) 1 else 0
fun Boolean.toDouble(): Double = toInt().toDouble()
fun Boolean.toFloat(): Float = toInt().toFloat()
fun Boolean.toByte(): Byte = toInt().toByte()
fun Boolean.toLong(): Long = toInt().toLong()
fun Boolean.toShort(): Short = toInt().toShort()



// toBoolean methods

fun Int.toBoolean() = this != 0
fun Double.toBoolean() = this != 0.0
fun Float.toBoolean() = this != 0f
fun Byte.toBoolean() = this != 0.toByte()
fun Short.toBoolean() = this != 0.toShort()
fun Long.toBoolean() = this != 0.toLong()
fun Char.toBoolean() = this != ' '
fun String.toBoolean() = isNotEmpty()

fun <T> Array<T>.toBoolean() = isNotEmpty()
fun Array<String>.toBoolean() = joinToString("").toBoolean()
fun Array<Char>.toBoolean() = joinToString("").toBoolean()
fun Array<Int>.toBoolean() = sumOf { it }.toBoolean()
fun Array<Double>.toBoolean() = sumOf { it }.toBoolean()
fun Array<Float>.toBoolean() = sumOf { it.toDouble() }.toBoolean()
fun Array<Byte>.toBoolean() = sumOf { it.toInt() }.toBoolean()
fun Array<Short>.toBoolean() = sumOf { it.toInt() }.toBoolean()
fun Array<Long>.toBoolean() = sumOf { it }.toBoolean()
fun Array<Boolean>.toBoolean() = all { it }

fun DoubleArray.toBoolean() = toTypedArray().toBoolean()
fun FloatArray.toBoolean() = toTypedArray().toBoolean()
fun LongArray.toBoolean() = toTypedArray().toBoolean()
fun IntArray.toBoolean() = toTypedArray().toBoolean()
fun ShortArray.toBoolean() = toTypedArray().toBoolean()
fun ByteArray.toBoolean() = toTypedArray().toBoolean()
fun CharArray.toBoolean() = toTypedArray().toBoolean()
fun BooleanArray.toBoolean() = toTypedArray().toBoolean()

inline fun <reified T> Collection<T>.toBoolean() = toTypedArray().toBoolean()

inline fun <reified T> Iterator<T>.toBoolean() = hasNext()


fun String.toTypedArray() = toCharArray().toTypedArray()
