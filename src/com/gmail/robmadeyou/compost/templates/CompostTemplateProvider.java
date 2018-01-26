package com.gmail.robmadeyou.compost.templates;

import com.jetbrains.php.lang.liveTemplates.PhpDefaultLiveTemplatesProvider;

public class CompostTemplateProvider  extends PhpDefaultLiveTemplatesProvider {
    @Override
    public String[] getDefaultLiveTemplateFiles() {
        return new String[]{
                "/resources/templates/"
        };
    }
}
