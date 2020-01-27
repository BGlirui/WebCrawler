package com.wcrawler.crawler;

import com.wcrawler.entity.ElementRule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ElementRuleManage {
    private ObservableList<ElementRule> elementContents = FXCollections.observableArrayList();
    private int numberFlog = 0;
    private boolean isRangeEnsure = true;
    private boolean isRuleTypeEnsure = true;

    public boolean addElementRule(int ruleTableNum, String ruleTableElementRange,String ruleTableRuleType,String name,String ruleTableRuleDetails,boolean isNull){

        elementContents.add(new ElementRule(Integer.toString(ruleTableNum),ruleTableElementRange,ruleTableRuleType,name,ruleTableRuleDetails,isNull));
        if(elementContents.get(elementContents.size()-1).isRangeCodeEnsure()&&elementContents.get(elementContents.size()-1).isRuleTypeCodeEnsure()){
            numberFlog++;
            return true;
        }else {
            isRangeEnsure = elementContents.get(elementContents.size()-1).isRangeCodeEnsure();
            isRuleTypeEnsure = elementContents.get(elementContents.size()-1).isRuleTypeCodeEnsure();
            elementContents.remove(getElementContents().size()-1);
            return false;
        }
    }

    public boolean isRangeEnsure() {
        return isRangeEnsure;
    }

    public boolean isRuleTypeEnsure() {
        return isRuleTypeEnsure;
    }

    public int getNumberFlog() {
        return numberFlog;
    }

    public ObservableList<ElementRule> getElementContents() {
        return elementContents;
    }
}
