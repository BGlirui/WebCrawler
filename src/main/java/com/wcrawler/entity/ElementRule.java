package com.wcrawler.entity;

import com.wcrawler.global.GlobalNum;
import com.wcrawler.global.GlobalObservableList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ElementRule {
    private final StringProperty ruleTableNum;
    private final StringProperty ruleTableElementRange;
    private final StringProperty ruleTableRuleType;
    private final StringProperty ruleTableName;
    private final StringProperty ruleTableRuleDetails;
    private int ruleElementRangeCode = 0;
    private int ruleElementRuleTypeCode = 0;
    private boolean isRangeCodeEnsure = false;
    private boolean isRuleTypeCodeEnsure = false;
    private final StringProperty ruleTableIsNull;
    private boolean isnull;


    public ElementRule(String ruleTableNum, String ruleTableElementRange, String ruleTableRuleType,String ruleTableName, String ruleTableRuleDetails,boolean isNull) {
        this.ruleTableNum = new SimpleStringProperty(ruleTableNum);
        this.ruleTableElementRange = new SimpleStringProperty(ruleTableElementRange);
        this.ruleTableRuleType = new SimpleStringProperty(ruleTableRuleType);
        this.ruleTableName = new SimpleStringProperty(ruleTableName);
        this.ruleTableRuleDetails = new SimpleStringProperty(ruleTableRuleDetails);
        this.isnull = isNull;
        if(isNull){
            this.ruleTableIsNull = new SimpleStringProperty("可");
        }else {
            this.ruleTableIsNull = new SimpleStringProperty("否");
        }
        isRangeCodeEnsure = whatRange(ruleTableElementRange);
        isRuleTypeCodeEnsure = whatRuleType(ruleTableRuleType);
    }

    public boolean whatRange(String range){
        if(GlobalObservableList.PAGE_URL.equals(range)){
            ruleElementRangeCode = GlobalNum.PAGE_URL;
            return true;
        }else if(GlobalObservableList.PAGE_HTML.equals(range)){
            ruleElementRangeCode = GlobalNum.PAGE_HTML;
            return true;
        }else if(GlobalObservableList.PAGE_JSON.equals(range)){
            ruleElementRangeCode = GlobalNum.PAGE_JSON;
            return true;
        }else {
            return false;
        }
    }
    public boolean whatRuleType(String ruleType){
        if(GlobalObservableList.XPATH.equals(ruleType)){
            ruleElementRuleTypeCode = GlobalNum.XPATH;
            return true;
        }else if(GlobalObservableList.CSS.equals(ruleType)){
            ruleElementRuleTypeCode = GlobalNum.CSS;
            return true;
        }else if(GlobalObservableList.REGEX.equals(ruleType)){
            ruleElementRuleTypeCode = GlobalNum.REGEX;
            return true;
        }else {
            return false;
        }
    }

    public String getRuleTableIsNull() {
        return ruleTableIsNull.get();
    }

    public StringProperty ruleTableIsNullProperty() {
        return ruleTableIsNull;
    }

    public boolean isIsnull() {
        return isnull;
    }

    public void setRuleElementRangeCode(int ruleElementRangeCode) {
        this.ruleElementRangeCode = ruleElementRangeCode;
    }

    public void setRuleTableIsNull(String ruleTableIsNull) {
        this.ruleTableIsNull.set(ruleTableIsNull);
    }

    public void setIsnull(boolean isnull) {
        this.isnull = isnull;
    }

    public int getRuleElementRangeCode() {
        return ruleElementRangeCode;
    }

    public int getRuleElementRuleTypeCode() {
        return ruleElementRuleTypeCode;
    }

    public boolean isRangeCodeEnsure() {
        return isRangeCodeEnsure;
    }

    public boolean isRuleTypeCodeEnsure() {
        return isRuleTypeCodeEnsure;
    }

    public String getRuleTableNum() {
        return ruleTableNum.get();
    }

    public StringProperty ruleTableNumProperty() {
        return ruleTableNum;
    }

    public void setRuleTableNum(String ruleTableNum) {
        this.ruleTableNum.set(ruleTableNum);
    }

    public String getRuleTableElementRange() {
        return ruleTableElementRange.get();
    }

    public StringProperty ruleTableElementRangeProperty() {
        return ruleTableElementRange;
    }

    public void setRuleTableElementRange(String ruleTableElementRange) {
        this.ruleTableElementRange.set(ruleTableElementRange);
    }

    public String getRuleTableRuleType() {
        return ruleTableRuleType.get();
    }

    public StringProperty ruleTableRuleTypeProperty() {
        return ruleTableRuleType;
    }

    public void setRuleTableRuleType(String ruleTableRuleType) {
        this.ruleTableRuleType.set(ruleTableRuleType);
    }

    public String getRuleTableName() {
        return ruleTableName.get();
    }

    public StringProperty ruleTableNameProperty() {
        return ruleTableName;
    }

    public void setRuleTableName(String ruleTableName) {
        this.ruleTableName.set(ruleTableName);
    }

    public String getRuleTableRuleDetails() {
        return ruleTableRuleDetails.get();
    }

    public StringProperty ruleTableRuleDetailsProperty() {
        return ruleTableRuleDetails;
    }

    public void setRuleTableRuleDetails(String ruleTableRuleDetails) {
        this.ruleTableRuleDetails.set(ruleTableRuleDetails);
    }
}
