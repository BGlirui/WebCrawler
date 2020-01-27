package com.wcrawler.crawler;

import com.wcrawler.entity.ElementRule;
import com.wcrawler.global.GlobalNum;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.processor.PageProcessor;

public class WebMagicConfig implements PageProcessor {

    private int retryTime;
    private int sleepTime;
    private Site site;
    private ElementRuleManage elementRuleManage;
    private boolean isInit;
    private ByRegex byRegex;
    private ByXPath byXPath;
    private ByCSS byCSS;
    private String link;
    private int csNum;
    private int unNum;



    public WebMagicConfig(int retryTime, int sleepTime, ElementRuleManage elementRuleManage, String link){
        this.retryTime = retryTime;
        this.sleepTime = sleepTime;
        this.site = Site.me().setRetryTimes(retryTime).setSleepTime(sleepTime);
        this.elementRuleManage = elementRuleManage;
        this.link = link;
        byRegex = new ByRegex();
        byXPath = new ByXPath();
        byCSS = new ByCSS();


    }

    @Override
    public void process(Page page) {
        for(int i = 0;i<elementRuleManage.getElementContents().size();i++){
            putField(page,elementRuleManage.getElementContents().get(i));
        }
        csNum++;
        if(link.equals("null")){
            page.addTargetRequests(page.getHtml().links().all());
            unNum = page.getTargetRequests().size();
        }else {
            page.addTargetRequests(page.getHtml().links().regex(link).all());
            unNum = page.getTargetRequests().size();
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    private boolean putField(Page page, ElementRule elementRule){
        boolean result = false;
        switch (elementRule.getRuleElementRuleTypeCode()){
            case GlobalNum.XPATH:
                result = byXPath.init(page,elementRule.getRuleTableName(),elementRule.getRuleElementRangeCode(),elementRule.getRuleTableRuleDetails());
                break;
            case GlobalNum.REGEX:
                result = byRegex.init(page,elementRule.getRuleTableName(),elementRule.getRuleElementRangeCode(),elementRule.getRuleTableRuleDetails());
                break;
            case GlobalNum.CSS:
                result = byCSS.init(page,elementRule.getRuleTableName(),elementRule.getRuleElementRangeCode(),elementRule.getRuleTableRuleDetails());
                break;
            default:
                result = false;
        }
        return result;
    }

    public int getCsNum() {
        return csNum;
    }

    public int getUnNum() {
        return unNum;
    }
}
