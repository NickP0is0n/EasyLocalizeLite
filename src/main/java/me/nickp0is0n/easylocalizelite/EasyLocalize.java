package me.nickp0is0n.easylocalizelite;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import me.nickp0is0n.easylocalizelite.ui.MainController;
import me.nickp0is0n.easylocalizelite.ui.MainForm;

public class EasyLocalize {
    public static void main(String[] args) {
        FlatOneDarkIJTheme.setup();
        MainForm form = new MainForm();
        form.setSize(640, 480);
        form.setTitle("EasyLocalize Lite 0.3.0 Early build");
        MainController controller = new MainController(form);
        controller.run();
    }
}
