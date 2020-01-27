package com.wcrawler.crawler;

import com.wcrawler.global.GlobalNum;

public class GetElementConfig {
    private String regex = "";
    private String xpath = "";
    private String css = "";
    private int funCode = 0;
    public boolean isGetFromPage = false;

    /**
     * 获得元素获取的代码
     * @param funCode 功能码 1.regex；2。xpath；3.css
     * @param content 交互窗口传回的元素获取代码
     */
    public GetElementConfig(int funCode,String content){
        this.funCode = funCode;
        switch (funCode){
            case GlobalNum.REGEX:
                this.regex = content;
                this.xpath = "";
                this.css = "";
                isGetFromPage = true;
                break;
            case GlobalNum.XPATH:
                this.xpath = content;
                this.regex = "";
                this.css = "";
                isGetFromPage = true;
                break;
            case GlobalNum.CSS:
                this.css = content;
                this.regex = "";
                this.xpath = "";
                isGetFromPage = true;
                break;
            default:
                this.regex = "";
                this.xpath = "";
                this.css = "";
                isGetFromPage = false;
                break;
        }
    }

    public String getRegex() {
        return regex;
    }

    public String getXpath() {
        return xpath;
    }

    public String getCss() {
        return css;
    }

    public int getFunCode() {
        return funCode;
    }
}
