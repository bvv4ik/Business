package com.bw.io;

import org.json.JSONObject;
import org.json.XML;

/*
 * Работа с обьектами в формате XML
 * 
 * @author Belyavtsev Vladimir Vladimirovich
 */
public class ItemDataXML extends ItemHold {

    private String sObjectDef = "<?xml version='1.0' encoding='UTF-8' ?>", sObject = sObjectDef;

    public ItemDataXML() {
        _Set(sObjectDef);
    }

    public ItemDataXML(String sObject) {
        _Set(sObject);
    }

    public boolean bEmpty() {
        return sObjectDef.equals(sObject) || !bIs(sObject);
    }

    public String sGet() {
        return sObject;
    }

    public String sGetJSON() {
        resetLogged();
        JSONObject oJSON = null;
        try {
            oJSON = XML.toJSONObject(sObject);
        } catch (Exception _) {
            _RiseError(_, "sGetJSON", "sObject=" + sObject);
        }
        return oJSON.toString();
    }

    final public ItemDataXML _Set(String s) {
        resetLogged();
        sObject = s;
        return this;
    }
}
