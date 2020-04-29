package com.audient.sqlitehelper

import kotlinx.coroutines.*
import kotlin.random.Random

data class Student(var name: String, var age: Int)

val student = Student("柯坚", 66)
val students = ArrayList<Student>()

val list = ArrayList<Int>().apply {
    repeat(10000) {
        add(it)
    }
}

fun main() {

    list.parallelStream().forEach {
        student.age = student.age + 1
        students.add(student)
        println(student)
    }
}