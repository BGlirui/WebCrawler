package com.wcrawler.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.SplittableRandom;

public class GetJsonToExcel {
    private ObservableList<String> filePathName = FXCollections.observableArrayList();
    private Map<String,Map<String,String>> fileMap = new HashMap<String, Map<String, String>>();
    private String path ;


    public void input(String path){
        this.path = path;
        File file = new File(path);
        File[] files = file.listFiles();
        String[] names = file.list();
        for(int i=0;i<names.length;i++){
            if(files[i].isDirectory()){
                filePathName.add(names[i]);
                fileInput(names[i]);
            }else {
                System.out.println(names[i]+":"+prefix(names[i],"xlsx"));
                if(filePathName.contains(prefix(names[i],"xlsx"))){
                    filePathName.remove(prefix(names[i],"xlsx"));
                }
                if(fileMap.containsKey(prefix(names[i],"xlsx"))){
                    fileMap.remove(prefix(names[i],"xlsx"));
                }
            }
        }
    }

    public String toExcel(String fileName,String path){
        if(fileMap.containsKey(fileName)){
            Map<String,String> filePathAndName = fileMap.get(fileName);
            if(filePathAndName.containsKey("head.json")){
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
                XSSFSheet xssfSheet = xssfWorkbook.createSheet("Sheet1");
                int cellNum = 0;
                int rowNum = 0;
                String jsonStrHead = readJsonFile(filePathAndName.get("head.json"));
                JSONObject jsonObjectHead = JSON.parseObject(jsonStrHead);
                Map<String,Object> mapHead = jsonObjectHead.getInnerMap();
                XSSFRow xssfRowHead = xssfSheet.createRow(rowNum);
                rowNum++;
                for (String ss:mapHead.keySet()){
                    xssfRowHead.createCell(cellNum).setCellValue(ss);
                    cellNum++;
                }
                for(String s:filePathAndName.keySet()){
                    if(!"head.json".equals(s)){
                        XSSFRow xssfRow = xssfSheet.createRow(rowNum);
                        String jsonStr = readJsonFile(filePathAndName.get(s));
                        JSONObject jsonObject = JSON.parseObject(jsonStr);
                        Map<String,Object> map= jsonObject.getInnerMap();
                        cellNum = 0;
                        for(String sss:mapHead.keySet()){
                            if(map.get(sss)!=null&&map.get(sss).toString().length()<30000){
                                xssfRow.createCell(cellNum).setCellValue(map.get(sss).toString());
                                cellNum++;
                            }else {
                                xssfRow.createCell(cellNum).setCellValue("");
                                cellNum++;
                            }

                        }
                        rowNum++;
                    }
                }
                save(xssfWorkbook,path);
                return "success";
            }else {
                return "noHand";
            }

        }else {
            return "noFileName";
        }

    }

    /**
     * 存储
     * @param xssfWorkbook
     * @param path
     */
    private void save(XSSFWorkbook xssfWorkbook,String path){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
            xssfWorkbook.write(fileOutputStream);
            xssfWorkbook.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取json字符串
     * @param fileName
     * @return
     */
    private String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void fileInput(String name){
        String path = this.path+"/"+name;
        File file = new File(path);
        File[] files = file.listFiles();
        String[] names = file.list();
        Map<String,String> filePathAndName = new HashMap<String, String>();
        for(int i=0;i<names.length;i++){
            if(!files[i].isDirectory()){
                if(suffix(names[i],"json")){
                    filePathAndName.put(names[i],path+"/"+names[i]);
                }
            }
        }
        fileMap.put(name,filePathAndName);
    }

    private boolean suffix(String fileName,String suff){
        return suff.equals(fileName.substring(fileName.lastIndexOf(".")+1));
    }

    private String prefix(String fileName,String suff){
        return fileName.substring(0,fileName.lastIndexOf("."));
    }



    public ObservableList<String> getFilePathName() {
        return filePathName;
    }

    public Map<String, Map<String, String>> getFileMap() {
        return fileMap;
    }
}
