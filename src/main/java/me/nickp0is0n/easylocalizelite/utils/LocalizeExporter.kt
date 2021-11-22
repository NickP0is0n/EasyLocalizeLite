package me.nickp0is0n.easylocalizelite.utils

import me.nickp0is0n.easylocalizelite.models.LocalizedString
import java.io.File
import java.io.PrintWriter

class LocalizeExporter {
    fun toFile(localizedStrings: List<LocalizedString>, outputFile: File) {
        val writer = PrintWriter(outputFile)
        var lastMark: String? = null

        writer.use {
            localizedStrings.forEach {
                if (it.copyrightHeader != null) {
                    writer.println("/*${it.copyrightHeader}*/") // writing xcode copyright header on top
                }
                if (it.mark != lastMark && it.mark != null) {
                    lastMark = it.mark
                    writer.println("\n// MARK:${it.mark}\n")
                }
                writer.println(it)
            }
        }
    }
}