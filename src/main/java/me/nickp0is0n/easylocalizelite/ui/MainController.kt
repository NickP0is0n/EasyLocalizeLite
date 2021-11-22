package me.nickp0is0n.easylocalizelite.ui

import me.nickp0is0n.easylocalizelite.models.LocalizedString
import me.nickp0is0n.easylocalizelite.models.ParserSettings
import me.nickp0is0n.easylocalizelite.models.StringIDListModel
import me.nickp0is0n.easylocalizelite.utils.AppInfo
import me.nickp0is0n.easylocalizelite.utils.LocalizeParser
import java.awt.FileDialog
import java.awt.event.ActionEvent
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.ObjectInputStream

class MainController(val form: MainForm) {
    val list = mutableListOf(LocalizedString("No file is currently loaded.", "", ""))
    var currentSaveFile: File? = null
    val parserSettings = ParserSettings()
    val parser = LocalizeParser(parserSettings)
    val model = StringIDListModel()

    fun run() {
        model.setElements(list)
        form.setStringIDList(model)
        form.setStringIDListListener {
            form.setStringAreaText(model.list[form.stringIDListCurrentSelectionIndex].text)
            form.setCommentAreaText(model.list[form.stringIDListCurrentSelectionIndex].comment)
        }

        form.setOpenMenuItemOnClickListener(onOpenItemClick)
    }

    val onOpenItemClick = fun(_: ActionEvent) {
        val openDialog = FileDialog(form)
        openDialog.isVisible = true
        if (openDialog.files.isEmpty()) {
            form.setTitle(AppInfo.windowTitle)
            list.also {
                it.clear()
            }.add(LocalizedString("No file is currently loaded.", "", ""))
        }
        form.setTitle(AppInfo.windowTitle + " – " + openDialog.files[0].name)
        if (openDialog.files[0].extension == "elproject") {
            currentSaveFile = openDialog.files[0]
            ObjectInputStream(FileInputStream(currentSaveFile!!)).use {
                list.also {
                    it.clear()
                }.addAll(it.readObject() as List<LocalizedString>)
            }
        }

        val stringFile = openDialog.files[0]
        list.also {
            it.clear()
        }.addAll(try { parser.fromFile(stringFile) } catch (e: IOException) { listOf(LocalizedString("No file loaded", "", ""))})
        model.setElements(list)
    }
}