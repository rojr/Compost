package com.gmail.robmadeyou.compost.listeners.ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class InputEnableCheckbox implements ChangeListener {

    private JTextField toEnable;
    public InputEnableCheckbox(JTextField toEnable) {
        super();

        this.toEnable = toEnable;
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        this.toEnable.setEnabled(((JCheckBox)changeEvent.getSource()).getModel().isSelected());
    }
}

