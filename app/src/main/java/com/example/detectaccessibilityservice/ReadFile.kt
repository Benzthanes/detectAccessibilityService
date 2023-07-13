package com.example.detectaccessibilityservice

import java.io.File
import java.io.FileWriter
import java.io.InputStream

fun main() {
    val inputStream: InputStream =
        File("/Users/thanessriamornruttanakul/AndroidStudioProjects/detectAccessibilityService/app/src/main/java/com/example/detectaccessibilityservice/mapping_issue.txt").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }
    val outputViewHolder =
        lineList.filter {
            it.contains("ViewHolder ->")
                    && (it.split(" -> ")[0].trim() != it.split(" -> ")[1].trim().dropLast(1))
        }
    val outputActivity =
        lineList.filter {
            it.contains("Activity ->")
                    && (it.split(" -> ")[0].trim() != it.split(" -> ")[1].trim().dropLast(1))
        }
    val outputFragment =
        lineList.filter {
            it.contains("Fragment ->")
                    && (it.split(" -> ")[0].trim() != it.split(" -> ")[1].trim().dropLast(1))
        }

    var text = ""
    text += "\n\n"
    text += "ViewHolder -----------------\n"
    outputViewHolder.forEach {
        text = text + it.trim() + "\n"
        println(it)
    }
    text += "\n\n"
    text += "Activity -----------------\n"
    outputActivity.forEach {
        text = text + it.trim() + "\n"
        println(it)
    }

    text += "\n\n"
    text += "Fragment -----------------\n"

    outputFragment.forEach {
        text = text + it.trim() + "\n"
        println(it)
    }

    FileWriter("/Users/thanessriamornruttanakul/AndroidStudioProjects/detectAccessibilityService/app/src/main/java/com/example/detectaccessibilityservice/output.txt").use {
        it.write(
            text
        )
    }


}