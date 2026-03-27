package com.example.a3dmodelsapp

class Person(
    var name: String = "",
    var age: Int = 0,
)

fun main() {

    val person = Person().apply {
        name = "Alice"
        age = 20
    }

    val text: String? = "Hello"
    text?.let{
        println(it.length)
    }

    with(person) {

    }
}