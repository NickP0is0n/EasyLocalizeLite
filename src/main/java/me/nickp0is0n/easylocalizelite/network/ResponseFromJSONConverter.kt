package me.nickp0is0n.easylocalizelite.network

import com.fasterxml.jackson.databind.ObjectMapper

class ResponseFromJSONConverter {
    fun convert(rawJson: String?): TranslationResponse? {
        val mapper = ObjectMapper()
        return mapper.readValue(rawJson, TranslationResponse::class.java)
    }
}