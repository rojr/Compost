package com.gmail.robmadeyou.compost.util;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.lang.Language;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.StreamUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.psi.PhpFileImpl;
import org.apache.commons.codec.language.bm.Lang;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Map;

public class CompostFileManagementUtil {
    /**
     * @param p Project
     * @param f Virtual File that the new class will need to be inserted in
     * @param t Template Name
     * @param n File Name
     * @param m String -> Object map for the template to fill in the blanks
     * @throws Exception
     */
    public static void createFileFromTemplate(Project p, VirtualFile f, String t, String n, String fN, Map<String, Object> m) throws Exception
    {
        if (fileExists(f, n)) {
            throw new Exception("File already exists");
        }

        FileTemplateManager manager = FileTemplateManager.getInstance(p);
        FileTemplate template = manager.getInternalTemplate(t);

        String fileContent = template.getText(m);

        PsiFile fileFromText = PsiFileFactory.getInstance(p).createFileFromText(fN, PhpLanguage.INSTANCE, fileContent);

        Runnable r = () -> PsiDirectoryFactory.getInstance(p).createDirectory(f).add(fileFromText);
        WriteCommandAction.runWriteCommandAction(p, r);
    }

    /**
     * Same as createFileFromTemplate() but specific for Php classes, will auto populate the namespace
     *
     * @param p Project
     * @param f Virtual File that the new class will need to be inserted in
     * @param t Template Name
     * @param n File Name
     * @param m String -> Object map for the template to fill in the blanks
     * @throws Exception
     */
    public static void createPhpFileFromTemplate(Project p, VirtualFile f, String t, String n, Map<String, Object> m) throws Exception
    {
        m.put("ns", getPHPNamespaceForVirtualFolder(p, f));
        m.put("class", n);

        createFileFromTemplate(p, f, t, n, n + ".php",m);
    }

    /**
     * Same as createFileFromTemplate() but specific for JS files
     *
     * @param p Project
     * @param f Virtual File that the new class will need to be inserted in
     * @param t Template Name
     * @param n File Name
     * @param m String -> Object map for the template to fill in the blanks
     * @throws Exception
     */
    public static void createJSFileFromTemplate(Project p, VirtualFile f, String t, String n, Map<String, Object> m) throws Exception
    {
        createFileFromTemplate(p, f, t, n, n + ".js",m);
    }

    public static String getPHPNamespaceForVirtualFolder(Project project, VirtualFile virtualFile)
    {
        String namespace = "";

        if (virtualFile.isDirectory()) {
            VirtualFile currentFile = virtualFile;

            outer: while (true) {
                VirtualFile[] children = currentFile.getChildren();

                for (VirtualFile f : children) {
                    if (!f.isDirectory()) {
                        PsiFile psiFile = PsiManager.getInstance(project).findFile(f);
                        if (psiFile != null) {
                            String fileNamespace = ((PhpFileImpl) psiFile).getMainNamespaceName();
                            if (!fileNamespace.equals("")) {
                                namespace = fileNamespace.concat(namespace);

                                break outer;
                            }
                        }
                    }
                }

                namespace = "\\" + currentFile.getName().concat(namespace);
                currentFile = currentFile.getParent();
            }
        }

        return namespace.replaceAll("(^\\\\+|\\\\+$)", "");
    }

    private static boolean fileExists(@NotNull VirtualFile bundleDir, @NotNull String className) {
        return VfsUtil.findRelativeFile(bundleDir, "DependencyInjection", "Compiler", className + ".php") != null || VfsUtil.findRelativeFile(bundleDir, "DependencyInjection", "CompilerPass", className + ".php") != null;
    }

    @Nullable
    private static String getFileTemplateContent(@NotNull String filename) {
        try {
            // replace on windows, just for secure reasons
            return StreamUtil.readText(CompostFileManagementUtil.class.getResourceAsStream(filename), "UTF-8").replace("\r\n", "\n");
        } catch (IOException e) {
            return null;
        }
    }
}
