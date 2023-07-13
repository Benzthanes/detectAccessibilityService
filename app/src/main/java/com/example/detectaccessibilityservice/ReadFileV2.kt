package com.example.detectaccessibilityservice

import java.io.File
import java.io.FileWriter

fun main() {
    val path = "/Users/thanessriamornruttanakul/AndroidStudioProjects/detectAccessibilityService/app/src/main/java/com/example/detectaccessibilityservice/"
    val inFilename = "mapping.txt"
    val input = readMappingData(pathname = "$path/$inFilename")

    val sections = mapOf(
        "ViewHolder" to "^com.*ViewHolder ->".toRegex(),
        "Activity" to "^com.*Activity ->".toRegex(),
        "Fragment" to "^com.*Fragment ->".toRegex()
    )

    sections
        .map { createFilteredData(input, it) }
        .forEach { createOutputFile(path, it) }
}

private fun readMappingData(pathname: String): List<String> {
    return File(pathname)
        .inputStream()
        .bufferedReader()
        .readLines()
}

private fun createFilteredData(
    input: List<String>,
    section: Map.Entry<String, Regex>
): Pair<String, String> {
    val (name, regex) = section
    val filtered = input
        .filter { regex.containsMatchIn(it) && (it.split(" -> ")[0].trim() != it.split(" -> ")[1].trim().dropLast(1)) }
        .joinToString("\n") { it.trim() }

    return name to filtered
}

private fun createOutputFile(path: String, output: Pair<String, String>) {
    val (name, data) = output

    val outDir = File("$path/filtered/")
    outDir.mkdirs()

    val outPath = "${outDir.absolutePath}/$name.txt"

    FileWriter(outPath)
        .use { it.write(data) }
}
