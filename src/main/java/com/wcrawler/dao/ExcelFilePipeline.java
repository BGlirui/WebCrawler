package com.wcrawler.dao;


import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelFilePipeline extends FilePersistentBase implements Pipeline {


    private String path;
    private String name;
    private String pathAndName;
    private transient Set<String> deSet = new HashSet<String>();
    private int lineNumber = 0;
    private Map<String,Boolean> wholeMap;
    private SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss");
    private String primaryKey;
    private int maxLineNum;
    private int newLineNum = 0;
    private int fileNum = 1;
    private String regEx="[\u4E00-\u9FA5]|[\\w]|[,.，。@#*！：!~]";;

    //测试数据
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;
    public ExcelFilePipeline(String path,String name,Map<String,Boolean> wholeMap,int maxLineNum){
        this.path = path;
        this.name = name;
        StringBuilder pathAndNameMiddle = new StringBuilder();
        pathAndNameMiddle.append(path);
        pathAndNameMiddle.append("\\");
        pathAndNameMiddle.append(name).append("-");
        pathAndNameMiddle.append(format.format(new Date(System.currentTimeMillis())));
        pathAndNameMiddle.append(".xlsx");
        this.pathAndName = pathAndNameMiddle.toString();
        this.wholeMap = wholeMap;
        this.maxLineNum = maxLineNum;
        //测试标志
        xssfWorkbook = new XSSFWorkbook();
        xssfSheet = xssfWorkbook.createSheet("Sheet1");
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        excelCreat(resultItems.getAll());

    }

    private void newExcelCreat(Map<String,Object> result){
        if(isWhole(result)){
            if(isDuplication(result)){
                XSSFRow xssfRow = xssfSheet.createRow(newLineNum);
                for(String s:result.keySet()){

                    if(result.get(s)!=null){

                    }
                }
            }
        }
    }

    private void excelCreat(Map<String,Object> result){
        if(isWhole(result)){
            if(isDuplication(result)){
                File file = new File(pathAndName);
                XSSFWorkbook xssfWorkbook = returnXSSFWorkbook(file,result.keySet());
                if(xssfWorkbook!=null){
                    lineNumber = xssfWorkbook.getSheet("Sheet1").getLastRowNum()+1;
                    add(xssfWorkbook,lineNumber,result);
                    newLineNum = deSet.size();
                    saveExcel(xssfWorkbook);
                }
            }
        }
        if(lineNumber>=maxLineNum){
            StringBuilder pathAndNameMiddle = new StringBuilder();
            pathAndNameMiddle.append(path);
            pathAndNameMiddle.append("\\");
            pathAndNameMiddle.append(name).append("-");
            pathAndNameMiddle.append(format.format(new Date(System.currentTimeMillis())));
            pathAndNameMiddle.append(".xlsx");
            this.pathAndName = pathAndNameMiddle.toString();
            fileNum++;
        }
    }


    /**
     * 返回XSSFWorkbook
     * @param file
     * @return
     */
    private XSSFWorkbook returnXSSFWorkbook(File file,Set<String> set){
        XSSFWorkbook result = null;
        if(file.exists()){
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                result = new XSSFWorkbook(fileInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fileInputStream!=null){
                    try {
                        fileInputStream.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }else {
            result = new XSSFWorkbook();
            XSSFSheet sheet =  result.createSheet("Sheet1");
            XSSFRow row = sheet.createRow(0);
            int cellNum = 0;
            for(String s:set){
                row.createCell(cellNum).setCellValue(s);
                cellNum++;
            }
        }
        return result;
    }

    /**
     *
     * @param str
     * @return
     */
    private String filter(String str) {
        if (str.trim().isEmpty()) {
            return str;
        }
        String pattern = "[\u4E00-\u9FA5]|[\\w]|[,.，。@#*！：!~]";
        Pattern emoji = Pattern.compile(pattern);
        Matcher emojiMatcher = emoji.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (emojiMatcher.find()) {
            sb.append(emojiMatcher.group());
        }
        return sb.toString();
    }

    /**
     *添加新数据到excel
     * @param xssfWorkbook
     * @param rowNum
     * @param map
     */
    private void add(XSSFWorkbook xssfWorkbook,int rowNum,Map<String,Object> map){
        XSSFSheet xssfSheet = xssfWorkbook.getSheet("Sheet1");
        XSSFRow xssfRow = xssfSheet.createRow(rowNum);
        int cellNum = 0;
        for(String s:map.keySet()){
            if(map.get(s)!=null && map.get(s).toString().length()<30000){
                xssfRow.createCell(cellNum).setCellValue( map.get(s).toString());
            }else {
                xssfRow.createCell(cellNum).setCellValue("");
            }
            cellNum++;
        }
    }

    /**
     * 存储Excel数据
     * @param xssfWorkbook
     */
    private void saveExcel(XSSFWorkbook xssfWorkbook){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(pathAndName);
            xssfWorkbook.write(fileOutputStream);
            xssfWorkbook.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断数据的完整性
     * @param map
     * @return
     */
    private boolean isWhole(Map<String,Object> map){
        boolean result = true;
        for(String s:map.keySet()){
            if(!wholeMap.get(s)){
                result = map.get(s)!=null&&"".equals(map.get(s));
            }
        }
        return result;
    }

    /**
     * 重置Excel行中的内容
     * @param xssfWorkbook
     * @param rowNum
     * @param map
     */
    private void replaceExcel(XSSFWorkbook xssfWorkbook,int rowNum,Map<String,Object> map,String primaryKey){
        XSSFSheet xssfSheet = xssfWorkbook.getSheet("Sheet1");
        XSSFRow xssfRow = xssfSheet.getRow(rowNum);
        int cellNum = 0;
        for(String s:map.keySet()){
            if(!s.equals(primaryKey)){
                if(map.get(s)!=null){
                    xssfRow.getCell(cellNum).setCellValue((String) map.get(s));
                }
            }
            cellNum++;
        }
    }


//    /**
//     * 如果主键存在则，如果主键不存在则添加一个新的数据行
//     * @return
//     */
//    private void setPrimaryHash(Map<String,Object> map){
//        if(deMap.containsKey(map.get(primaryKey))){
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append(deMap.get(map.get(primaryKey)));
//            stringBuilder.append(";");
//            for(String s:map.keySet()){
//                stringBuilder.append(map.get(s).hashCode());
//            }
//            deMap.replace((String) map.get(primaryKey),stringBuilder.toString());
//        }else {
//            StringBuilder stringBuilder = new StringBuilder();
//            for (String s:map.keySet()){
//                stringBuilder.append(map.get(s).hashCode());
//            }
//            deMap.put((String) map.get(primaryKey),stringBuilder.toString());
//        }
//    }


    private boolean isDuplication(Map<String,Object> map){
        StringBuilder stringBuilder = new StringBuilder();
        for(String s:map.keySet()){
            if(map.get(s)!=null){
                stringBuilder.append(map.get(s).hashCode());
            }
        }
        return deSet.add(stringBuilder.toString());
    }


    public int getLineNumber() {
        return lineNumber;
    }

    public int getNewLineNum() {
        return newLineNum;
    }
}
