package me.nickp0is0n.easylocalizelite.ui;

import me.nickp0is0n.easylocalizelite.utils.CustomOptions;

import javax.swing.*;
import java.awt.event.*;

public class LibreTranslateCustomUrlDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField urlTextField;

    public LibreTranslateCustomUrlDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(650, 150);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (urlTextField.getText().isEmpty() || urlTextField.getText().isBlank()) {
            CustomOptions.INSTANCE.setCustomUrlUsedForLibreTranslate(false);
            CustomOptions.INSTANCE.setCustomLibreTranslateUrl("");
        }
        else {
            CustomOptions.INSTANCE.setCustomUrlUsedForLibreTranslate(true);
            CustomOptions.INSTANCE.setCustomLibreTranslateUrl(urlTextField.getText());
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        LibreTranslateCustomUrlDialog dialog = new LibreTranslateCustomUrlDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
