package com.github.jlcool.shorebird.ui;

import com.intellij.openapi.ui.DialogWrapper;

import org.jetbrains.annotations.Nullable;

import javax.swing.ButtonModel;
import javax.swing.JComponent;

public class MyDialog  extends DialogWrapper {
    public MyDialog() {
        super(true); // 设置为模态对话框
        init();
    }
    @Override
    protected @Nullable JComponent createCenterPanel() {
        return null;
    }

    public String getTypeRadio() {
        return "";
    }
}
