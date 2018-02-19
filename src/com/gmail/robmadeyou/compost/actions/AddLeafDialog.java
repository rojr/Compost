package com.gmail.robmadeyou.compost.actions;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class AddLeafDialog extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
       /* AddNewLeafDialog dialog = new AddNewLeafDialog();

        VirtualFile file = PlatformDataKeys.VIRTUAL_FILE.getData(e.getDataContext());

        dialog.setOnOkPressed(new AddNewLeafDialog.AddNewLeafDialogEvent() {
            @Override
            public void onOK() {
                if (file != null) {
                    HashMap<String, String> current = (HashMap<String, String>) this.values.clone();
                    for (String name : this.values.keySet()) {
                        try {
                            CompostFileManagementUtil.createFileWithTemplate(e.getProject(), file, name, current.get(name), current);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }

                }
            }
        });

        dialog.pack();
        dialog.setVisible(true);*/

        /*  first, get and initialize an engine  */
        FileTemplateManager manager = FileTemplateManager.getInstance(e.getProject());

        FileTemplate leafTemplate = manager.getInternalTemplate("View");

        HashMap map = new HashMap<String, String>();
        map.put("viewBridgeName", "Robert!");
        try {
            String s = leafTemplate.getText(map);
        } catch (IOException ex) {}

    }
}
