package com.wcrawler.crawler;

import com.wcrawler.global.GlobalNum;
import us.codecraft.webmagic.Page;

public class ByCSS {
    private Page page;
    private String name;
    private String ruleDetails;

    public boolean init(Page page,String name,int range,String ruleDetails){
        this.page = page;
        this.name = name;
        this.ruleDetails = ruleDetails;
        boolean result = false;

        switch (range){
            case GlobalNum.PAGE_HTML:
                putFieldByHtml();
                result = true;
                break;
            case GlobalNum.PAGE_JSON:
                putFieldByJson();
                result = true;
                break;
            case GlobalNum.PAGE_URL:
                putFieldByUrl();
                result = true;
                break;
            default:
                result = false;
        }
        return result;
    }
    /**
     * URL
     */
    private void putFieldByUrl(){
        page.putField(name,page.getUrl().css(ruleDetails).toString());
    }
    /**
     * Html
     */
    private void putFieldByHtml(){
        page.putField(name,page.getHtml().css(ruleDetails).toString());
    }

    /**
     * Json
     */
    private void putFieldByJson(){
        page.putField(name,page.getJson().css(ruleDetails).toString());
    }
}
