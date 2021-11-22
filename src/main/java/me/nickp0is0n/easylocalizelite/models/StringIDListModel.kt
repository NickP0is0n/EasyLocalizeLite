package me.nickp0is0n.easylocalizelite.models

import javax.swing.DefaultListModel
import javax.swing.ListModel
import javax.swing.event.ListDataListener

class StringIDListModel: DefaultListModel<String>() {
    var list = mutableListOf<LocalizedString>()

    fun setElements(stringList: List<LocalizedString>) {
        this.list = stringList as MutableList<LocalizedString>
        fireContentsChanged(this, 0, list.size)
    }

    override fun getSize(): Int {
        return list.size
    }

    override fun getElementAt(index: Int): String {
        return list[index].id
    }
}