package com.wcrawler.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.wcrawler.crawler.ElementRuleManage;
import com.wcrawler.crawler.WebMagicAction;
import com.wcrawler.entity.ElementRule;
import com.wcrawler.global.GlobalObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.net.SyslogAppender;
import org.apache.poi.hssf.record.BOFRecord;
import org.omg.CORBA.TRANSACTION_MODE;
import us.codecraft.webmagic.Spider;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RunConfigLayout {
    private Stage dialog;
    @FXML
    private JFXButton runConfigPathBrowse,runConfigRun,runConfigStop;
    @FXML
    private JFXTextField runConfigPath,runConfigFileName,runConfigSleepTime,runConfigLineNumber;
    @FXML
    private JFXComboBox<Integer> runConfigThreadNumber,runConfigRetryTime;
    @FXML
    private Label runConfigHasData,runConfigUrl,runConfigNum;

    private ElementRuleManage elementRuleManage;
    private String Url;
    private String urlLink;
    private WebMagicAction webMagicAction;

    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private boolean is = true;
    private Service runConfigHasDataService;
    private Service runConfigUrlService;
    private Service runConfigNumService;
    private Map<String,Boolean> wholeMap;
    private String path;


    /**
     * 停止爬虫程序按钮
     */
    @FXML
    private void runConfigStopEvent(){
        if(webMagicAction!=null){
            while (webMagicAction.stop()&&is){
                runConfigPath.setDisable(false);
                runConfigFileName.setDisable(false);
                runConfigLineNumber.setDisable(false);
                runConfigThreadNumber.setDisable(false);
                runConfigRetryTime.setDisable(false);
                runConfigSleepTime.setDisable(false);
                runConfigRun.setDisable(false);
                runConfigPathBrowse.setDisable(false);
            }
        }
    }

    /**
     * 浏览按钮点击事件
     */
    @FXML
    private void runConfigPathBrowseEvent() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择");
        File directory = directoryChooser.showDialog(dialog);
        if(directory!=null){
            path = directory.getPath();
            runConfigPath.setText(path);
        }
    }

    /**
     * 开始按钮点击事件
     */
    @FXML
    private void runConfigRunEvent(){
        StringBuilder stringBuilder = new StringBuilder();
        boolean hasError = false;
        int num = 0;
        if(!runConfigLineNumber.getText().matches("[1-9][0-9]{0,5}?")){
            runConfigLineNumber.setText("");
            runConfigLineNumber.setUnFocusColor(Paint.valueOf("#e4101e"));
            num++;
            stringBuilder.append(num);
            stringBuilder.append(".");
            stringBuilder.append("请确认单个文件最大行数，该部分只能为1~100000的数字;\r\n");
            hasError = true;
        }
        if("".equals(runConfigFileName.getText())){
            runConfigFileName.setUnFocusColor(Paint.valueOf("#e4101e"));
            num++;
            stringBuilder.append(num);
            stringBuilder.append(".");
            stringBuilder.append("请输入文件名;\r\n");
            hasError = true;
        }
        if(!runConfigSleepTime.getText().matches("[1-9][0-9]{0,5}?")){
            runConfigSleepTime.setText("");
            runConfigSleepTime.setUnFocusColor(Paint.valueOf("#e4101e"));
            num++;
            stringBuilder.append(num);
            stringBuilder.append(".");
            stringBuilder.append("请确定抓取间隔时间，该部分的值只能为1~100000的数字;\r\n");
            hasError = true;
        }
        if(runConfigThreadNumber.getValue()==null){
            runConfigThreadNumber.setUnFocusColor(Paint.valueOf("#e4101e"));
            num++;
            stringBuilder.append(num);
            stringBuilder.append(".");
            stringBuilder.append("请选择线程数！;\r\n");
            hasError = true;
        }
        if(runConfigRetryTime.getValue()==null){
            runConfigRetryTime.setUnFocusColor(Paint.valueOf("#e4101e"));
            num++;
            stringBuilder.append(num);
            stringBuilder.append(".");
            stringBuilder.append("请选重试次数！;\r\n");
            hasError = true;
        }
        if("".equals(runConfigPath.getText())){
            runConfigPath.setUnFocusColor(Paint.valueOf("#e4101e"));
            num++;
            stringBuilder.append(num);
            stringBuilder.append(".");
            stringBuilder.append("请选择保存文件的路径！;\r\n");
            hasError = true;
        }

        if(hasError){
            num = 0;
            alert.setTitle("警告！");
            alert.setHeaderText(null);
            alert.setContentText(stringBuilder.toString());
            alert.showAndWait();
        }else {
            runConfigPath.setDisable(true);
            runConfigFileName.setDisable(true);
            runConfigLineNumber.setDisable(true);
            runConfigThreadNumber.setDisable(true);
            runConfigRetryTime.setDisable(true);
            runConfigSleepTime.setDisable(true);
            runConfigRun.setDisable(true);
            runConfigPathBrowse.setDisable(true);
            this.runConfigHasDataService = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            while (is){
                                updateValue(webMagicAction.getWebMagicConfig().getCsNum()+"");
                                Thread.sleep(1000);
                            }
                            return "success";
                        }
                    };
                }
            };
            this.runConfigUrlService = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            while (is){
                                updateValue(webMagicAction.getWebMagicConfig().getUnNum()+"");
                                Thread.sleep(1000);
                            }
                            return "success";
                        }
                    };
                }
            };
            this.runConfigNumService = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            while (is){
                                updateValue(webMagicAction.getNewJsonFilePipeline().getSumLineNum()+"");
                                Thread.sleep(1000);
                            }
                            return "success";
                        }
                    };
                }
            };
            runConfigHasData.textProperty().bind(runConfigHasDataService.valueProperty());
            runConfigUrl.textProperty().bind(runConfigUrlService.valueProperty());
            runConfigNum.textProperty().bind(runConfigNumService.valueProperty());


            this.webMagicAction = new WebMagicAction(runConfigRetryTime.getValue(),Integer.parseInt(runConfigSleepTime.getText()),runConfigThreadNumber.getValue()
            ,Url,Integer.parseInt(runConfigLineNumber.getText()),elementRuleManage,urlLink,runConfigPath.getText(),runConfigFileName.getText(),wholeMap);
            webMagicAction.action();
            runConfigHasDataService.start();
            runConfigUrlService.start();
            runConfigNumService.start();
        }
    }

    public void setDialog(Stage dialog){
        this.dialog = dialog;
    }

    public void init(GlobalObservableList globalObservableList, ElementRuleManage elementRuleManage, String Url, String urlLink){
        this.elementRuleManage = elementRuleManage;
        this.urlLink = urlLink;
        this.Url = Url;
        this.wholeMap = new HashMap<String, Boolean>();
        for(int i=0;i<elementRuleManage.getElementContents().size();i++){
            this.wholeMap.put(elementRuleManage.getElementContents().get(i).getRuleTableName(),elementRuleManage.getElementContents().get(i).isIsnull());
        }
        System.out.println(wholeMap);
        runConfigThreadNumber.setItems(globalObservableList.getObservableListThreadNumber());
        runConfigRetryTime.setItems(globalObservableList.getObservableListReTryNumber());
    }

    public String getPath() {
        return path;
    }
}
