package me.nickp0is0n.easylocalizelite.utils

import javax.swing.JFrame
import javax.swing.JOptionPane

class DialogNotificationSender(): NotificationSender {
    override fun send(title: String, text: String) {
        JOptionPane.showMessageDialog(JFrame(), text, title, JOptionPane.INFORMATION_MESSAGE);
    }
}