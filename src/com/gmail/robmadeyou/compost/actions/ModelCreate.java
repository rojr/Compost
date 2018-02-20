package com.gmail.robmadeyou.compost.actions;

import com.gmail.robmadeyou.compost.dialogs.AddNewModelDialog;
import com.gmail.robmadeyou.compost.dialogs.InputDialog;
import com.gmail.robmadeyou.compost.util.CompostFileManagementUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.WindowManager;

import java.util.HashMap;
import java.util.Map;

public class ModelCreate extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        AddNewModelDialog dialog = new AddNewModelDialog();

        VirtualFile file = PlatformDataKeys.VIRTUAL_FILE.getData(e.getDataContext());

        dialog.setOnOkPressed(new InputDialog.OkEvent() {
            @Override
            public void onOK() {
                HashMap<String, Object> current = (HashMap<String, Object>) this.values.clone();
                try {
                    CompostFileManagementUtil.createPhpFileFromTemplate(e.getProject(), file, "Model", this.values.get("Name"), current);
                } catch (Exception ex) {
                }
            }
        });

        dialog.setLocationRelativeTo(WindowManager.getInstance().getFrame(e.getProject()));
        dialog.setTitle("Add a new Model");
        dialog.pack();
        dialog.setVisible(true);
    }
}
