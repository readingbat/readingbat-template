package kgroup1

// @desc Description of **combine()**

fun List<String>.combine(): String = mapIndexed { i, s -> i.toString() + s }.joinToString(", ")

fun main() {
  println(listOf("a").combine())
  println(listOf("a", "b", "c", "d").combine())
}