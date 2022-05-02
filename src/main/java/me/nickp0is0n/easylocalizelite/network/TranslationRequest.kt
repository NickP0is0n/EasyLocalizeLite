package me.nickp0is0n.easylocalizelite.network

data class TranslationRequest(
    val q: String,
    val source: String = "auto",
    val target: String
)
