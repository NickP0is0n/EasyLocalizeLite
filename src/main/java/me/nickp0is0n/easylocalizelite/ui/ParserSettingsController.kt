package me.nickp0is0n.easylocalizelite.ui

import me.nickp0is0n.easylocalizelite.models.ParserSettings
import java.awt.event.ItemEvent

class ParserSettingsController(var form: ParserSettingsForm, val settings: ParserSettings) {
    fun run() {
        form.setIgnoreCommentsCheckBoxOnEditListener(onIgnoreCommentsChange)
        form.setIgnoreCopyrightHeaderCheckBoxOnEditListener(onIgnoreCopyrightHeaderChange)
    }

    val onIgnoreCommentsChange = fun(e: ItemEvent) {
        settings.ignoreComments = e.stateChange != 2 // if stateChange is 2, it means "unselected"
    }

    val onIgnoreCopyrightHeaderChange = fun(e: ItemEvent) {
        settings.ignoreCopyrightHeader = e.stateChange != 2 // if stateChange is 2, it means "unselected"
    }
}