package com.thepyprogrammer.ktlib.io.dummy

class str(var string: String) {
    fun replaceAll(regex: String, replacement: String?) {
        string = string.replace(regex.toRegex(), replacement!!)
    }

    fun replaceFirst(regex: String, replacement: String?) {
        string = string.replaceFirst(regex.toRegex(), replacement!!)
    }

    override fun toString(): String {
        return string
    }
}