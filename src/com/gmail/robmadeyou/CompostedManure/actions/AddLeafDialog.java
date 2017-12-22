package com.gmail.robmadeyou.CompostedManure.actions;

import com.gmail.robmadeyou.CompostedManure.util.PhpBundleFileFactory;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.HashMap;

public class AddLeafDialog extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile file = PlatformDataKeys.VIRTUAL_FILE.getData(e.getDataContext());

        String filename = Messages.showInputDialog("What is the name of this leaf?", "Enter Name", null);
        try {
            PhpBundleFileFactory.createFileWithTemplate(e.getProject(), file, "Leaf", filename, new HashMap<>());
            PhpBundleFileFactory.createFileWithTemplate(e.getProject(), file, "Model", filename + "Model", new HashMap<>());

            HashMap viewHashmap = new HashMap<String, String>();
            viewHashmap.put("leafname", filename);
            PhpBundleFileFactory.createFileWithTemplate(e.getProject(), file, "View", filename + "View", viewHashmap);
        } catch (Exception ex) {
            System.out.println("Fuck " + ex.getMessage());
        }
    }
}
