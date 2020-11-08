package org.jlr

/**
 * Gets the index of the maximum value sitting in an array.
 *
 * Returns null if the values in the array are all identical.
 *
 * @param array [Array] of T
 * @return Index of the maximum value or null if not found
 */
fun <T : Comparable<T>> maxValueIndex(array: Array<T>): Int? {
    return when {
        array.isEmpty() -> null
        array.distinct().all { array[0] == it } -> null
        else -> array.indices.maxBy { array[it] }
    }
}

/**
 * Gets the index of the maximum value sitting in an array.
 *
 * Returns null if the values in the array are all identical.
 *
 * @param list [List] of T
 * @return Index of the maximum value or null if not found
 */
fun <T : Comparable<T>> maxValueIndex(list: List<T>): Int? {
    return when {
        list.isEmpty() -> null
        list.distinct().all { list[0] == it } -> null
        else -> list.indices.maxBy { list[it] }
    }
}

/**
 * Gets the index of the maximum value sitting in an array.
 *
 * Returns null if the values in the array are all identical.
 *
 * @param array A [FloatArray]
 * @return Index of the maximum value or null if not found
 */
fun maxValueIndex(array: FloatArray): Int? {
    return when {
        array.isEmpty() -> null
        array.distinct().all { array[0] == it } -> null
        else -> array.indices.maxBy { array[it] }
    }
}

/**
 * Gets the index of the maximum value sitting in an array.
 *
 * Returns a default if the values in the array are all identical.
 *
 * @param array A [FloatArray]
 * @return Index of the maximum value or null if not found
 */
fun nonNullMaxValueIndex(array: FloatArray, default: Int = 0): Int {
    return array.indices.maxBy { array[it] } ?: default
}

/**
 * Tail extension for [List].
 */
fun <T> List<T>.tail() = drop(1)

/**
 * Plus operator extension for prepending atomic element to a [[ArrayList]].
 */
operator fun <T> T.plus(tail: ArrayList<T>): ArrayList<T> {
    val list = ArrayList<T>(1 + tail.size)
    list.add(this)
    list.addAll(tail)
    return list
}

/**
 * Tuple helpers.
 */
data class Tuple1<out A>(val _1: A)
data class Tuple2<out A, out B>(val _1: A, val _2: B)
data class Tuple3<out A, out B, out C>(val _1: A, val _2: B, val _3: C)
data class Tuple4<out A, out B, out C, out D>(val _1: A, val _2: B, val _3: C, val _4: D)
data class Tuple5<out A, out B, out C, out D, out E>(val _1: A, val _2: B, val _3: C, val _4: D, val _5: E)
data class Tuple6<out A, out B, out C, out D, out E, out F>(val _1: A, val _2: B, val _3: C, val _4: D, val _5: E, val _6: F)

object Tuple {
    operator fun <A> invoke(_1: A): Tuple1<A> = Tuple1(_1)
    operator fun <A, B> invoke(_1: A, _2: B): Tuple2<A, B> = Tuple2(_1, _2)
    operator fun <A, B, C> invoke(_1: A, _2: B, _3: C): Tuple3<A, B, C> = Tuple3(_1, _2, _3)
    operator fun <A, B, C, D> invoke(_1: A, _2: B, _3: C, _4: D): Tuple4<A, B, C, D> = Tuple4(_1, _2, _3, _4)
    operator fun <A, B, C, D, E> invoke(_1: A, _2: B, _3: C, _4: D, _5: E): Tuple5<A, B, C, D, E> = Tuple5(_1, _2, _3, _4, _5)
    operator fun <A, B, C, D, E, F> invoke(_1: A, _2: B, _3: C, _4: D, _5: E, _6: F): Tuple6<A, B, C, D, E, F> = Tuple6(_1, _2, _3, _4, _5, _6)
}
