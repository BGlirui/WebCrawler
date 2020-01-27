package com.wcrawler.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.wcrawler.action.MainApp;
import com.wcrawler.crawler.ElementRuleManage;
import com.wcrawler.crawler.WebMagicAction;
import com.wcrawler.entity.ElementRule;
import com.wcrawler.global.GlobalObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.Map;

public class HomeLayout {
    @FXML
    private JFXTextField webUrl,ruleDetails,name,urlLink;
    @FXML
    private JFXButton webUrlEnsure,ruleAdd,ruleChange,run,addUrlLink,read,excelCreat;
    @FXML
    private TableView ruleTable;
    @FXML
    private TableColumn<ElementRule,String> ruleTableNum,ruleTableElementRange,ruleTableRuleType,ruleTableName,ruleTableIsNull,ruleTableRuleDetails;
    @FXML
    private JFXComboBox<String> elementRange,ruleType;
    @FXML
    private JFXCheckBox ruleIsNull;
    private MainApp mainApp;
    private ElementRuleManage elementRuleManage = null;
    private int number;
    private GlobalObservableList globalObservableList;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private String path;

    public HomeLayout(){

    }
    /**
     * 点击转换Excel文件事件
     */
    @FXML
    private void excelCreatEvent(){
        mainApp.showExcelCreatLayout();
    }


    /**
     * 点击开始按钮事件
     */
    @FXML
    private void runEvent(){
        int num = 0;
        boolean hasError = false;
        StringBuilder stringBuilder = new StringBuilder();
        if(elementRuleManage.getElementContents().size()==0){
            num++;
            stringBuilder.append(num);
            stringBuilder.append(".");
            stringBuilder.append("请添加规则信息！\r\n");
            hasError = true;
        }
        if("".equals(webUrl.getText())){
            num++;
            webUrl.setUnFocusColor(Paint.valueOf("#e4101e"));
            stringBuilder.append(num);
            stringBuilder.append(".");
            stringBuilder.append("请确认Url地址！\r\n");
            hasError = true;
        }
        if("".equals(urlLink.getText())){
            urlLink.setText("null");
        }
        if(hasError){
            num=0;
            alert.setTitle("警告！");
            alert.setHeaderText(null);
            alert.setContentText(stringBuilder.toString());
            alert.showAndWait();
        }else {
            mainApp.showRunConfigLayout(elementRuleManage,webUrl.getText(),urlLink.getText());
        }
    }
    @FXML
    private void testEvent(){
        elementRuleManage.addElementRule(elementRuleManage.getNumberFlog(),GlobalObservableList.PAGE_URL,GlobalObservableList.REGEX,"author","https://github\\.com/(\\w+)/.*",true);
        elementRuleManage.addElementRule(elementRuleManage.getNumberFlog(),GlobalObservableList.PAGE_HTML,GlobalObservableList.XPATH,"name","//h1[@class='entry-title public']/strong/a/text()",true);
        elementRuleManage.addElementRule(elementRuleManage.getNumberFlog(),GlobalObservableList.PAGE_HTML,GlobalObservableList.XPATH,"readme","//div[@id='readme']/tidyText()",true);
        webUrl.setText("https://github.com/code4craft");
        urlLink.setText("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)");
    }
    
    /**
     * 改变元素范围的点击属性
     */
    @FXML
    private void elementRangeEvent(){
        elementRange.setUnFocusColor(Paint.valueOf("#4d4d4d"));
    }
    /**
     * 改变规则类型事件
     */
    @FXML
    private void ruleTypeEvent(){
        ruleType.setUnFocusColor(Paint.valueOf("#4d4d4d"));
    }
    /**
     * 点击规则细节事件
     */
    @FXML
    private void ruleDetailsEvent(){
        ruleDetails.setUnFocusColor(Paint.valueOf("#4d4d4d"));
    }
    /**
     * 点击字段名事件
     */
    @FXML
    private void nameEvent(){
        name.setUnFocusColor(Paint.valueOf("#4d4d4d"));
    }
    /**
     * 添加规则按钮
     */
    @FXML
    private void ruleAddEvent(){

        boolean isOk = false;
        boolean isNull = false;
        if(elementRange.getValue()==null){
            elementRange.setUnFocusColor(Paint.valueOf("#e4101e"));
            isNull = true;
        }
        if(ruleType.getValue()==null){
            ruleType.setUnFocusColor(Paint.valueOf("#e4101e"));
            isNull = true;
        }
        if("".equals(ruleDetails.getText())){
            ruleDetails.setUnFocusColor(Paint.valueOf("#e4101e"));
            isNull = true;
        }
        if("".equals(name.getText())){
            name.setUnFocusColor(Paint.valueOf("e4101e"));
            isNull = true;
        }
        if(isNull){
            alert.setTitle("警告！");
            alert.setHeaderText(null);
            alert.setContentText("请将信息填写完整！");
            alert.showAndWait();
            return;
        }else {
            isOk = elementRuleManage.addElementRule(elementRuleManage.getNumberFlog(),elementRange.getValue(),ruleType.getValue(),name.getText(),ruleDetails.getText(),ruleIsNull.isSelected());
            if(isOk){
                elementRange.setValue("");
                ruleType.setValue("");
                ruleDetails.setText("");
                name.setText("");
            }else {
                if(elementRuleManage.isRangeEnsure()){
                    //Range已经确认！
                    alert.setTitle("警告！");
                    alert.setHeaderText(null);
                    alert.setContentText("规则类型异常！");
                    alert.showAndWait();
                }else if(elementRuleManage.isRuleTypeEnsure()){
                    //ruleType已经确认！
                    alert.setTitle("警告！");
                    alert.setHeaderText(null);
                    alert.setContentText("元素范围异常！");
                    alert.showAndWait();
                }else {
                    alert.setTitle("警告！");
                    alert.setHeaderText(null);
                    alert.setContentText("元素范围和规则类型均异常！，建议重启软件！");
                    alert.showAndWait();
                }
            }
        }

    }

    /**
     * 选中Table的事件的业务逻辑代码
     * @param elementRule
     */
    @FXML
    private void ruleTouchEvent(ElementRule elementRule){
        if(elementRule!=null){
            this.number = Integer.parseInt(elementRule.getRuleTableNum());
        }else {
            number = 0;
        }
    }

    /**
     * 修改按钮的点击事件
     */
    @FXML
    private void ruleChangeEvent(){
        int numberNow = 0;
        for(int i =0;i<elementRuleManage.getElementContents().size();i++){
            numberNow = Integer.parseInt(elementRuleManage.getElementContents().get(i).getRuleTableNum());
            if(numberNow == this.number){
                mainApp.showRuleChangeLayout(i,elementRuleManage);
            }
        }
    }

    /**
     * 删除按钮事件
     */
    @FXML
    private void ruleDeleteEvent(){
        int numberNow = 0;
        for(int i =0;i<elementRuleManage.getElementContents().size();i++){
            numberNow = Integer.parseInt(elementRuleManage.getElementContents().get(i).getRuleTableNum());
            if(numberNow == this.number){
                elementRuleManage.getElementContents().remove(i);
            }
        }
    }



    @FXML
    private void initialize(){

        ruleTableNum.setCellValueFactory(cellData -> cellData.getValue().ruleTableNumProperty());
        ruleTableElementRange.setCellValueFactory(cellData -> cellData.getValue().ruleTableElementRangeProperty());
        ruleTableRuleType.setCellValueFactory(cellData -> cellData.getValue().ruleTableRuleTypeProperty());
        ruleTableName.setCellValueFactory(cellData -> cellData.getValue().ruleTableNameProperty());
        ruleTableIsNull.setCellValueFactory(cellData -> cellData.getValue().ruleTableIsNullProperty());
        ruleTableRuleDetails.setCellValueFactory(cellDate ->cellDate.getValue().ruleTableRuleDetailsProperty());

        //选中Table事件
        ruleTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> ruleTouchEvent((ElementRule) newValue)
        );

    }


    public void setMainApp(MainApp mainApp, GlobalObservableList globalObservableList){
        this.mainApp = mainApp;
        this.globalObservableList = globalObservableList;
        elementRange.setItems(globalObservableList.getObservableListRange());
        elementRange.setPromptText("请选择！");
        ruleType.setItems(globalObservableList.getObservableListRuleType());
        ruleType.setPromptText("请选择！");
        elementRuleManage = new ElementRuleManage();
        ruleTable.setItems(elementRuleManage.getElementContents());
        run.setDisable(true);
    }

    @FXML
    private void readEvent(){
        run.setDisable(false);
    }
}
