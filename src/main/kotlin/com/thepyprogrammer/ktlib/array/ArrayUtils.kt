package com.thepyprogrammer.ktlib.array

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




private fun substring(string: String, range: IntProgression) = range.map { string[it] }.joinToString("")

private fun <T> subList(list: List<T>, range: IntProgression): List<T> = range.map { list[it] }

/**
 * Get sublist of Array/List
 */
inline operator fun <reified T> Array<T>.get(vararg args: Int): Array<T> = args.map { this[it % this.size] }.toTypedArray()
inline operator fun <reified T> List<T>.get(vararg args: Int): List<T> = args.map { this[it % this.size] }


inline infix fun <reified R, reified S, reified T> Pair<R, S>.to(other: T) = Triple(first, second, other)

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
 * Get a 1D x-sized Zero Double Array
 */
fun zeros(x: Int) = (0..x).map { 0.0 }.toTypedArray()

/**
 * Get a 2D row x col Zero Double Array
 */
fun zeros(row: Int, col: Int): DoubleArray2D = (0..row).map { zeros(col) }.toTypedArray()

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

