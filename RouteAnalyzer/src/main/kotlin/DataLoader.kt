package org.example

import java.io.File

/**
 * Reads a CSV file and returns a list of lists of strings.
 * @param path the path to the CSV file
 * @return a list of lists of strings
 */
fun readCsv(path: String): List<List<String>> {
    val file = File(path)
    val list = mutableListOf<List<String>>()
    file.forEachLine { line ->
        val row = line.split(";")
        list.add(row)
    }
    return list
}


/**
 * Reads a YAML file and returns a map of strings to any.
 * @param path the path to the YAML file
 * @return a map of strings to any
 */
fun readYaml(path: String): Map<String, Any?> {
    val file = File(path)
    val map = mutableMapOf<String, Any>()
    file.forEachLine { line ->
        val row = line.split(":")
        map[row[0]] = row[1]
    }
    return map
}