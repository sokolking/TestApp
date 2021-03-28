package com.test.base.data

interface Transformable<T> {

    fun transform(): T
}

fun <T> List<Transformable<T>>.transform(): List<T> =
    map { it.transform() }