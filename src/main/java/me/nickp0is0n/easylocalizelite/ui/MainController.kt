package me.nickp0is0n.easylocalizelite.ui

import me.nickp0is0n.easylocalizelite.models.LocalizedString
import me.nickp0is0n.easylocalizelite.models.StringIDListModel
import me.nickp0is0n.easylocalizelite.ui.MainForm

class MainController(val form: MainForm) {
    val list = mutableListOf(LocalizedString("No file is currently loaded.", "", ""))

    fun run() {
        val model = StringIDListModel()
        model.setElements(list)
        form.setStringIDList(model)
        form.setStringIDListListener {
            form.setStringAreaText(model.list[form.stringIDListCurrentSelectionIndex].text)
            form.setCommentAreaText(model.list[form.stringIDListCurrentSelectionIndex].comment)
        }
    }
}