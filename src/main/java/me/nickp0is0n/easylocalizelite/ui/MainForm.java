package me.nickp0is0n.easylocalizelite.ui;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;

public class MainForm extends JFrame{
    private JPanel panel1;
    private JList stringIdList;
    private JTextArea stringArea;
    private JTextArea commentArea;
    private JButton exportTranslationsToFileButton;
    private JButton copyStringToClipboardButton;

    private JMenuBar menuBar;
    private JMenu fileMenu, toolsMenu;
    private JMenuItem openMenuItem, saveAsMenuItem, parserSettingsMenuItem;

    public MainForm() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        openMenuItem = new JMenuItem("Open...");
        saveAsMenuItem = new JMenuItem("Save project as...");
        fileMenu.add(openMenuItem);
        fileMenu.add(saveAsMenuItem);

        toolsMenu = new JMenu("Tools");
        parserSettingsMenuItem = new JMenuItem("Parser settings");
        toolsMenu.add(parserSettingsMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);

        setJMenuBar(menuBar);
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

    public void setOpenMenuItemOnClickListener(ActionListener listener) {
        openMenuItem.addActionListener(listener);
    }

    public void setSaveAsMenuItemOnClickListener(ActionListener listener) {
        saveAsMenuItem.addActionListener(listener);
    }

    public void setParserSettingsMenuItemOnClickListener(ActionListener listener) {
        parserSettingsMenuItem.addActionListener(listener);
    }
}
