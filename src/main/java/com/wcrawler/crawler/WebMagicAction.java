package com.wcrawler.crawler;

import com.change.HttpClientDownloader;
import com.wcrawler.dao.ExcelFilePipeline;
import com.wcrawler.dao.newJsonFilePipeline;
import javafx.scene.control.Label;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.monitor.SpiderStatus;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class WebMagicAction {
    private int retryTime = 30;
    private int sleepTime = 1000;
    private int threadNum = 1;
    private String url;
    private ElementRuleManage elementRuleManage;
    private String link;
    private Spider spider;
    private WebMagicConfig webMagicConfig;
    private ExcelFilePipeline excelFilePipeline;
    private newJsonFilePipeline newJsonFilePipeline;
    private boolean is = false;
    private String path;
    private String name;
    private Map<String,Boolean> wholeMap;
    private int maxLineNum;
    public WebMagicAction(int retryTime, int sleepTime, int threadNum, String url,int maxLineNum, ElementRuleManage elementRuleManage,
                          String link, String path, String name, Map<String,Boolean> wholeMap){
        this.retryTime = retryTime;
        this.sleepTime = sleepTime;
        this.threadNum = threadNum;
        this.url = url;
        this.elementRuleManage = elementRuleManage;
        this.link = link;
        this.path = path;
        this.name = name;
        this.wholeMap = wholeMap;
        this.maxLineNum = maxLineNum;
    }

    public void action(){
        webMagicConfig = new WebMagicConfig(retryTime,sleepTime,elementRuleManage,link);
        excelFilePipeline = new ExcelFilePipeline(path,name,wholeMap,maxLineNum);
        newJsonFilePipeline = new newJsonFilePipeline(path,name,wholeMap,maxLineNum);

        this.spider = Spider.create(webMagicConfig);
        spider.setDownloader(new HttpClientDownloader())
                .addUrl(this.url)
                .addPipeline(newJsonFilePipeline)
                .thread(this.threadNum)
                .start();
        is = true;
    }

    public WebMagicConfig getWebMagicConfig() {
        return webMagicConfig;
    }

    public ExcelFilePipeline getExcelFilePipeline() {
        return excelFilePipeline;
    }

    public com.wcrawler.dao.newJsonFilePipeline getNewJsonFilePipeline() {
        return newJsonFilePipeline;
    }

    public Boolean stop(){
        boolean result = false;
        if(is){
            spider.stop();
        }
        while (is){
            if(spider.getThreadAlive()==0){
                is = false;
                result = true;
            }
        }
        return result;
    }
}
