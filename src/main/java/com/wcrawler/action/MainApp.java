package com.wcrawler.action;

import com.wcrawler.controller.ExcelCreatLayout;
import com.wcrawler.controller.HomeLayout;
import com.wcrawler.controller.RuleChangeLayout;
import com.wcrawler.controller.RunConfigLayout;
import com.wcrawler.crawler.ElementRuleManage;
import com.wcrawler.global.GlobalObservableList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private GlobalObservableList globalObservableList;
    private String path = "";


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("WebCrawler");
        this.globalObservableList = new GlobalObservableList();

        initRootLayout();
        showHomeLayout();

    }

    public void initRootLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/RootLayout.fxml"));
            rootLayout = (BorderPane)loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showHomeLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/HomeLayout.fxml"));
            AnchorPane homeLayout = (AnchorPane)loader.load();
            rootLayout.setCenter(homeLayout);

            HomeLayout homeLayoutController = loader.getController();
            homeLayoutController.setMainApp(this,globalObservableList);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean showRuleChangeLayout(int num,ElementRuleManage elementRuleManage){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/RuleChangeLayout.fxml"));
            AnchorPane ruleChangeLayout = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("修改规则");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(ruleChangeLayout);
            dialogStage.setScene(scene);

            RuleChangeLayout ruleChangeLayoutController = loader.getController();
            ruleChangeLayoutController.setDialogStage(globalObservableList.getObservableListRange(),globalObservableList.getObservableListRuleType(),dialogStage);
            ruleChangeLayoutController.setElementRuleManage(num,elementRuleManage);

            dialogStage.showAndWait();

            return ruleChangeLayoutController.isOkClicked();

        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean showRunConfigLayout(ElementRuleManage elementRuleManage, String Url, String urlLink){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/RunConfigLayout.fxml"));
            AnchorPane runConfigLayout = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("最后的设置");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(runConfigLayout);
            dialogStage.setScene(scene);

            RunConfigLayout runConfigLayoutController = loader.getController();
            runConfigLayoutController.setDialog(dialogStage);
            runConfigLayoutController.init(globalObservableList,elementRuleManage,Url,urlLink);
            dialogStage.showAndWait();

            path = runConfigLayoutController.getPath();
            System.out.println(path);
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean showExcelCreatLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/ExcelCreatLayout.fxml"));
            AnchorPane excelCreatLayout = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Excel文件转换");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(excelCreatLayout);
            dialogStage.setScene(scene);

            ExcelCreatLayout excelCreatLayoutController = loader.getController();
            excelCreatLayoutController.setDialog(dialogStage);
            excelCreatLayoutController.init(path);
            dialogStage.showAndWait();
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public static void main(String[] args){
        launch(args);
    }
}
