package me.nickp0is0n.easylocalizelite.models

import javax.swing.ListModel
import javax.swing.event.ListDataListener

class StringIDListModel: ListModel<String> {
    var list = mutableListOf<LocalizedString>()

    fun setElements(stringList: List<LocalizedString>) {
        this.list = stringList as MutableList<LocalizedString>
    }

    override fun getSize(): Int {
        return list.size
    }

    override fun getElementAt(index: Int): String {
        return list[index].id
    }

    override fun addListDataListener(l: ListDataListener?) {
    }

    override fun removeListDataListener(l: ListDataListener?) {
    }
}