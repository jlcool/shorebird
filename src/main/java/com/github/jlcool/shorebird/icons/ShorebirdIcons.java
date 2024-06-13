package com.github.jlcool.shorebird.icons;

import com.intellij.openapi.util.IconLoader;
import com.intellij.util.IconUtil;

import javax.swing.Icon;


public class ShorebirdIcons {
    private static Icon load(String path) {
        return IconLoader.getIcon(path, ShorebirdIcons.class);
    }

    public static final Icon shorebird = IconUtil.scale(load("/icons/shorebird.svg"), null, 0.5f);;
}
