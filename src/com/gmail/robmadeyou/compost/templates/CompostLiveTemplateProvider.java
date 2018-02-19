package com.gmail.robmadeyou.compost.templates;

import com.jetbrains.php.lang.liveTemplates.PhpDefaultLiveTemplatesProvider;

public class CompostLiveTemplateProvider extends PhpDefaultLiveTemplatesProvider {
    @Override
    public String[] getDefaultLiveTemplateFiles() {

        return new String[]{
                "/resources/templates/live/"
        };
    }
}
