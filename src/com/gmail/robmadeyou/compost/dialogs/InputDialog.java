package com.gmail.robmadeyou.compost.dialogs;

import javax.swing.*;
import java.util.HashMap;

public class InputDialog extends JDialog {

    protected OkEvent onOkPressed;

    public InputDialog setOnOkPressed(OkEvent event)
    {
        this.onOkPressed = event;

        return this;
    }

    protected void onCancel() {
        dispose();
    }

    public abstract static class OkEvent {

        public HashMap<String,String> values = new HashMap<>();

        public abstract void onOK();
    }
}
