package me.nickp0is0n.easylocalizelite;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import me.nickp0is0n.easylocalizelite.models.ParserSettings;
import me.nickp0is0n.easylocalizelite.ui.MainController;
import me.nickp0is0n.easylocalizelite.ui.MainForm;
import me.nickp0is0n.easylocalizelite.ui.ParserSettingsController;
import me.nickp0is0n.easylocalizelite.ui.ParserSettingsForm;
import me.nickp0is0n.easylocalizelite.utils.AppInfo;

public class EasyLocalize {
    public static void main(String[] args) {
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        FlatOneDarkIJTheme.setup();
        MainForm form = new MainForm();
        form.setSize(640, 480);
        form.setTitle(AppInfo.INSTANCE.getWindowTitle());
        MainController controller = new MainController(form);
        controller.run();
    }
}
