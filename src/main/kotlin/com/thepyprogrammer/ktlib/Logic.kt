package com.thepyprogrammer.ktlib

fun <T> unless(condition: Boolean, block: () -> T): T? =
    if(!condition) block()
    else null

fun <T> unless(condition: Int, block: () -> T): T? =
    if(!condition.toBoolean()) block()
    else null

fun <T> given(condition: Boolean, block: () -> T): T? =
    if(condition) block()
    else null

fun <T> given(condition: Int, block: () -> T): T? =
    if(condition.toBoolean()) block()
    else null

fun loop(condition: () -> Boolean = { true }, block: () -> Unit = {}) {
    while(condition()) block()
}

fun loopInt(condition: () -> Int = { 1 }, block: () -> Unit = {}) {
    loop({ condition().toBoolean() }, block)
}

fun until(condition: () -> Boolean = { true }, block: () -> Unit = {}) {
    while(!condition()) block()
}

fun untilInt(condition: () -> Int = { 1 }, block: () -> Unit = {}) {
    until({ condition().toBoolean() }, block)
}

infix fun Any.`=`(other: Any) = this to other

class Object(vararg vals: Pair<Any, Any>): HashMap<Any, Any>(hashMapOf(*vals)) {
    fun let(vararg pairs: Pair<Any, Any>) {
        putAll(hashMapOf(*pairs))
    }
}


/**
 * struct like in Swift, intertextuality it is.
 */
fun struct(vararg vals: Pair<Any, Any>, apply: Object.() -> Unit) = Object().apply(apply)

//fun test() {
//    var other = 5
//    val obj = struct(
//        "five" `=` 10,
//        "hello" `=` 99
//    ) {
//        other = this["five"] as Int
//    }
//
//    obj.let(
//        "this" `=` 5,
//        9 `=` 20,
//        "99" `=` 99
//    )
//}


fun <T> recurse(init: T, block: (T) -> T?): MutableList<T> {
    var response = block(init)
    val responses = mutableListOf<T>()
    while(response != null) {
        responses.add(response)
        response = block(response)
    }
    return responses
}

fun <T> recursive(init: T, block: (Any, T) -> T) = block(block, init)

fun fib(maxn:Int) =
    recursive(maxn) { block, n ->
        val fib = block as ((Any, Int) -> Int)
        given(n > 2) {
            fib(fib, n-1) + fib(fib, n-2)
        } ?: n
    }

data class Knapsack(val capacity: Int, val weights: List<Int>, val values: List<Int>, var counter: Int)

//fun knapsack(capacity: Int, weights: List<Int>, values: List<Int>, counter: Int) {
//    recursive(Knapsack(capacity, weights, values, counter)) { block, sack ->
//        val knapsackFun = block as ((Any, Knapsack) -> Knapsack)
//        val newKnapsack = sack.also { it.counter-- }
//        unless(
//            counter == 0 || capacity == 0
//        ) {
//            if(sack.weights.get(counter - 1) > capacity) {
//                knapsackFun(knapsackFun, newKnapsack)
//            } else {
//                val left_capacity = sack.capacity - weights[counter - 1]
//                knapsackFun(knapsackFun, newKnapsack)
//            }
//        } ?: 0
//
//    }
//}


//fun maxDivide(a: Int, b: Int): Int = run {
//    var _a = a
//    loopInt({ _a % b }) { _a /= b }
//    _a
//    recurse(a) {
//        unless(it % b) { it / b }
//    }[0]
//}

fun Boolean.toInt() = if(this) 1 else 0
fun Int.toBoolean() = this != 0

