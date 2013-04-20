package com.bvv.io;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Работа с таблицей
 *
 * @author Belyavtsev Vladimir Vladimirovich
 */
public class ItemDataTable {
    HashMap m=new HashMap();
    private int nRow=0;
    public ItemDataTable (HashMap mSourceTable){
        m=mSourceTable;
    }
    //-sdfa
    
    public int nRows(String sColumnName) {
        return ((ArrayList<String>)m.get(sColumnName)).size();
    }
    public HashMap m() {
        return m;
    }
    public ItemDataTable _Row(int nRow) {
        this.nRow=nRow;
        return this;
    }
    public String s(String sName) {
        return s(sName, nRow);
    }
    public String s(String sName, int nRow) {
        return ((ArrayList<String>)m.get(sName)).get(nRow);
    }
    public int n(String sName) {
        return n(sName, nRow);
    }
    public int n(String sName, int nRow) {
        return ((ArrayList<Integer>)m.get(sName)).get(nRow);
    }
    public ItemDataTable _(String sName, Integer nValue) {
        return _(sName, nRow, nValue);
    }
    public ItemDataTable _(String sName, int nRow, Integer nValue) {
        //this.nRow=nRow;
        ((ArrayList<Integer>)m.get(sName)).set(nRow, nValue);
        return this;
    }
    public ItemDataTable _(String sName, String sValue) {
        return _(sName, nRow, sValue);
    }
    public ItemDataTable _(String sName, int nRow, String sValue) {
        //this.nRow=nRow;
        ((ArrayList<String>)m.get(sName)).set(nRow, sValue);
        return this;
    }
}
