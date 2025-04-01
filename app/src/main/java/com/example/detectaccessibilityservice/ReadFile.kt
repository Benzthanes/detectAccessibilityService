package com.example.detectaccessibilityservice

//import com.google.auth.oauth2.GoogleCredentials
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.util.Arrays


fun main() {
//    println("token:"+getAccessToken())
    val inputStream: InputStream =
        File("/Users/thanessriamornruttanakul/StudioProjects/detectAccessibilityService/app/src/main/java/com/example/detectaccessibilityservice/mapping.txt").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }
    val outputViewHolder =
        lineList.filter {
            it.contains("ViewHolder ->")
                    && (it.split(" -> ")[0].trim() != it.split(" -> ")[1].trim().dropLast(1))
        }
    val outputActivity =
        lineList.filter {
            it.contains("Activity ->") ||  it.contains("Activity ->")
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

    FileWriter("/Users/thanessriamornruttanakul/StudioProjects/detectAccessibilityService/app/src/main/java/com/example/detectaccessibilityservice/output_3_86_0.txt").use {
        it.write(
            text
        )
    }


}

//@Throws(IOException::class)
//private fun getAccessToken(): String {
//    val googleCredentials =
//        GoogleCredentials.fromStream(FileInputStream("/Users/thanessriamornruttanakul/StudioProjects/detectAccessibilityService/app/fasteasy-5788b-firebase-adminsdk-i0gvq-24a42e3b87.json")).createScoped(
//            Arrays.asList("https://www.googleapis.com/auth/firebase.messaging")
//        )
//    googleCredentials.refresh()
//    return googleCredentials.accessToken.tokenValue
//}