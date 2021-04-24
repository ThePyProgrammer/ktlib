package com.thepyprogrammer.ktlib.array

/**
 * Functions for Arrays
 */

typealias DoubleArray2D = Array<Array<Double>>

/**
 * Returns array with piecewise operation having been performed
 * Can be utilised to apply piecewise functions on Iterable
 * @param  function  piecewise function to apply on each element
 */
inline fun <T, reified R> Iterable<T>.each(function: (T) -> R): Array<R> {
    val list = mutableListOf<R>()
    forEach { list.add(function( it )) }
    return list.toTypedArray()
}


/**
 * Returns array with piecewise operation having been performed
 * Can be utilised to apply piecewise functions on Array
 * @param  function  piecewise function to apply on each element
 */
inline fun <T, reified R> Array<T>.each(function: (T) -> R): Array<R> {
    val list = mutableListOf<R>()
    forEach { list.add(function(it)) }
    return list.toTypedArray()
}


/**
 * Returns list with piecewise operation having been performed
 * Can be utilised to apply piecewise functions on List
 * @param  function  piecewise function to apply on each element
 */
inline fun <T, reified R> List<T>.each(function: (T) -> R): List<R> {
    val list = mutableListOf<R>()
    forEach { list.add(function(it)) }
    return list
}

fun <T, R> Collection<T>.fold(
    initial: R,
    combine: (acc: R, nextElement: T) -> R
): R {
    var accumulator: R = initial
    forEach { accumulator = combine(accumulator, it) }
    return accumulator
}


/**
 * Piecewise Division for Double Array/List
 */
infix operator fun Array<Double>.div(x: Number) = each { it / x.toDouble() }
infix operator fun List<Double>.div(x: Number) = each { it / x.toDouble() }


/**
 * Slice (with start, end and stop) for Double Array
 */

data class Slice(val start: Int, val end: Int, val step: Int)

infix fun Pair<Int, Int>.by(step: Int) = Slice(first, second, step)

inline fun <reified T> Array<T>.slice(slice: Slice) = (slice.start..slice.end step slice.step).each { this[it] }

inline fun <reified T> Array<T>.slice(slice: Pair<Int, Int>) = slice( slice by 1 )

inline fun <reified T> Array<T>.slice(start: Int, end: Int, step: Int = 1) = slice( start to end by step )



inline fun <reified T> List<T>.slice(slice: Slice) = (slice.start..slice.end step slice.step).each { this[it] }

inline fun <reified T> List<T>.slice(slice: Pair<Int, Int>) = slice( slice by 1 )

inline fun <reified T> List<T>.slice(start: Int, end: Int, step: Int = 1) = slice( start to end by step )


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
inline fun <reified T: Number> Array<T>.normalise() = each { it.toDouble() - mean() }

/**
 * Get a 1D x-sized Zero Double Array
 */
fun zeros(x: Int) = (0..x).each { 0.0 }

/**
 * Get a 2D row x col Zero Double Array
 */
fun zeros(row: Int, col: Int): Array<Array<Double>> = (0..row).each { zeros(col) }

/**
 * Transpose 2D Double Array
 */
fun DoubleArray2D.transpose() = run {
    val numRow = this.size
    val numCol = this[0].size
    val array = zeros(numCol, numRow)

    for (i in 0..numRow) {
        for (j in 0..numCol) {
            array[j][i] = this[i][j]
        }
    }
    array
}

fun <T> mutableListOf(arr: Array<T>) = mutableListOf(*arr)