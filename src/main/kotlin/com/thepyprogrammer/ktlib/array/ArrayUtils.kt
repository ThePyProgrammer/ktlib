package com.thepyprogrammer.ktlib.array

import com.thepyprogrammer.ktlib.toBoolean
import com.thepyprogrammer.ktlib.toTypedArray

/**
 * Functions for Arrays
 */

typealias DoubleArray2D = Array<Array<Double>>


/**
 * Piecewise Product for Double Array/List
 */
infix operator fun Array<Double>.times(x: Number) = map { it * x.toDouble() }
infix operator fun List<Double>.times(x: Number) = map { it * x.toDouble() }

/**
 * Piecewise Division for Double Array/List
 */
infix operator fun Array<Double>.div(x: Number) = map { it / x.toDouble() }
infix operator fun List<Double>.div(x: Number) = map { it / x.toDouble() }


inline infix fun <reified T> List<T>.from(range: IntProgression): List<T> = range.map { get(it % size) }
inline infix fun <reified T> Array<T>.from(range: IntProgression): Array<T> = (toList() from range).toTypedArray()

infix fun String.from(range: IntProgression) = (toTypedArray() from range).joinToString("")


/**
 * Get sublist of Array/List
 */
inline operator fun <reified T> List<T>.get(vararg args: Int): List<T> = args.map { this[it % this.size] }
inline operator fun <reified T> Array<T>.get(vararg args: Int): Array<T> = this.toList().get(*args).toTypedArray()


inline infix fun <reified R, reified S, reified T> Pair<R, S>.to(other: T): Triple<R, S, T> = Triple(first, second, other)
inline infix fun <reified R, reified S, reified T> R.to(other: Pair<S, T>): Triple<R, S, T> = this to other.first to other.second

class TripleNestedMap<R, S, T>(vararg args: Triple<R, S, T>): HashMap<R, HashMap<S, T>>() {
    init {
        val firstValues = args.map { it.first }. toHashSet()
        firstValues.forEach { first ->
            this[first] = hashMapOf(*args.filter { it.first == first }.map { it.second to it.third }.toTypedArray())
        }
    }
}

inline fun <reified R, reified S, reified T> tripleNestedMapOf(vararg args: Triple<R, S, T>): TripleNestedMap<R, S, T> =
    TripleNestedMap(*args)

inline fun <reified R, reified S, reified T> tripleNestedMapOf(vararg args: Pair<Pair<R, S>, T>): TripleNestedMap<R, S, T> =
    tripleNestedMapOf(*args.map { it.first to it.second }.toTypedArray())

@JvmName("tripleNestedMapOfPairAsSecondary")
inline fun <reified R, reified S, reified T> tripleNestedMapOf(vararg args: Pair<R, Pair<S, T>>): TripleNestedMap<R, S, T> =
    tripleNestedMapOf(*args.map { it.first to it.second }.toTypedArray())

/**
 * Slice (with start, end and stop) for Double Array
 */

data class Slice(var start: Int?, var end: Int?, var step: Int? = 1) {
    init {
        start = start ?: 0
        end = end ?: 0
        step = step ?: 1
    }
}

typealias Range = Pair<Int?, Int?>

infix fun Range.by(step: Int?) = Slice(first, second, step)

inline fun <reified T> Array<T>.slice(slice: Slice) = (
        ((slice.start ?: 0) % size)..((slice.end ?: 0) % size) step ((slice.step ?: 0) % size)
        )
    .map { this[it] }
    .toTypedArray()

inline fun <reified T> Array<T>.slice(range: Range) = slice( range by 1 )

inline fun <reified T> Array<T>.slice(start: Int?, end: Int?, step: Int? = 1) = slice( start to end by step )

inline fun <reified T> Array<T>.get(slice: Slice) = slice(slice)
inline fun <reified T> Array<T>.get(range: Range) = slice(range)



inline fun <reified T> List<T>.slice(slice: Slice) = (
        ((slice.start ?: 0) % size)..((slice.end ?: 0) % size) step ((slice.step ?: 0) % size)
        )
    .map { this[it] }

inline fun <reified T> List<T>.slice(range: Range) = slice( range by 1 )

inline fun <reified T> List<T>.slice(start: Int, end: Int, step: Int = 1) = slice( start to end by step )

inline fun <reified T> List<T>.get(slice: Slice) = slice(slice)
inline fun <reified T> List<T>.get(range: Range) = slice(range)


fun <R, S> Pair<R, S>.reverse(): Pair<S, R> = second to first

data class Shape(var row: Int, var col: Int) {
    init {
        if(row < 1) row = 1
        if(col < 1) col = 1
    }

    val reversed: Shape
        get() = Shape(col, row)

    val size: Int = row * col
}

infix fun Int.by(other: Int) = Shape(this, other)

val DoubleArray2D.shape
    get() = size by fold(0) { acc, value -> if (value.size > acc) value.size else acc }

/**
 * Find sum of all elements in Numeric Array
 */
inline fun <reified T: Number> Array<T>.sum() = fold(0.0){ acc, it -> acc + it.toDouble() }

/**
 * Find mean of Numeric Array
 */
inline fun<reified T: Number> Array<T>.mean() = sum() / size

/**
 * Normalise Double Array
 */
inline fun <reified T: Number> Array<T>.normalise() = map { it.toDouble() - mean() }

/**
 * Get a 1D x-sized One Double Array
 */
fun full(x: Int, value: Double) = (0..x).map { value }.toTypedArray()

/**
 * Get a 2D row x col One Double Array
 */
fun full(row: Int, col: Int, value: Double): DoubleArray2D = (0..row).map { full(col, value) }.toTypedArray()

fun full(shape: Shape, value: Double): DoubleArray2D = full(shape.row, shape.col, value)

/**
 * Get a 1D x-sized Zero Double Array
 */
fun zeros(x: Int) = full(x, 0.0)

/**
 * Get a 2D row x col Zero Double Array
 */
fun zeros(row: Int, col: Int): DoubleArray2D = full(row, col, 0.0)

fun zeros(shape: Shape): DoubleArray2D = full(shape, 0.0)

/**
 * Get a 1D x-sized One Double Array
 */
fun ones(x: Int) = full(x, 1.0)

/**
 * Get a 2D row x col One Double Array
 */
fun ones(row: Int, col: Int): DoubleArray2D = full(row, col, 1.0)

fun ones(shape: Shape): DoubleArray2D = full(shape, 0.0)

/**
 * Get an Identity Array
 */
fun eye(x: Int) = (0..x).map { zeros(x).apply { set(x, 1.0) } }.toTypedArray()




/**
 * Transpose 2D Double Array
 */
fun DoubleArray2D.transpose(sub: Double = 0.0) = run {
    val array = full(shape.reversed, sub)

    for (i in 0..shape.row) {
        for (j in 0..shape.col) {
            try { array[j][i] = this[i][j] } catch(ex: ArrayIndexOutOfBoundsException) {}
        }
    }
    array
}


inline infix fun <reified T> Array<T>.with(arr: Array<T>): Array<T> =
    toMutableList().apply { arr.forEach { add(it) } }.toTypedArray()

inline infix fun <reified T> List<T>.with(arr: List<T>): List<T> =
    toMutableList().apply { arr.forEach { add(it) } }.toList()


fun DoubleArray2D.flatten(): Array<Double> = fold(arrayOf()) { acc, value -> acc with value }

fun <T> mutableListOf(arr: Array<T>) = mutableListOf(*arr)

fun DoubleArray2D.reshape(shape: Shape, sub: Double = 0.0): DoubleArray2D = run {
    var flat = flatten()

    if (shape.size > flat.size) {
        flat = flat with full(shape.size - flat.size, sub)
    } else if (shape.size < flat.size) {
        flat = flat.slice(0 to shape.size)
    }

    val arr = full(shape, sub)
    for (i in 0..shape.size) {
        arr[i / shape.row][i % shape.col] = flat[i]

    }

    arr
}

infix fun DoubleArray2D.reshapeTo(shape: Shape) = reshape(shape)

fun DoubleArray2D.reshape(row: Int, col: Int, sub: Double = 0.0) = reshape(row by col, sub)
