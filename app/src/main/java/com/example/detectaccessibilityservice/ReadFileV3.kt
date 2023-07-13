package com.scb.phone.dexguardfilter

import java.io.File
import java.io.FileWriter

enum class FilterClass(private val regex: Regex) {
    ViewHolder("^(com.*ViewHolder) -> (.*):$".toRegex()),
    Activity("^(com.*Activity) -> (.*):\$".toRegex()),
    Fragment("^(com.*Fragment) -> (.*):\$".toRegex());

    fun match(input: CharSequence): Boolean {
        return regex.matches(input)
    }

    fun matchGroupDifference(input: CharSequence): Boolean {
        return regex.matchEntire(input)?.let { result ->
            val before = result.groupValues[1]
            val after = result.groupValues[2]
            before != after
        } ?: false
    }
}

fun main() {
    val path = "/Users/thanessriamornruttanakul/AndroidStudioProjects/detectAccessibilityService/app/src/main/java/com/example/detectaccessibilityservice/"
    val inFilename = "mapping.txt"
    val input = readMappingData(pathname = "$path/$inFilename")

    FilterClass.values()
        .map { createFilteredData(input, it) }
        .forEach { writeOutputFile(path, it) }
}

private fun readMappingData(pathname: String): List<String> {
    return File(pathname)
        .inputStream()
        .bufferedReader()
        .readLines()
}

private fun createFilteredData(
    input: List<String>,
    by: FilterClass
): Pair<String, String> {
    val filtered = input
        .filter { by.matchGroupDifference(it) }
        .joinToString("\n") { it.trim() }

    return by.name to filtered
}

private fun writeOutputFile(path: String, output: Pair<String, String>) {
    val (name, data) = output

    val outDir = File("$path/filtered/")
    outDir.mkdirs()

    val outPath = "${outDir.absolutePath}/$name.txt"

    FileWriter(outPath).use { it.write(data) }
}
