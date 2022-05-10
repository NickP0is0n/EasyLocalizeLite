package me.nickp0is0n.easylocalizelite.network

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TranslationResponse(
    @JsonProperty("translatedText") val translatedText: String
)
