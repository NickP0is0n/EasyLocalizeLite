package me.nickp0is0n.easylocalizelite.utils

import dorkbox.notify.Notify
import dorkbox.notify.Pos
import javax.imageio.ImageIO

class NotifyNotificationSender: NotificationSender {
    override fun send(title: String, text: String) {
        Notify
            .create()
            .title(title)
            .text(text)
            .darkStyle()
            .position(Pos.TOP_RIGHT)
            .image(ImageIO.read(javaClass.classLoader.getResource("success_pic.png")))
            .hideAfter(3000)
            .show()
    }
}