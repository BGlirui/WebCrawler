package com.wcrawler.dao;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class newJsonFilePipeline extends FilePersistentBase implements Pipeline {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private int maxLineNum;
    private int nowLineNum = 0;
    private int sumLineNum = 0;

    private transient Set<String> deSet = new HashSet<>();
    private Map<String,Boolean> wholeMap;
    private SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss");
    private String name;
    private String excelPath;
    private GetJsonToExcel getJsonToExcel = new GetJsonToExcel();


    public newJsonFilePipeline(String excelPath,String name,Map<String,Boolean> wholeMap,int maxLineNum){
        setPath(excelPath+"/"+name+"-"+format.format(new Date(System.currentTimeMillis())));
        this.wholeMap = wholeMap;
        this.maxLineNum = maxLineNum;
        this.name = name;
        this.excelPath = excelPath;
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(getFile(this.path +"/"+ "head" + ".json")));
            printWriter.write(JSON.toJSONString(wholeMap));
            printWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        if(isWhole(resultItems.getAll())){
            if(isDuplication(resultItems.getAll())){
                try{
                    PrintWriter printWriter = new PrintWriter(new FileWriter(getFile(this.path +"/"+ DigestUtils.md5Hex(resultItems.getRequest().getUrl()) + ".json")));
                    printWriter.write(JSON.toJSONString(resultItems.getAll()));
                    printWriter.close();
                    nowLineNum++;
                    sumLineNum++;
                    if(nowLineNum>=maxLineNum){
                        getJsonToExcel.input(excelPath);
                        for(String s:getJsonToExcel.getFileMap().keySet()){
                            getJsonToExcel.toExcel(s,excelPath+"/"+s+".xlsx");
                        }
                        setPath(excelPath+"/"+name+"-"+format.format(new Date(System.currentTimeMillis()))+"/");
                        nowLineNum=0;
                        PrintWriter printWriterNew = new PrintWriter(new FileWriter(getFile(this.path +"/"+ "head" + ".json")));
                        printWriterNew.write(JSON.toJSONString(wholeMap));
                        printWriterNew.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
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
     * 去重复
     * @param map
     * @return
     */
    private boolean isDuplication(Map<String,Object> map){
        StringBuilder stringBuilder = new StringBuilder();
        for(String s:map.keySet()){
            if(map.get(s)!=null){
                stringBuilder.append(map.get(s).hashCode());
            }
        }
        return deSet.add(stringBuilder.toString());
    }

    public int getNowLineNum() {
        return nowLineNum;
    }

    public int getSumLineNum() {
        return sumLineNum;
    }
}
