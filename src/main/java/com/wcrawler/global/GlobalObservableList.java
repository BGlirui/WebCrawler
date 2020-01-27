package com.wcrawler.global;

import com.wcrawler.crawler.GetElementConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class GlobalObservableList {
    private ObservableList<String> observableListRange = FXCollections.observableArrayList();
    private ObservableList<String> observableListRuleType = FXCollections.observableArrayList();
    public static final String PAGE_HTML = "Html";
    public static final String PAGE_JSON = "Json";
    public static final String PAGE_URL = "Url";
    public static final String CSS = "CSS";
    public static final String XPATH = "XPath";
    public static final String REGEX = "Regex";

    private ObservableList<Integer> observableListThreadNumber = FXCollections.observableArrayList();
    private ObservableList<Integer> observableListReTryNumber = FXCollections.observableArrayList();
    public GlobalObservableList(){
        //observableListRange.add("Target Requests");
        observableListRange.add(PAGE_HTML);
        //observableListRange.add("Headers");
        //observableListRange.add("Charset");
        //observableListRange.add("Byte");
        observableListRange.add(PAGE_JSON);
        //observableListRange.add("RawText");
        //observableListRange.add("Request");
        //observableListRange.add("Result Items");
        //observableListRange.add("Status Code");
        observableListRange.add(PAGE_URL);

        observableListRuleType.add(CSS);
        observableListRuleType.add(XPATH);
        observableListRuleType.add(REGEX);

        for(int i=1;i<=10;i++){
            observableListThreadNumber.add(i);
            observableListReTryNumber.add(i);
        }

    }

    public ObservableList<Integer> getObservableListReTryNumber() {
        return observableListReTryNumber;
    }

    public ObservableList<Integer> getObservableListThreadNumber() {
        return observableListThreadNumber;
    }

    public ObservableList<String> getObservableListRange() {
        return observableListRange;
    }

    public ObservableList<String> getObservableListRuleType() {
        return observableListRuleType;
    }
}
