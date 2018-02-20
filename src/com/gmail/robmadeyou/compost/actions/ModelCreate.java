package com.gmail.robmadeyou.compost.actions;

import com.gmail.robmadeyou.compost.dialogs.AddNewModelDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.WindowManager;

public class ModelCreate extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        AddNewModelDialog dialog = new AddNewModelDialog();

        VirtualFile file = PlatformDataKeys.VIRTUAL_FILE.getData(e.getDataContext());

        

        dialog.setLocationRelativeTo(WindowManager.getInstance().getFrame(e.getProject()));
        dialog.setTitle("Add a new Model");
        dialog.pack();
        dialog.setVisible(true);
    }
}
