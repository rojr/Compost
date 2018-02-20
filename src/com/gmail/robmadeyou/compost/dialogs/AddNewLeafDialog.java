package com.gmail.robmadeyou.compost.dialogs;

import com.gmail.robmadeyou.compost.listeners.ui.InputEnableCheckbox;

import javax.swing.*;
import java.awt.event.*;

public class AddNewLeafDialog extends InputDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField leafNameTextField;
    private JTextField viewName;
    private JTextField modelName;
    private JCheckBox modelCheckBox;
    private JCheckBox viewCheckBox;
    private JTextField viewBridgeName;
    private JCheckBox viewBridgeCheckBox;

    public AddNewLeafDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction((ActionListener) -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        viewCheckBox.addChangeListener(new InputEnableCheckbox(viewName));
        modelCheckBox.addChangeListener(new InputEnableCheckbox(modelName));
        viewBridgeCheckBox.addChangeListener(new InputEnableCheckbox(viewBridgeName));

        leafNameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                this.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                this.keyPressed(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                String text = textField.getText();

                modelName.setText(text + "Model");
                viewName.setText(text + "View");
                viewBridgeName.setText(text + "ViewBridge");
            }
        });
    }

    private void onOK() {
        if (this.onOkPressed != null) {
            for (JTextField f : new JTextField[]{viewName, modelName, leafNameTextField, viewBridgeName}) {
                if (f.isEnabled()) {
                    this.onOkPressed.values.put(f.getName(), f.getText());
                }
            }
            this.onOkPressed.onOK();
        }
        dispose();
    }
}
