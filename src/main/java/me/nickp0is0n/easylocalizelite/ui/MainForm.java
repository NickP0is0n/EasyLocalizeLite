package me.nickp0is0n.easylocalizelite.ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.util.Locale;

public class MainForm extends JFrame {
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    public void setExportTranslationsToFileButtonOnClickListener(ActionListener listener) {
        exportTranslationsToFileButton.addActionListener(listener);
    }

    public void setCopyStringToClipboardButtonOnClickListener(ActionListener listener) {
        copyStringToClipboardButton.addActionListener(listener);
    }

    public void setStringAreaOnEditListener(Runnable listener) {
        stringArea.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                listener.run();
            }

            public void removeUpdate(DocumentEvent e) {
                listener.run();
            }

            public void insertUpdate(DocumentEvent e) {
                listener.run();
            }
        });
    }

    public String getStringAreaText() {
        return stringArea.getText();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 3, new Insets(5, 10, 5, 10), -1, -1));
        panel1.setPreferredSize(new Dimension(640, 480));
        panel1.setRequestFocusEnabled(false);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, Font.BOLD, 16, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("String ID's");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, Font.BOLD, 16, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("String");
        panel1.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stringArea = new JTextArea();
        stringArea.setLineWrap(true);
        panel1.add(stringArea, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, Font.BOLD, 16, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Comment");
        panel1.add(label3, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        copyStringToClipboardButton = new JButton();
        Font copyStringToClipboardButtonFont = this.$$$getFont$$$(null, -1, 14, copyStringToClipboardButton.getFont());
        if (copyStringToClipboardButtonFont != null)
            copyStringToClipboardButton.setFont(copyStringToClipboardButtonFont);
        copyStringToClipboardButton.setText("Copy string to clipboard");
        panel1.add(copyStringToClipboardButton, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        commentArea = new JTextArea();
        commentArea.setLineWrap(true);
        commentArea.setText("");
        panel1.add(commentArea, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 4, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        stringIdList = new JList();
        scrollPane1.setViewportView(stringIdList);
        exportTranslationsToFileButton = new JButton();
        Font exportTranslationsToFileButtonFont = this.$$$getFont$$$(null, -1, 14, exportTranslationsToFileButton.getFont());
        if (exportTranslationsToFileButtonFont != null)
            exportTranslationsToFileButton.setFont(exportTranslationsToFileButtonFont);
        exportTranslationsToFileButton.setText("Export translations to file...");
        panel1.add(exportTranslationsToFileButton, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
