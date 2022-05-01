package me.nickp0is0n.easylocalizelite.utils

import java.awt.desktop.OpenFilesEvent
import java.awt.desktop.OpenFilesHandler
import java.io.File

class MacFileHandler: OpenFilesHandler {
    lateinit var openedFiles: List<File>

    override fun openFiles(e: OpenFilesEvent?) {
        openedFiles = e?.files!!
    }

}