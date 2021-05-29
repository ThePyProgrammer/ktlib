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






infix operator fun (() -> Unit).plus(other: () -> Unit): () -> Unit = { this(); other() }


infix operator fun <T> (T.() -> Unit).plus(other: T.() -> Unit): T.() -> Unit = { this@plus(this); other(this) }


infix operator fun<T, R> ((T, R) -> Unit).plus(other: (T, R) -> Unit): (T, R) -> Unit = { t, r -> this(t, r); other(t, r) }


infix operator fun<T, R, S> ((T, R, S) -> Unit).plus(other: (T, R, S) -> Unit): (T, R, S) -> Unit = { t, r, s -> this(t, r, s); other(t, r, s) }
