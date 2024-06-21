/*
 * Created by JFormDesigner on Tue Jun 18 10:48:29 CST 2024
 */

package com.github.jlcool.shorebird.ui;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.ui.DialogWrapper;

import net.miginfocom.swing.MigLayout;

import org.jetbrains.annotations.Nullable;

import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author liangjiang
 */
public class MydialogForm extends DialogWrapper {
    public MydialogForm() {
        super(true);
        initComponents();
        init();


    }

    private void radioButtonAPKListener(ActionEvent e) {
        PropertiesComponent.getInstance().setValue("type_radio_button", 0,0);
        checkBoxArtifact.setEnabled(true);
        checkBoxCodesign.setEnabled(false);
        checkBoxDryRun.setEnabled(radioButtonRelease.isSelected() || radioButtonPatch.isSelected());
        checkBoxStaging.setEnabled(radioButtonPreview.isSelected() || radioButtonRelease.isSelected() || radioButtonPatch.isSelected());
        checkBoxDingDing.setEnabled(radioButtonRelease.isSelected());
        checkBoxUpload.setEnabled(radioButtonRelease.isSelected());
        apiKeyField.setEnabled(radioButtonRelease.isSelected() && checkBoxUpload.isSelected());
        dingdingTokenField.setEnabled(radioButtonRelease.isSelected() && checkBoxDingDing.isSelected());
        flutterVersionField.setEnabled(radioButtonRelease.isSelected());
        releaseVersionField.setEnabled(radioButtonRelease.isSelected() || radioButtonPatch.isSelected() || radioButtonPreview.isSelected());
    }

    private void radioButtonIOSListener(ActionEvent e) {
        PropertiesComponent.getInstance().setValue("type_radio_button", 1,0);
        checkBoxArtifact.setEnabled(false);
        checkBoxCodesign.setEnabled(radioButtonPreview.isSelected() || radioButtonRelease.isSelected() || radioButtonPatch.isSelected());
        checkBoxDryRun.setEnabled(radioButtonRelease.isSelected() || radioButtonPatch.isSelected());
        checkBoxStaging.setEnabled(radioButtonPreview.isSelected() || radioButtonRelease.isSelected() || radioButtonPatch.isSelected());
        checkBoxDingDing.setEnabled(radioButtonRelease.isSelected());
        checkBoxUpload.setEnabled(radioButtonRelease.isSelected());
        apiKeyField.setEnabled(radioButtonRelease.isSelected() && checkBoxUpload.isSelected());
        dingdingTokenField.setEnabled(radioButtonRelease.isSelected() && checkBoxDingDing.isSelected());
        flutterVersionField.setEnabled(radioButtonRelease.isSelected());
        releaseVersionField.setEnabled(radioButtonRelease.isSelected() || radioButtonPatch.isSelected() || radioButtonPreview.isSelected());
    }

    private void loginListener(ActionEvent e) {
        PropertiesComponent.getInstance().setValue("command_radio_button", 0,0);
        radioButtonAPK.setEnabled(false);
        radioButtonIOS.setEnabled(false);
        checkBoxArtifact.setEnabled(false);
        checkBoxCodesign.setEnabled(false);
        checkBoxDryRun.setEnabled(false);
        checkBoxStaging.setEnabled(false);
        checkBoxDingDing.setEnabled(false);
        checkBoxUpload.setEnabled(false);
        apiKeyField.setEnabled(false);
        dingdingTokenField.setEnabled(false);
        flutterVersionField.setEnabled(false);
        releaseVersionField.setEnabled(false);
    }

    private void initListener(ActionEvent e) {
        PropertiesComponent.getInstance().setValue("command_radio_button", 1,0);
        radioButtonAPK.setEnabled(false);
        radioButtonIOS.setEnabled(false);
        checkBoxArtifact.setEnabled(false);
        checkBoxCodesign.setEnabled(false);
        checkBoxDryRun.setEnabled(false);
        checkBoxStaging.setEnabled(false);
        checkBoxDingDing.setEnabled(false);
        checkBoxUpload.setEnabled(false);
        apiKeyField.setEnabled(false);
        dingdingTokenField.setEnabled(false);
        flutterVersionField.setEnabled(false);
        releaseVersionField.setEnabled(false);
    }

    private void previewListener(ActionEvent e) {
        PropertiesComponent.getInstance().setValue("command_radio_button", 2,0);
        radioButtonAPK.setEnabled(true);
        radioButtonIOS.setEnabled(true);
        checkBoxArtifact.setEnabled(false);
        checkBoxCodesign.setEnabled(radioButtonIOS.isSelected());
        checkBoxDryRun.setEnabled(false);
        checkBoxStaging.setEnabled(true);
        checkBoxDingDing.setEnabled(false);
        checkBoxUpload.setEnabled(false);
        apiKeyField.setEnabled(false);
        dingdingTokenField.setEnabled(false);
        flutterVersionField.setEnabled(false);
        releaseVersionField.setEnabled(true);
    }

    private void releaseListener(ActionEvent e) {
        PropertiesComponent.getInstance().setValue("command_radio_button", 3,0);
        radioButtonAPK.setEnabled(true);
        radioButtonIOS.setEnabled(true);
        checkBoxArtifact.setEnabled(radioButtonAPK.isSelected());
        checkBoxCodesign.setEnabled(radioButtonIOS.isSelected());
        checkBoxDryRun.setEnabled(true);
        checkBoxStaging.setEnabled(true);
        checkBoxDingDing.setEnabled(true);
        checkBoxUpload.setEnabled(true);
        apiKeyField.setEnabled(checkBoxUpload.isSelected());
        dingdingTokenField.setEnabled(checkBoxDingDing.isSelected());
        flutterVersionField.setEnabled(true);
        releaseVersionField.setEnabled(true);
    }

    private void patchListener(ActionEvent e) {
        PropertiesComponent.getInstance().setValue("command_radio_button", 4,0);
        radioButtonAPK.setEnabled(true);
        radioButtonIOS.setEnabled(true);
        checkBoxArtifact.setEnabled(false);
        checkBoxCodesign.setEnabled(radioButtonIOS.isSelected());
        checkBoxDryRun.setEnabled(true);
        checkBoxStaging.setEnabled(true);
        checkBoxDingDing.setEnabled(false);
        checkBoxUpload.setEnabled(false);
        apiKeyField.setEnabled(checkBoxUpload.isSelected());
        dingdingTokenField.setEnabled(checkBoxDingDing.isSelected());
        flutterVersionField.setEnabled(false);
        releaseVersionField.setEnabled(true);
    }

    private void reinitListener(ActionEvent e) {
        PropertiesComponent.getInstance().setValue("command_radio_button", 5,0);
        radioButtonAPK.setEnabled(false);
        radioButtonIOS.setEnabled(false);
        checkBoxArtifact.setEnabled(false);
        checkBoxCodesign.setEnabled(false);
        checkBoxDryRun.setEnabled(false);
        checkBoxStaging.setEnabled(false);
        checkBoxDingDing.setEnabled(false);
        checkBoxUpload.setEnabled(false);
        apiKeyField.setEnabled(false);
        dingdingTokenField.setEnabled(false);
        flutterVersionField.setEnabled(false);
        releaseVersionField.setEnabled(false);
    }

    private void artifactListener(ActionEvent e) {
        PropertiesComponent.getInstance().setValue("shorebird_check_box_artifact", checkBoxArtifact.isSelected());
    }

    private void stagingListener(ActionEvent e) {
        PropertiesComponent.getInstance().setValue("shorebird_check_box_staging", checkBoxStaging.isSelected());
    }

    private void codesignListener(ActionEvent e) {
        PropertiesComponent.getInstance().setValue("shorebird_check_box_codesign", checkBoxCodesign.isSelected());
    }

    private void dryRunListener(ActionEvent e) {
        PropertiesComponent.getInstance().setValue("shorebird_check_box_dry_run", checkBoxDryRun.isSelected());
    }

    private void uploadListener(ActionEvent e) {
        PropertiesComponent.getInstance().setValue("shorebird_check_box_upload", checkBoxUpload.isSelected());
        apiKeyField.setEnabled(radioButtonRelease.isSelected() && checkBoxUpload.isSelected());
        dingdingTokenField.setEnabled(radioButtonRelease.isSelected() && checkBoxDingDing.isSelected());
    }

    private void dingDingListener(ActionEvent e) {
        PropertiesComponent.getInstance().setValue("shorebird_check_box_dingding", checkBoxDingDing.isSelected());
        apiKeyField.setEnabled(radioButtonRelease.isSelected() && checkBoxUpload.isSelected());
        dingdingTokenField.setEnabled(radioButtonRelease.isSelected() && checkBoxDingDing.isSelected());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - 阿亮
        contentPanel = new JPanel();
        label3 = new JLabel();
        radioButtonAPK = new JRadioButton();
        radioButtonIOS = new JRadioButton();
        label4 = new JLabel();
        radioButtonLogin = new JRadioButton();
        radioButtonInit = new JRadioButton();
        radioButtonPreview = new JRadioButton();
        radioButtonRelease = new JRadioButton();
        radioButtonPatch = new JRadioButton();
        radioButtonReInit = new JRadioButton();
        label5 = new JLabel();
        checkBoxArtifact = new JCheckBox();
        checkBoxStaging = new JCheckBox();
        checkBoxCodesign = new JCheckBox();
        checkBoxDryRun = new JCheckBox();
        label1 = new JLabel();
        flutterVersionField = new JTextField();
        label2 = new JLabel();
        releaseVersionField = new JTextField();
        label6 = new JLabel();
        checkBoxUpload = new JCheckBox();
        checkBoxDingDing = new JCheckBox();
        label7 = new JLabel();
        apiKeyField = new JTextField();
        label8 = new JLabel();
        dingdingTokenField = new JTextField();
        radioTypeGroup = new ButtonGroup();
        radioCommandGroup = new ButtonGroup();

        //======== contentPanel ========
        {
            contentPanel.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]"));

            //---- label3 ----
            label3.setText("type:");
            label3.setHorizontalTextPosition(SwingConstants.LEADING);
            label3.setHorizontalAlignment(SwingConstants.RIGHT);
            contentPanel.add(label3, "cell 1 0");

            //---- radioButtonAPK ----
            radioButtonAPK.setText("android");
            radioButtonAPK.setActionCommand("android");
            radioButtonAPK.addActionListener(e -> radioButtonAPKListener(e));
            contentPanel.add(radioButtonAPK, "cell 2 0");

            //---- radioButtonIOS ----
            radioButtonIOS.setText("ios");
            radioButtonIOS.setActionCommand("ios");
            radioButtonIOS.addActionListener(e -> radioButtonIOSListener(e));
            contentPanel.add(radioButtonIOS, "cell 3 0");

            //---- label4 ----
            label4.setText("command:");
            label4.setHorizontalAlignment(SwingConstants.RIGHT);
            contentPanel.add(label4, "cell 1 1");

            //---- radioButtonLogin ----
            radioButtonLogin.setText("login");
            radioButtonLogin.setActionCommand("login");
            radioButtonLogin.addActionListener(e -> loginListener(e));
            contentPanel.add(radioButtonLogin, "cell 2 1");

            //---- radioButtonInit ----
            radioButtonInit.setText("init");
            radioButtonInit.setActionCommand("init");
            radioButtonInit.addActionListener(e -> initListener(e));
            contentPanel.add(radioButtonInit, "cell 3 1");

            //---- radioButtonPreview ----
            radioButtonPreview.setText("preview");
            radioButtonPreview.setActionCommand("preview");
            radioButtonPreview.addActionListener(e -> previewListener(e));
            contentPanel.add(radioButtonPreview, "cell 4 1");

            //---- radioButtonRelease ----
            radioButtonRelease.setText("release");
            radioButtonRelease.setActionCommand("release");
            radioButtonRelease.addActionListener(e -> releaseListener(e));
            contentPanel.add(radioButtonRelease, "cell 5 1");

            //---- radioButtonPatch ----
            radioButtonPatch.setText("patch");
            radioButtonPatch.setActionCommand("patch");
            radioButtonPatch.addActionListener(e -> patchListener(e));
            contentPanel.add(radioButtonPatch, "cell 6 1");

            //---- radioButtonReInit ----
            radioButtonReInit.setText("reinit");
            radioButtonReInit.setActionCommand("reinit");
            radioButtonReInit.addActionListener(e -> reinitListener(e));
            contentPanel.add(radioButtonReInit, "cell 7 1");

            //---- label5 ----
            label5.setText("flag:");
            label5.setHorizontalAlignment(SwingConstants.RIGHT);
            contentPanel.add(label5, "cell 1 2");

            //---- checkBoxArtifact ----
            checkBoxArtifact.setText("artifact");
            checkBoxArtifact.addActionListener(e -> artifactListener(e));
            contentPanel.add(checkBoxArtifact, "cell 2 2");

            //---- checkBoxStaging ----
            checkBoxStaging.setText("staging");
            checkBoxStaging.addActionListener(e -> stagingListener(e));
            contentPanel.add(checkBoxStaging, "cell 3 2");

            //---- checkBoxCodesign ----
            checkBoxCodesign.setText("codesign");
            checkBoxCodesign.addActionListener(e -> codesignListener(e));
            contentPanel.add(checkBoxCodesign, "cell 4 2");

            //---- checkBoxDryRun ----
            checkBoxDryRun.setText("dryrun");
            checkBoxDryRun.addActionListener(e -> dryRunListener(e));
            contentPanel.add(checkBoxDryRun, "cell 5 2");

            //---- label1 ----
            label1.setText("flutter version:");
            label1.setHorizontalTextPosition(SwingConstants.RIGHT);
            label1.setHorizontalAlignment(SwingConstants.RIGHT);
            contentPanel.add(label1, "cell 1 3");
            contentPanel.add(flutterVersionField, "cell 2 3 6 1");

            //---- label2 ----
            label2.setText("release version:");
            label2.setHorizontalAlignment(SwingConstants.RIGHT);
            contentPanel.add(label2, "cell 1 4");
            contentPanel.add(releaseVersionField, "cell 2 4 6 1");

            //---- label6 ----
            label6.setText("extend:");
            label6.setHorizontalAlignment(SwingConstants.RIGHT);
            contentPanel.add(label6, "cell 1 5");

            //---- checkBoxUpload ----
            checkBoxUpload.setText("upload to pgy");
            checkBoxUpload.addActionListener(e -> uploadListener(e));
            contentPanel.add(checkBoxUpload, "cell 2 5 2 1");

            //---- checkBoxDingDing ----
            checkBoxDingDing.setText("send dingding");
            checkBoxDingDing.addActionListener(e -> dingDingListener(e));
            contentPanel.add(checkBoxDingDing, "cell 4 5 2 1");

            //---- label7 ----
            label7.setText("pyg api key:");
            label7.setHorizontalAlignment(SwingConstants.RIGHT);
            contentPanel.add(label7, "cell 1 6");
            contentPanel.add(apiKeyField, "cell 2 6 6 1");

            //---- label8 ----
            label8.setText("dingding token:");
            label8.setHorizontalAlignment(SwingConstants.RIGHT);
            contentPanel.add(label8, "cell 1 7");
            contentPanel.add(dingdingTokenField, "cell 2 7 6 1");
        }

        //---- radioTypeGroup ----
        radioTypeGroup.add(radioButtonAPK);
        radioTypeGroup.add(radioButtonIOS);

        //---- radioCommandGroup ----
        radioCommandGroup.add(radioButtonLogin);
        radioCommandGroup.add(radioButtonInit);
        radioCommandGroup.add(radioButtonPreview);
        radioCommandGroup.add(radioButtonRelease);
        radioCommandGroup.add(radioButtonPatch);
        radioCommandGroup.add(radioButtonReInit);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - 阿亮
    private JPanel contentPanel;
    private JLabel label3;
    private JRadioButton radioButtonAPK;
    private JRadioButton radioButtonIOS;
    private JLabel label4;
    private JRadioButton radioButtonLogin;
    private JRadioButton radioButtonInit;
    private JRadioButton radioButtonPreview;
    private JRadioButton radioButtonRelease;
    private JRadioButton radioButtonPatch;
    private JRadioButton radioButtonReInit;
    private JLabel label5;
    private JCheckBox checkBoxArtifact;
    private JCheckBox checkBoxStaging;
    private JCheckBox checkBoxCodesign;
    private JCheckBox checkBoxDryRun;
    private JLabel label1;
    private JTextField flutterVersionField;
    private JLabel label2;
    private JTextField releaseVersionField;
    private JLabel label6;
    private JCheckBox checkBoxUpload;
    private JCheckBox checkBoxDingDing;
    private JLabel label7;
    private JTextField apiKeyField;
    private JLabel label8;
    private JTextField dingdingTokenField;
    private ButtonGroup radioTypeGroup;
    private ButtonGroup radioCommandGroup;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
    @Override
    protected @Nullable JComponent createCenterPanel() {

        flutterVersionField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textChanged(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textChanged(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // 对于纯文本组件，此方法通常不需要处理，但为了完整性这里依然包含
            }

            // 处理文本变化的逻辑
            private void textChanged(DocumentEvent e) {
                PropertiesComponent.getInstance().setValue("shorebird_flutter_version", flutterVersionField.getText());
            }
        });
        releaseVersionField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textChanged(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textChanged(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // 对于纯文本组件，此方法通常不需要处理，但为了完整性这里依然包含
            }

            // 处理文本变化的逻辑
            private void textChanged(DocumentEvent e) {
                PropertiesComponent.getInstance().setValue("shorebird_release_version", releaseVersionField.getText());
            }
        });
        apiKeyField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textChanged(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textChanged(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // 对于纯文本组件，此方法通常不需要处理，但为了完整性这里依然包含
            }

            // 处理文本变化的逻辑
            private void textChanged(DocumentEvent e) {
                PropertiesComponent.getInstance().setValue("shorebird_pyg_api_key", apiKeyField.getText());
            }
        });
        dingdingTokenField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textChanged(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textChanged(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // 对于纯文本组件，此方法通常不需要处理，但为了完整性这里依然包含
            }

            // 处理文本变化的逻辑
            private void textChanged(DocumentEvent e) {
                PropertiesComponent.getInstance().setValue("shorebird_ding_token", dingdingTokenField.getText());
            }
        });
        
        radioButtonAPK.setSelected(PropertiesComponent.getInstance().getInt("type_radio_button", 0)==0);
        radioButtonIOS.setSelected(PropertiesComponent.getInstance().getInt("type_radio_button", 0)==1);

        radioButtonLogin.setSelected(PropertiesComponent.getInstance().getInt("command_radio_button", 0)==0);
        radioButtonInit.setSelected(PropertiesComponent.getInstance().getInt("command_radio_button", 0)==1);
        radioButtonPreview.setSelected(PropertiesComponent.getInstance().getInt("command_radio_button", 0)==2);
        radioButtonRelease.setSelected(PropertiesComponent.getInstance().getInt("command_radio_button", 0)==3);

        radioButtonPatch.setSelected(PropertiesComponent.getInstance().getInt("command_radio_button", 0)==4);
        radioButtonReInit.setSelected(PropertiesComponent.getInstance().getInt("command_radio_button", 0)==5);
        
        checkBoxArtifact.setSelected(PropertiesComponent.getInstance().getBoolean("shorebird_check_box_artifact", false));
        checkBoxStaging.setSelected(PropertiesComponent.getInstance().getBoolean("shorebird_check_box_staging", false));
        checkBoxCodesign.setSelected(PropertiesComponent.getInstance().getBoolean("shorebird_check_box_codesign", false));
        checkBoxDryRun.setSelected(PropertiesComponent.getInstance().getBoolean("shorebird_check_box_dry_run", false));

        flutterVersionField.setText(PropertiesComponent.getInstance().getValue("shorebird_flutter_version", ""));
        releaseVersionField.setText(PropertiesComponent.getInstance().getValue("shorebird_release_version", ""));
        
        apiKeyField.setText(PropertiesComponent.getInstance().getValue("shorebird_pyg_api_key", ""));
        dingdingTokenField.setText(PropertiesComponent.getInstance().getValue("shorebird_ding_token", ""));
        
        checkBoxUpload.setSelected(PropertiesComponent.getInstance().getBoolean("shorebird_check_box_upload", false));
        checkBoxDingDing.setSelected(PropertiesComponent.getInstance().getBoolean("shorebird_check_box_dingding", false));


        radioButtonAPK.setEnabled(radioButtonPreview.isSelected() || radioButtonRelease.isSelected() || radioButtonPatch.isSelected());
        radioButtonIOS.setEnabled(radioButtonPreview.isSelected() || radioButtonRelease.isSelected() || radioButtonPatch.isSelected());
        checkBoxArtifact.setEnabled(radioButtonAPK.isSelected());
        checkBoxCodesign.setEnabled(radioButtonPreview.isSelected() || radioButtonRelease.isSelected() || radioButtonPatch.isSelected());
        checkBoxDryRun.setEnabled(radioButtonRelease.isSelected() || radioButtonPatch.isSelected());
        checkBoxStaging.setEnabled(radioButtonPreview.isSelected() || radioButtonRelease.isSelected() || radioButtonPatch.isSelected());
        checkBoxDingDing.setEnabled(radioButtonRelease.isSelected());
        checkBoxUpload.setEnabled(radioButtonRelease.isSelected());
        apiKeyField.setEnabled(radioButtonRelease.isSelected() && checkBoxUpload.isSelected());
        dingdingTokenField.setEnabled(radioButtonRelease.isSelected() && checkBoxDingDing.isSelected());
        flutterVersionField.setEnabled(radioButtonRelease.isSelected());
        releaseVersionField.setEnabled(radioButtonRelease.isSelected() || radioButtonPatch.isSelected() || radioButtonPreview.isSelected());
        return contentPanel;
    }

    public String getTypeRadio() {
        return radioTypeGroup.getSelection().getActionCommand();
    }
    public String getCommandRadio() {
        return radioCommandGroup.getSelection().getActionCommand();
    }
    public String getFlutterVersion() {
        return flutterVersionField.getText();
    }
    public String getReleaseVersion() {
        return releaseVersionField.getText();
    }
    public String getApiKey() {
        return apiKeyField.getText();
    }
    public boolean isStaging() {
        if(radioButtonPatch.isSelected() || radioButtonPreview.isSelected()) {
            return checkBoxStaging.isSelected();
        }
        return false;
    }
    public boolean isArtifact() {
        if(radioButtonAPK.isEnabled()&& radioButtonAPK.isSelected()) {
            return checkBoxArtifact.isSelected();
        }
        return false;
    }
    public boolean isDryRun() {
        return checkBoxDryRun.isSelected();
    }
    public boolean isUploadToPgy() {
        return checkBoxUpload.isSelected();
    }
    public boolean isDingDingSelected() {
        return checkBoxDingDing.isSelected();
    }
    public boolean isCodesignSelected() {
        return checkBoxCodesign.isSelected() && checkBoxCodesign.isEnabled();
    }
    public String getDingToken() {
        return dingdingTokenField.getText();
    }
}
