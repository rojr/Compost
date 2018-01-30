package com.gmail.robmadeyou.compost.actions;

import com.gmail.robmadeyou.compost.dialogs.AddNewLeafDialog;
import com.gmail.robmadeyou.compost.util.PhpBundleFileFactory;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.php.completion.PhpTemplateCompletionProcessor;

import java.util.HashMap;

public class AddLeafDialog extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        AddNewLeafDialog dialog = new AddNewLeafDialog(e.getProject());

        VirtualFile file = PlatformDataKeys.VIRTUAL_FILE.getData(e.getDataContext());

        dialog.setOnOkPressed(new AddNewLeafDialog.AddNewLeafDialogEvent() {
            @Override
            public void onOK() {
                if (file != null) {
                    HashMap<String, String> current = (HashMap<String, String>) this.values.clone();
                    for (String name : this.values.keySet()) {
                        try {
                            PhpBundleFileFactory.createFileWithTemplate(e.getProject(), file, name, current.get(name), current);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }

                }
            }
        });

        dialog.pack();
        dialog.setVisible(true);
    }
}
