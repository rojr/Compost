package com.gmail.robmadeyou.CompostedManure;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

public class AddLeafDialog extends AnAction
{

    @Override
    public void actionPerformed (AnActionEvent e)
    {
        Messages.showMessageDialog (e.getProject (), "Hello!", "Cool Title!", null);
    }
}
