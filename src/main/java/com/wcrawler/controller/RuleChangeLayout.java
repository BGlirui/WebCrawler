package com.wcrawler.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.wcrawler.crawler.ElementRuleManage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.Map;

public class RuleChangeLayout {
    @FXML
    private JFXButton ruleChangeEnsure,ruleChangeCancel;
    @FXML
    private JFXComboBox<String> ruleChangeRange,ruleChangeType;
    @FXML
    private JFXTextField ruleChangeRuleDetails,ruleChangeName;
    @FXML
    private JFXCheckBox ruleChangeIsNull;


    private Stage dialogStage;
    private ElementRuleManage elementRuleManage;
    private int num;
    private boolean okClicked = false;
    private String ruleTableElementRange = "";
    private String ruleTableElementRuleType = "";
    private String ruleTableElementRuleDetails = "";
    private String ruleTableElementName = "";
    private boolean ruleTableElementIsNull = false;
    private ObservableList<String> observableListRange;
    private ObservableList<String> observableListRuleType;


    @FXML
    private void ruleChangeRangeEvent(){
        ruleTableElementRange = ruleChangeRange.getValue();
    }
    @FXML
    private void ruleChangeRuleTypeEvent(){
        ruleTableElementRuleType = ruleChangeType.getValue();
        System.out.println(ruleTableElementRuleType);
    }

    @FXML
    private void ruleChangeEnsureEvent(){
        ruleTableElementRuleDetails = ruleChangeRuleDetails.getText();
        elementRuleManage.getElementContents().get(num).setRuleTableElementRange(ruleTableElementRange);
        elementRuleManage.getElementContents().get(num).setRuleTableRuleType(ruleTableElementRuleType);
        ruleTableElementIsNull = ruleChangeIsNull.isSelected();
        elementRuleManage.getElementContents().get(num).setIsnull(ruleTableElementIsNull);
        if(!"".equals(ruleTableElementRuleDetails)){
            elementRuleManage.getElementContents().get(num).setRuleTableRuleDetails(ruleTableElementRuleDetails);
        }
        if(!"".equals(ruleTableElementName)){
            elementRuleManage.getElementContents().get(num).setRuleTableName(ruleTableElementName);
        }
        if(ruleTableElementIsNull){
            elementRuleManage.getElementContents().get(num).setRuleTableIsNull("可");
        }else {
            elementRuleManage.getElementContents().get(num).setRuleTableIsNull("否");
        }

        dialogStage.close();
    }

    /**
     * 删除按钮
     */
    @FXML
    private void ruleChangeCancelEvent(){
        dialogStage.close();
    }

    public void setDialogStage(ObservableList<String> observableListRange,ObservableList<String> observableListRuleType,Stage dialogStage){
        this.dialogStage = dialogStage;
        this.observableListRange = observableListRange;
        this.observableListRuleType = observableListRuleType;
        ruleChangeRange.setItems(observableListRange);
        ruleChangeType.setItems(observableListRuleType);
    }

    public void setElementRuleManage(int num, ElementRuleManage elementRuleManage) {
        this.num = num;
        this.elementRuleManage = elementRuleManage;
        this.ruleTableElementRange = elementRuleManage.getElementContents().get(num).getRuleTableElementRange();
        this.ruleTableElementRuleType = elementRuleManage.getElementContents().get(num).getRuleTableRuleType();
        this.ruleTableElementName = elementRuleManage.getElementContents().get(num).getRuleTableName();
        this.ruleTableElementRuleDetails = elementRuleManage.getElementContents().get(num).getRuleTableRuleDetails();
        this.ruleTableElementIsNull = elementRuleManage.getElementContents().get(num).isIsnull();
        ruleChangeRange.setPromptText(ruleTableElementRange);
        ruleChangeType.setPromptText(ruleTableElementRuleType);
        ruleChangeName.setPromptText(ruleTableElementName);
        ruleChangeRuleDetails.setPromptText(ruleTableElementRuleDetails);
        ruleChangeIsNull.setSelected(ruleTableElementIsNull);

    }

    public boolean isOkClicked() {
        return okClicked;
    }
}
