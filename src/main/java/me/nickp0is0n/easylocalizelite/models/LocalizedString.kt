package me.nickp0is0n.easylocalizelite.models

import java.io.Serializable

data class LocalizedString constructor(
    val id: String,
    val text: String,
    val comment: String,
    val isCommentMultilined: Boolean = false,
    val mark: String? = null,
    val copyrightHeader: String? = null
) : Serializable {
    companion object {
        private const val serialVersionUID = 20322616434400L
    }

    override fun toString(): String {
        val rawLocalizedStringBuilder = StringBuilder()
        if (comment.isNotEmpty()) {
            if (isCommentMultilined) {
                rawLocalizedStringBuilder
                    .append("/*")
                    .append(comment.trim { it <= ' ' })
                    .append("*/")
                    .append("\n")
            } else {
                val commentStrings = listOf(*comment.split("\n").toTypedArray())
                commentStrings.forEach {
                    rawLocalizedStringBuilder
                        .append("// ")
                        .append(it.replace("\n", "\n// "))
                        .append("\n")
                }
            }
        }
        rawLocalizedStringBuilder
            .append("\"")
            .append(id)
            .append("\" = \"")
            .append(text)
            .append("\";")
        return rawLocalizedStringBuilder.toString()
    }
}
