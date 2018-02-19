package com.gmail.robmadeyou.compost.util;

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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Map;

public class CompostFileManagementUtil {
    public static void createFileWithTemplate(Project project, @NotNull VirtualFile virtualFile, @NotNull String template, @NotNull String className, Map<String, String> vars) throws Exception {
        if (fileExists(virtualFile, className)) {
            throw new Exception("File already exists");
        }

        String fileTemplateContent = getFileTemplateContent("/resources/fileTemplates/" + template + ".template");
        if (fileTemplateContent == null) {
            throw new Exception("Template content error");
        }

        String ns = getNamespaceForVirtualFolder(project, virtualFile);

        vars.put("ns", ns);
        vars.put("class", className);
        for (Map.Entry<String, String> entry : vars.entrySet()) {
            fileTemplateContent = fileTemplateContent.replace("{{ " + entry.getKey() + " }}", entry.getValue());
        }

        PsiFile fileFromText = PsiFileFactory.getInstance(project).createFileFromText(className + ".php", PhpLanguage.INSTANCE, fileTemplateContent);

        CodeStyleManager.getInstance(project).reformat(fileFromText);
        PsiDirectoryFactory.getInstance(project).createDirectory(virtualFile).add(fileFromText);
    }

    public static void createPhpClassFromTemplate()
    {

    }

    public static void createFileFromTemplate()
    {
        
    }

    private static String getNamespaceForVirtualFolder(Project project, VirtualFile virtualFile)
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
