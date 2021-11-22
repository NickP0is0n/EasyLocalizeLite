package me.nickp0is0n.easylocalizelite.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.nickp0is0n.easylocalizelite.models.LocalizedString
import me.nickp0is0n.easylocalizelite.models.ParserSettings
import me.nickp0is0n.easylocalizelite.models.StringIDListModel
import me.nickp0is0n.easylocalizelite.utils.AppInfo
import me.nickp0is0n.easylocalizelite.utils.DialogNotificationSender
import me.nickp0is0n.easylocalizelite.utils.LocalizeExporter
import me.nickp0is0n.easylocalizelite.utils.LocalizeParser
import java.awt.FileDialog
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.ActionEvent
import java.io.*

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
        form.setSaveAsMenuItemOnClickListener(onSaveAsItemClick)
        form.setStringAreaOnEditListener(onStringEdit)
        form.setExportTranslationsToFileButtonOnClickListener(onExportButtonClick)
        form.setCopyStringToClipboardButtonOnClickListener(onCopyButtonClick)
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
        else {
            form.setTitle(AppInfo.windowTitle + " – " + openDialog.files[0].name)
            if (openDialog.files[0].extension == "elproject") {
                currentSaveFile = openDialog.files[0]
                ObjectInputStream(FileInputStream(currentSaveFile!!)).use {
                    list.also {
                        it.clear()
                    }.addAll(it.readObject() as List<LocalizedString>)
                }
            }
            else {
                currentSaveFile = null
                val stringFile = openDialog.files[0]
                list.also {
                    it.clear()
                }.addAll(try { parser.fromFile(stringFile) } catch (e: IOException) { listOf(LocalizedString("No file loaded", "", ""))})
            }
        }
        model.setElements(list)
    }

    val onSaveAsItemClick = fun(_: ActionEvent) {
        val saveDialog = FileDialog(form)
        saveDialog.mode = FileDialog.SAVE
        saveDialog.file = "*.elproject"
        saveDialog.isVisible = true
        if (saveDialog.files.isNotEmpty()) {
            currentSaveFile = saveDialog.files[0]
            saveProjectFile()
        }
    }

    val onExportButtonClick = fun(_: ActionEvent) {
        val exporter = LocalizeExporter()

        val openDialog = FileDialog(form)
        openDialog.mode = FileDialog.SAVE
        openDialog.isVisible = true

        val exportFile = try {
            openDialog.files[0]
        }
        catch (e: ArrayIndexOutOfBoundsException) {
            null
        }

        if (exportFile != null) {
            if (!exportFile.exists()) {
                exportFile.createNewFile()
            }
            exporter.toFile(list, exportFile)
            val notificator = DialogNotificationSender()
            notificator.send("Success", "Localization file has been successfully exported.")
        }
    }

    val onCopyButtonClick = fun(_: ActionEvent) {
        val selection = StringSelection(list[form.stringIDListCurrentSelectionIndex].toString())
        Toolkit.getDefaultToolkit().systemClipboard.setContents(selection, null)
    }

    private fun saveProjectFile() {
        CoroutineScope(Dispatchers.IO).launch {
            writeToProjectFile()
        }
    }

    private suspend fun writeToProjectFile() = withContext(Dispatchers.IO) {
        try {
            if (currentSaveFile != null) {
                ObjectOutputStream(FileOutputStream(currentSaveFile!!)).use {
                    it.writeObject(list)
                }
            }
        }
        catch (e: IOException) {
            e.printStackTrace()
        }
    }

    val onStringEdit = fun() {
        val currentString = list[form.stringIDListCurrentSelectionIndex]
        list[form.stringIDListCurrentSelectionIndex] = LocalizedString(
            currentString.id,
            form.stringAreaText,
            currentString.comment,
            mark = currentString.mark,
            copyrightHeader = currentString.copyrightHeader
        )
        saveProjectFile()
    }
}