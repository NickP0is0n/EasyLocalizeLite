package me.nickp0is0n.easylocalizelite;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import me.nickp0is0n.easylocalizelite.network.QueryClient;
import me.nickp0is0n.easylocalizelite.network.TranslationRequest;
import me.nickp0is0n.easylocalizelite.ui.MainController;
import me.nickp0is0n.easylocalizelite.ui.MainForm;
import me.nickp0is0n.easylocalizelite.utils.AppInfo;
import me.nickp0is0n.easylocalizelite.utils.MacFileHandler;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class EasyLocalize {
    public static void main(String[] args) {
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        var macFileHandler = new MacFileHandler();
        try {
            Desktop.getDesktop().setOpenFileHandler(macFileHandler);
        } catch (Exception ignored) {}
        FlatOneDarkIJTheme.setup();
        MainForm form = new MainForm();
        form.setSize(640, 480);
        form.setTitle(AppInfo.INSTANCE.getWindowTitle());
        MainController controller = new MainController(form);
        if (macFileHandler.openedFiles != null) {
            controller.setAssociatedFile(macFileHandler.openedFiles.get(0));
        }
        controller.run();
    }
}
