package com.gmail.robmadeyou.compost.actions;

import com.gmail.robmadeyou.compost.dialogs.AddNewLeafDialog;
import com.gmail.robmadeyou.compost.util.CompostFileManagementUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.WindowManager;

import java.util.HashMap;

public class AddLeaf extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        AddNewLeafDialog dialog = new AddNewLeafDialog();

        VirtualFile file = PlatformDataKeys.VIRTUAL_FILE.getData(e.getDataContext());

        dialog.setOnOkPressed(new AddNewLeafDialog.OkEvent() {
            @Override
            public void onOK() {
                if (file != null) {
                    HashMap<String, Object> current = (HashMap<String, Object>) this.values.clone();

                    for (String name : this.values.keySet()) {
                        try {
                            if (name.equals("ViewBridge")) {
                                CompostFileManagementUtil.createJSFileFromTemplate(e.getProject(), file, name, (String) current.get(name), current);
                            } else {
                                CompostFileManagementUtil.createPhpFileFromTemplate(e.getProject(), file, name, (String) current.get(name), current);
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
            }
        });

        dialog.setLocationRelativeTo(WindowManager.getInstance().getFrame(e.getProject()));
        dialog.setTitle("Add a new Leaf set");
        dialog.pack();
        dialog.setVisible(true);
    }
}
