package com.gmail.robmadeyou.compost.dialogs;

import com.gmail.robmadeyou.compost.listeners.ui.InputEnableCheckbox;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;

public class AddNewLeafDialog extends JDialog {
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

    private Project project;

    private AddNewLeafDialogEvent onOkPressed;

    public AddNewLeafDialog(Project project) {
        this.project = project;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

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

    public AddNewLeafDialog setOnOkPressed(AddNewLeafDialogEvent event)
    {
        this.onOkPressed = event;

        return this;
    }

    private void onOK() {
        if (this.onOkPressed != null) {
            this.onOkPressed.raise(viewName, modelName, leafNameTextField, viewBridgeName);
            this.onOkPressed.onOK();
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public abstract static class AddNewLeafDialogEvent {

        public HashMap<String,String> values = new HashMap<>();

        public abstract void onOK();

        public void raise(JTextField view, JTextField model, JTextField leaf, JTextField bridge) {
            for (JTextField f : new JTextField[]{view, model, leaf, bridge}) {
                if (f.isEnabled()) {
                    values.put(f.getName(), f.getText());
                }
            }
        }
    }
}
