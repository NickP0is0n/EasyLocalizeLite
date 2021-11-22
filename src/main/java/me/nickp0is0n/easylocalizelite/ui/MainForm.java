package me.nickp0is0n.easylocalizelite.ui;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

public class MainForm extends JFrame{
    private JPanel panel1;
    private JList stringIdList;
    private JTextArea stringArea;
    private JTextArea commentArea;
    private JButton exportTranslationsToFileButton;
    private JButton copyStringToClipboardButton;

    public MainForm() {
        setContentPane(panel1);
        setVisible(true);
    }

    public void setStringIDList(ListModel<String> model) {
        stringIdList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        stringIdList.setLayoutOrientation(JList.VERTICAL);
        stringIdList.setModel(model);
    }

    public void setStringIDListListener(ListSelectionListener listener) {
        stringIdList.addListSelectionListener(listener);
    }

    public void setStringAreaText(String text) {
        stringArea.setText(text);
    }

    public void setCommentAreaText(String text) {
        commentArea.setText(text);
    }

    public int getStringIDListCurrentSelectionIndex() {
        return stringIdList.getSelectedIndex();
    }
}
