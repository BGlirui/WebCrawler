package com.wcrawler.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.wcrawler.dao.GetJsonToExcel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;


public class ExcelCreatLayout {

    @FXML
    private JFXTextField excelCreatPath;
    @FXML
    private JFXButton excelCreatPathEnsure,excelCreatEnsure;
    @FXML
    private JFXComboBox<String> excelCreatSelect;
    @FXML
    private Label excelCreatResult;

    private Stage dialog;
    private String path;
    private GetJsonToExcel getJsonToExcel;

    public ExcelCreatLayout(){

    }

    public void setDialog(Stage dialog){
        this.dialog = dialog;
    }

    public void init(String path){
        this.path = path;
        excelCreatPath.setText(path);
        getJsonToExcel = new GetJsonToExcel();
        if(!path.equals("")){
            getJsonToExcel.input(path);
            excelCreatSelect.setItems(getJsonToExcel.getFilePathName());
        }
    }

    /**
     * 浏览按钮事件
     */
    @FXML
    private void pathEnsureEvent(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择");
        File directory = directoryChooser.showDialog(dialog);
        if(directory!=null){
            path = directory.getPath();
            excelCreatPath.setText(path);
            getJsonToExcel.input(path);
            excelCreatSelect.setItems(getJsonToExcel.getFilePathName());
        }
    }

    /**
     * 创建按钮事件
     */
    @FXML
    private void ensureEvent(){
        String result = getJsonToExcel.toExcel(excelCreatSelect.getValue(),path+"/"+excelCreatSelect.getValue()+".xlsx");
        System.out.println(result);
        if("success".equals(result)){
            excelCreatResult.setText("成功！");
            dialog.close();
        }else if("noFileName".equals(result)){
            excelCreatResult.setText("失败，无此文件");
        }else if("noHand".equals(result)){
            excelCreatResult.setText("失败，无头文件");
        }
    }
}
