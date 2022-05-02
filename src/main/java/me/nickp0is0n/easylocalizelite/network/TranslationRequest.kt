package me.nickp0is0n.easylocalizelite.network

import com.fasterxml.jackson.annotation.JsonProperty

data class TranslationRequest(
    @get:JsonProperty("q") val text: String,
    @get:JsonProperty("source") val sourceLanguage: String = "auto",
    @get:JsonProperty("target") val targetLanguage: String
)
