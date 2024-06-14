package com.github.jlcool.shorebird.ui;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.ui.DialogWrapper;

import org.jetbrains.annotations.Nullable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MyDialog  extends DialogWrapper {
    private JPanel contentPanel= new JPanel();
    private JPanel typePanel= new JPanel();
    private JLabel typeLabel= new JLabel("type:");
    private ButtonGroup radioTypeGroup= new ButtonGroup();
    private JRadioButton radioButtonAPK=new JRadioButton("apk");
    private JRadioButton radioButtonIOS=new JRadioButton("ios");

    private JPanel commandPanel= new JPanel();
    private JLabel commandLabel= new JLabel("command:");
    private ButtonGroup radioCommandGroup= new ButtonGroup();
    private JRadioButton radioButtonLogin=new JRadioButton("login");
    private JRadioButton radioButtonInit=new JRadioButton("init");
    private JRadioButton radioButtonRelease=new JRadioButton("release");
    private JRadioButton radioButtonPreview=new JRadioButton("preview");
    private JRadioButton radioButtonPatch=new JRadioButton("patch");
    private JRadioButton radioButtonReInit=new JRadioButton("reinit");
    JPanel releasePanel = new JPanel();
    private JTextField flutterVersionField=new JTextField();
    private JCheckBox checkBoxArtifact= new JCheckBox("artifact");
    JPanel patchPanel = new JPanel();
    private JTextField releaseVersionField=new JTextField();
    private JCheckBox checkBoxStaging= new JCheckBox("staging");

    private JCheckBox checkBoxUpload= new JCheckBox("upload to pgy");
    private JCheckBox checkBoxUploadShorebird= new JCheckBox("upload to shorebird");
    private JCheckBox checkBoxDingDing= new JCheckBox("dingding message");
    private JTextField apiKeyField=new JTextField();
    private JTextField dingdingTokenField=new JTextField();

    private JPanel apiKeyPanel= new JPanel();
    private JPanel dingdingPanel= new JPanel();
    private JPanel checkBoxUploadPanel= new JPanel();
    private JPanel checkBoxDingDingPanel= new JPanel();

    public MyDialog() {
        super(true); // 设置为模态对话框
        init();
    }
    @Override
    protected @Nullable JComponent createCenterPanel()
    {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.X_AXIS));
        commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.X_AXIS));

        radioButtonAPK.setActionCommand("android");
        radioButtonIOS.setActionCommand("ios");
        radioButtonAPK.setSelected(PropertiesComponent.getInstance().getInt("type_radio_button", 0)==0);
        radioButtonIOS.setSelected(PropertiesComponent.getInstance().getInt("type_radio_button", 0)==1);
        radioButtonAPK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PropertiesComponent.getInstance().setValue("type_radio_button", 0,0);
            }
        });

        radioButtonIOS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PropertiesComponent.getInstance().setValue("type_radio_button", 1,0);
            }
        });
        radioTypeGroup.add(radioButtonAPK);
        radioTypeGroup.add(radioButtonIOS);
        typePanel.add(typeLabel);
        typePanel.add(radioButtonAPK);
        typePanel.add(radioButtonIOS);
        typePanel.add(Box.createHorizontalGlue());

        radioButtonLogin.setActionCommand("login");
        radioButtonInit.setActionCommand("init");
        radioButtonRelease.setActionCommand("release");
        radioButtonPreview.setActionCommand("preview");
        radioButtonPatch.setActionCommand("patch");
        radioButtonReInit.setActionCommand("reinit");

        radioButtonLogin.setSelected(PropertiesComponent.getInstance().getInt("command_radio_button", 0)==0);
        radioButtonInit.setSelected(PropertiesComponent.getInstance().getInt("command_radio_button", 0)==1);
        radioButtonRelease.setSelected(PropertiesComponent.getInstance().getInt("command_radio_button", 0)==2);
        radioButtonPreview.setSelected(PropertiesComponent.getInstance().getInt("command_radio_button", 0)==3);
        radioButtonPatch.setSelected(PropertiesComponent.getInstance().getInt("command_radio_button", 0)==4);
        radioButtonReInit.setSelected(PropertiesComponent.getInstance().getInt("command_radio_button", 0)==5);

        radioButtonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PropertiesComponent.getInstance().setValue("command_radio_button", 0,0);
            }
        });
        radioButtonInit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                releasePanel.setVisible(false);
                patchPanel.setVisible(false);
                PropertiesComponent.getInstance().setValue("command_radio_button", 1,0);
            }
        });
        radioButtonRelease.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                releasePanel.setVisible(radioButtonRelease.isSelected());
                patchPanel.setVisible(!radioButtonRelease.isSelected());
                PropertiesComponent.getInstance().setValue("command_radio_button", 2,0);
            }
        });
        radioButtonPreview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                releasePanel.setVisible(!radioButtonRelease.isSelected());
                patchPanel.setVisible(radioButtonRelease.isSelected());
                PropertiesComponent.getInstance().setValue("command_radio_button", 3,0);
            }
        });
        radioButtonPatch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                releasePanel.setVisible(!radioButtonPatch.isSelected());
                patchPanel.setVisible(radioButtonPatch.isSelected());
                PropertiesComponent.getInstance().setValue("command_radio_button", 4,0);
            }
        });
        radioButtonReInit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                releasePanel.setVisible(false);
                patchPanel.setVisible(false);
                PropertiesComponent.getInstance().setValue("command_radio_button", 5,0);
            }
        });

        releasePanel.setLayout(new BoxLayout(releasePanel, BoxLayout.X_AXIS));
        releasePanel.add(new JLabel("flutter version:"));
        String shorebirdFlutterVersion = PropertiesComponent.getInstance().getValue("shorebird_flutter_version", "");
        flutterVersionField.setText(shorebirdFlutterVersion);
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
        releasePanel.add(flutterVersionField);
        checkBoxArtifact.setSelected(PropertiesComponent.getInstance().getBoolean("shorebird_check_box_artifact", false));
        checkBoxArtifact.addActionListener(e -> {
            PropertiesComponent.getInstance().setValue("shorebird_check_box_artifact", checkBoxArtifact.isSelected());
        });
        releasePanel.add(checkBoxArtifact);
        releasePanel.add(Box.createHorizontalGlue());

        patchPanel.setLayout(new BoxLayout(patchPanel, BoxLayout.X_AXIS));
        patchPanel.add(new JLabel("release version:"));
        String shorebirdReleaseVersion = PropertiesComponent.getInstance().getValue("shorebird_release_version", "");
        releaseVersionField.setText(shorebirdReleaseVersion);
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
        patchPanel.add(releaseVersionField);
        checkBoxStaging.setSelected(PropertiesComponent.getInstance().getBoolean("shorebird_check_box_staging", false));
        checkBoxStaging.addActionListener(e -> {
            PropertiesComponent.getInstance().setValue("shorebird_check_box_staging", checkBoxStaging.isSelected());
        });
        patchPanel.add(checkBoxStaging);
        patchPanel.add(Box.createHorizontalGlue());

        checkBoxUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 根据 checkBoxUpload 的选中状态设置 apiKeyPanel 的可见性
                apiKeyPanel.setVisible(checkBoxUpload.isSelected());
            }
        });

        //是否发送钉钉消息
        checkBoxDingDing = new JCheckBox("send dingding message");
        checkBoxDingDing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dingdingPanel.setVisible(checkBoxDingDing.isSelected());
            }
        });

        checkBoxUploadPanel.setLayout(new BoxLayout(checkBoxUploadPanel, BoxLayout.X_AXIS));
        checkBoxUpload.setSelected(PropertiesComponent.getInstance().getBoolean("shorebird_check_box_upload", false));
        checkBoxUploadShorebird.setSelected(PropertiesComponent.getInstance().getBoolean("shorebird_check_box_upload_shorebird", false));
        checkBoxUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PropertiesComponent.getInstance().setValue("shorebird_check_box_upload", checkBoxUpload.isSelected());
            }
        });
        checkBoxUploadShorebird.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PropertiesComponent.getInstance().setValue("shorebird_check_box_upload_shorebird", checkBoxUploadShorebird.isSelected());
            }
        });
        checkBoxUploadPanel.add(checkBoxUpload);
        checkBoxUploadPanel.add(checkBoxUploadShorebird);
        checkBoxUploadPanel.add(Box.createHorizontalGlue());

        apiKeyPanel.setLayout(new BoxLayout(apiKeyPanel, BoxLayout.X_AXIS));
        apiKeyPanel.add(new JLabel("Apikey:"));
        String lastApiKey = PropertiesComponent.getInstance().getValue("shorebird_pyg_api_key", "");
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
        apiKeyField.setText(lastApiKey);
        apiKeyPanel.add(apiKeyField);

        checkBoxDingDingPanel.setLayout(new BoxLayout(checkBoxDingDingPanel, BoxLayout.X_AXIS));
        checkBoxDingDing.setSelected(PropertiesComponent.getInstance().getBoolean("shorebird_check_box_dingding", false));
        checkBoxDingDing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PropertiesComponent.getInstance().setValue("shorebird_check_box_dingding", checkBoxDingDing.isSelected());
            }
        });
        checkBoxDingDingPanel.add(checkBoxDingDing);
        checkBoxDingDingPanel.add(Box.createHorizontalGlue());
        dingdingPanel.setLayout(new BoxLayout(dingdingPanel, BoxLayout.X_AXIS));
        dingdingPanel.add(new JLabel("Token:"));
        String dingToken = PropertiesComponent.getInstance().getValue("shorebird_ding_token", "");
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
        dingdingTokenField.setText(dingToken);
        dingdingPanel.add(dingdingTokenField);



        radioCommandGroup.add(radioButtonLogin);
        radioCommandGroup.add(radioButtonInit);
        radioCommandGroup.add(radioButtonRelease);
        radioCommandGroup.add(radioButtonPreview);
        radioCommandGroup.add(radioButtonPatch);
        radioCommandGroup.add(radioButtonReInit);
        commandPanel.add(commandLabel);
        commandPanel.add(radioButtonLogin);
        commandPanel.add(radioButtonInit);
        commandPanel.add(radioButtonRelease);
        commandPanel.add(radioButtonPreview);
        commandPanel.add(radioButtonPatch);
        commandPanel.add(radioButtonReInit);
        commandPanel.add(Box.createHorizontalGlue());

        contentPanel.add(typePanel);
        contentPanel.add(commandPanel);
        contentPanel.add(releasePanel);
        contentPanel.add(patchPanel);

        contentPanel.add(checkBoxUploadPanel);
        contentPanel.add(apiKeyPanel);
        contentPanel.add(checkBoxDingDingPanel);
        contentPanel.add(dingdingPanel);

        releasePanel.setVisible(radioButtonRelease.isSelected());
        patchPanel.setVisible(radioButtonPreview.isSelected() || radioButtonPatch.isSelected());

        apiKeyPanel.setVisible(checkBoxUpload.isSelected());
        dingdingPanel.setVisible(checkBoxDingDing.isSelected());
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
        if(radioButtonRelease.isSelected()) {
            return checkBoxArtifact.isSelected();
        }
        return false;
    }
    public boolean isUploadToShorebird() {
        return checkBoxUploadShorebird.isSelected();
    }
    public boolean isUploadToPgy() {
        return checkBoxUpload.isSelected();
    }
    public boolean isDingDingSelected() {
        return checkBoxDingDing.isSelected();
    }
    public String getDingToken() {
        return dingdingTokenField.getText();
    }
}
