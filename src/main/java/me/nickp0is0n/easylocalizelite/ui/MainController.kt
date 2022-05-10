package me.nickp0is0n.easylocalizelite.ui

import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import com.neovisionaries.i18n.LanguageCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.nickp0is0n.easylocalizelite.models.LocalizedString
import me.nickp0is0n.easylocalizelite.models.ParserSettings
import me.nickp0is0n.easylocalizelite.models.StringIDListModel
import me.nickp0is0n.easylocalizelite.network.QueryClient
import me.nickp0is0n.easylocalizelite.network.ResponseFromJSONConverter
import me.nickp0is0n.easylocalizelite.network.TranslationRequest
import me.nickp0is0n.easylocalizelite.utils.AppInfo
import me.nickp0is0n.easylocalizelite.utils.DialogNotificationSender
import me.nickp0is0n.easylocalizelite.utils.LocalizeExporter
import me.nickp0is0n.easylocalizelite.utils.LocalizeParser
import java.awt.FileDialog
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.ActionEvent
import java.io.*
import java.net.SocketException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

class MainController(val form: MainForm) {
    private val list = hashMapOf(Pair("Original", mutableListOf(LocalizedString("No file is currently loaded.", "", ""))))
        //mutableListOf(LocalizedString("No file is currently loaded.", "", ""))
    private var currentSaveFile: File? = null
    private val parserSettings = ParserSettings()
    private val parser = LocalizeParser(parserSettings)
    private val model = StringIDListModel()
    private var autoTranslationEnabled = false
    var associatedFile: File? = null

    fun run() {
        model.setElements(list[form.currentLanguage]!!.filter {
            it.id.contains(form.searchBarText, ignoreCase = true) || it.text.contains(form.searchBarText, ignoreCase = true) || it.comment.contains(form.searchBarText, ignoreCase = true)
        })
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
        form.setSearchButtonOnClickListener(onSearchButtonClick)
        form.setSearchBarOnEditListener(onSearchBarEdit)
        form.setParserSettingsMenuItemOnClickListener(onParserSettingsItemClick)
        form.setSelectLanguageButtonOnClickListener(onSelectLanguageButtonClick)
        form.setAddNewLanguageButtonOnClickListener(onAddNewLanguageButtonClick)
        form.setLanguageSelectorListener(onLanguageSelectorValueChange)
        form.setEnableTranslationsMenuItemOnClickListener(onEnableAutoTranslationItemClick)
        form.setTranslateButtonOnClickListener(onTranslationButtonClick)

        if (associatedFile != null) {
            showFileContent(associatedFile!!)
        }
    }

    private val onOpenItemClick = fun(_: ActionEvent) {
        val openDialog = FileDialog(form)
        openDialog.isVisible = true
        currentSaveFile = null
        if (openDialog.files.isEmpty()) {
            form.setTitle(AppInfo.windowTitle)
            form.resetLanguageSelector()
            list.also {
                it.clear()
                it["Original"] = mutableListOf()
            }["Original"]!!.add(LocalizedString("No file is currently loaded.", "", ""))
        }
        else {
            showFileContent(openDialog.files[0])
        }
        model.setElements(list["Original"]!!)
    }

    private fun showFileContent(currentFile: File) {
        form.setTitle(AppInfo.windowTitle + " – " + currentFile.name)
        if (currentFile.extension == "elproject") {
            currentSaveFile = currentFile
            ObjectInputStream(FileInputStream(currentSaveFile!!)).use {
                val rawSaveData = it.readObject()
                list.clear()
                if (rawSaveData is MutableList<*>) { //backwards compatibility with 0.3.* and under
                    list["Original"] = (rawSaveData as MutableList<LocalizedString>).toMutableList()
                } else {
                    list.putAll(rawSaveData as Map<String, MutableList<LocalizedString>>)
                }
                form.setLanguageSelectorContent(ArrayList(list.keys.toList()))
            }
        } else {
            val stringFile = currentFile
            list.also {
                it.clear()
                it["Original"] = mutableListOf()
            }["Original"]!!.addAll(
                try {
                    parser.fromFile(stringFile)
                } catch (e: IOException) {
                    listOf(LocalizedString("No file loaded", "", ""))
                }
            )
        }
    }

    private val onSelectLanguageButtonClick = fun(_: ActionEvent) {
        form.switchLanguageSelectionVisibility()
    }

    private val onLanguageSelectorValueChange = fun() {
        model.setElements(list[form.currentLanguage]!!.filter {
            it.id.contains(form.searchBarText, ignoreCase = true) || it.text.contains(form.searchBarText, ignoreCase = true) || it.comment.contains(form.searchBarText, ignoreCase = true)
        })
        if (form.stringIDListCurrentSelectionIndex != -1) {
            form.setStringAreaText(model.list[form.stringIDListCurrentSelectionIndex].text)
            form.setCommentAreaText(model.list[form.stringIDListCurrentSelectionIndex].comment)
        }
    }

    private val onAddNewLanguageButtonClick = fun(_: ActionEvent) {
        val newLanguageForm = NewLanguageForm()
        if (newLanguageForm.languageTitle != null) {
            list[newLanguageForm.languageTitle] = list["Original"]!!.toMutableList()
            form.setLanguageSelectorContent(list.keys.toList() as ArrayList<String>)
        }
    }

    private val onSaveAsItemClick = fun(_: ActionEvent) {
        val saveDialog = FileDialog(form)
        saveDialog.mode = FileDialog.SAVE
        saveDialog.file = "*.elproject"
        saveDialog.isVisible = true
        if (saveDialog.files.isNotEmpty()) {
            currentSaveFile = saveDialog.files[0]
            saveProjectFile()
            form.setTitle(AppInfo.windowTitle + " – " + saveDialog.files[0].name)
        }
    }

    private val onExportButtonClick = fun(_: ActionEvent) {
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
            list.keys.forEach {
                val saveFile = File("${exportFile.absolutePath.removeSuffix(".${exportFile.extension}")}_$it.${exportFile.extension}")
                if (!saveFile.exists()) {
                    saveFile.createNewFile()
                }
                exporter.toFile(list[it]!!, saveFile)
            }

            val notificator = DialogNotificationSender()
            notificator.send("Success", "Localization files have been successfully exported.")
        }
    }

    private val onCopyButtonClick = fun(_: ActionEvent) {
        val selection = StringSelection(list[form.currentLanguage]!![form.stringIDListCurrentSelectionIndex].toString())
        Toolkit.getDefaultToolkit().systemClipboard.setContents(selection, null)
    }

    private val onSearchButtonClick = fun(_: ActionEvent) {
        form.switchSearchBarVisibility()
    }

    private val onSearchBarEdit = fun() {
        model.setElements(list[form.currentLanguage]!!.filter {
            it.id.contains(form.searchBarText, ignoreCase = true) || it.text.contains(form.searchBarText, ignoreCase = true) || it.comment.contains(form.searchBarText, ignoreCase = true)
        })
    }

    private val onParserSettingsItemClick = fun(_: ActionEvent) {
        val psForm = ParserSettingsForm()
        psForm.setSize(250, 100)
        form.title = "Parser Settings"
        val psController = ParserSettingsController(psForm, parserSettings)
        psController.run()
    }

    private val onEnableAutoTranslationItemClick = fun(_: ActionEvent) {
        form.setEnableTranslationsMenuItemName(if (autoTranslationEnabled) "Enable auto-translation (beta)" else "Disable auto-translation")
        autoTranslationEnabled = !autoTranslationEnabled
        form.switchTranslateButtonVisibility()
    }

    private val onTranslationButtonClick = fun(_: ActionEvent) {
        if (autoTranslationEnabled) {
            val queryClient = QueryClient(AppInfo.autoTranslateUrl)
            val request = TranslationRequest(form.stringAreaText, "auto", LanguageCode.findByName(form.currentLanguage)[0].name)
            try {
                val response = ResponseFromJSONConverter().convert(queryClient.doQuery(request))
                if (response != null) {
                    form.setStringAreaText(response.translatedText)
                }
            }
            catch (_: ValueInstantiationException) {
                val notificator = DialogNotificationSender()
                notificator.send("Error", "Current language is not supported at the moment.")
            }
            //TODO: More adequate way to handle absent internet connection
            catch (_: Exception) {
                val notificator = DialogNotificationSender()
                notificator.send("Error", "Connection to translation server could not be established.")
            }
        }
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
        val currentString = model.list[form.stringIDListCurrentSelectionIndex]
        list[form.currentLanguage]!![list[form.currentLanguage]!!.indexOf(list[form.currentLanguage]!!.find { it.id == currentString.id })] = LocalizedString(
            currentString.id,
            form.stringAreaText,
            currentString.comment,
            mark = currentString.mark,
            copyrightHeader = currentString.copyrightHeader
        )
        model.setElements(list[form.currentLanguage]!!.filter {
            it.id.contains(form.searchBarText, ignoreCase = true) || it.text.contains(form.searchBarText, ignoreCase = true) || it.comment.contains(form.searchBarText, ignoreCase = true)
        })
        saveProjectFile()
    }
}