package com.bw.io;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Класс уровня доступа к базам данных.
 *
 * @author Belyavtsev Vladimir Vladimirovich
 */
public class ItemAccessBase extends ItemDataHold {

    /**
     * Параметры
     */
    private Connection oConnection;
    private PreparedStatement oStatement;
    private ResultSet oRowset;
    private int nCount = 0, nRows = 0, nRow = 0;
    private String sFormat = "json", sFrom = "";
    private String sReqFind = "", sNames = "", sWhere = "", sOrder = "", sKeys = "", sSQL = "";
    private boolean bFindN = true, bFindS = true, bAt = false, bTo = false, bRowHolderClearing = false;

    /**
     * Конструкторы
     */
    public ItemAccessBase(ItemDataHold o) {
        resetDefault();
        updateLogged(o);
        _m(o.m());
    }

    public ItemAccessBase(HashMap m) {
        resetDefault();
        _m(m);
    }

    /**
     * Сбросить параметры по умолчанию
     */
    @Override
    public void resetDefault() {
        super.resetDefault();
        _nRows(0);
        resetQuery();
        resetAnswer();
    }

    /**
     * Сбросить параметры запроса
     */
    public void resetQuery() {
        _FROM("");
        _Keys("");
        _Names("*");
        _WHERE("");
        _ORDER("");
        sReqFind = "";
        sSQL = "";
    }

    /**
     * Сбросить параметры ответа
     */
    public void resetAnswer() {
        if (bRowHolderClearing) {
            m().clear();
        }//offLog();
        resetLogged();
    }

    /**
     * Установить вормат возвращаемого обьекта по умолчанию
     *
     * @param sFormatNew название нового вормата по умолчанию
     * @return this
     */
    public ItemAccessBase _Format(String sFormatNew) {
        sFormat = sFormatNew;
        return this;
    }

    /**
     * Установить соединение к базе по умолчанию
     *
     * @param oConnectionNew обьект нового соединения
     * @return this
     */
    public ItemAccessBase _(Connection oConnectionNew) {
        oConnection = oConnectionNew;
        return this;
    }

    /**
     * Установить стэйтмент соединения к базе по умолчанию
     *
     * @param oStatementNew обьект нового стэйтмэнта соединения
     * @return this
     */
    public ItemAccessBase _(PreparedStatement oStatementNew) {
        oStatement = oStatementNew;
        return this;
    }

    /**
     * Установить полученный ровсэт базы по умолчанию
     *
     * @param oRowsetNew обьект полученного ровсэта базы
     * @return this
     */
    public ItemAccessBase _(ResultSet oRowsetNew) {
        oRowset = oRowsetNew;
        return this;
    }

    /**
     * Получить номер полученной строки запроса
     *
     * @return номер
     */
    public int nRow() {
        return nRow;
    }

    /**
     * Получить число строк запроса
     *
     * @return число
     */
    public int nRows() {
        return nRows;
    }

    /**
     * Получить максимальное число строк запроса по умолчанию
     *
     * @return число
     */
    public int nRowsMax() {
        return nCount;
    }

    /**
     * Установить максимальное число строк запроса по умолчанию
     *
     * @return this
     */
    public ItemAccessBase _nRows(int nRowMax) {
        nCount = nRowMax;
        return this;
    }

    public boolean bFindS() {
        return bFindS;
    }

    public boolean bFindN() {
        return bFindN;
    }

    private ItemAccessBase _Find(boolean bUniqS, boolean bUniqN, String sData, String sSplit, String sMixed, String sOnlyString, String sOnlyNumber) {
        int n, i;
        String s, sEq, sO = "";
        boolean bN, bNN, bSS, bNNN = !"".equals(sOnlyNumber), bSSS = !"".equals(sOnlyString), b = (bNNN || bSSS) & !"".equals(sData);
        bFindN = b;
        bFindS = b;
        String[] aS = null;
        if ("".equals(sSplit)) {
            aS[0] = sData;
        } else {
            aS = sData.split(sSplit);
        }
        if (!"".equals(sMixed)) {
            for (i = 0; i < aS.length; i++) {
                s = aS[i];
                if (!"".equals(s)) {
                    bNN = b;
                    bSS = b;
                    sEq = bUniqS ? "='" + s + "'" : " like '" + (bAt ? "" : "%") + s + (bTo ? "" : "%") + "'";
                    if (b) {
                        for (n = 0; n < s.length(); n++) {
                            bN = Character.isDigit(s.charAt(n));
                            bNN &= bN;
                            bSS &= !bN;
                        }
                    }
                    bFindN &= bNN;
                    bFindS &= bSS;
                    sO += (i > 0 ? " and " : "") + (bNN & bNNN ? (bUniqN ? sOnlyNumber + "=" + s : "str(" + sOnlyNumber + ")" + sEq) : (bSS & bSSS ? sOnlyString : sMixed) + sEq);
                }
            }
        }
        sReqFind = sO;
        bAt = false;
        bTo = false;
        return this;
    }

    public ItemAccessBase _Find(String sData, String sSplit, String sMixed) {
        return _Find(false, false, sData, sSplit, sMixed, "", "");
    }

    public ItemAccessBase _Find(String sData, String sSplit, String sMixed, String sOnlyNumber) {
        return _Find(false, false, sData, sSplit, sMixed, "", sOnlyNumber);
    }

    public ItemAccessBase _Find(String sData, String sSplit, String sMixed, String sOnlyString, String sOnlyNumber) {
        return _Find(false, false, sData, sSplit, sMixed, sOnlyString, sOnlyNumber);
    }

    public ItemAccessBase _FindUniq(String sData, String sSplit, String sMixed) {
        return _Find(true, true, sData, sSplit, sMixed, "", "");
    }

    public ItemAccessBase _FindUniq(String sData, String sSplit, String sMixed, String sOnlyNumber) {
        return _Find(true, true, sData, sSplit, sMixed, "", sOnlyNumber);
    }

    public ItemAccessBase _FindUniq(String sData, String sSplit, String sMixed, String sOnlyString, String sOnlyNumber) {
        return _Find(true, true, sData, sSplit, sMixed, sOnlyString, sOnlyNumber);
    }

    public ItemAccessBase _FindUniqS(String sData, String sSplit, String sMixed) {
        return _Find(true, false, sData, sSplit, sMixed, "", "");
    }

    public ItemAccessBase _FindUniqS(String sData, String sSplit, String sMixed, String sOnlyNumber) {
        return _Find(true, false, sData, sSplit, sMixed, "", sOnlyNumber);
    }

    public ItemAccessBase _FindUniqN(String sData, String sSplit, String sMixed, String sOnlyNumber) {
        return _Find(false, true, sData, sSplit, sMixed, "", sOnlyNumber);
    }

    public ItemAccessBase _FindUniqN(String sData, String sSplit, String sMixed, String sOnlyString, String sOnlyNumber) {
        return _Find(false, true, sData, sSplit, sMixed, sOnlyString, sOnlyNumber);
    }

    public ItemAccessBase _At() {
        _At(false);
        return this;
    }

    public ItemAccessBase _At(boolean bOff) {
        bAt = !bOff;
        return this;
    }

    public ItemAccessBase _To() {
        _To(false);
        return this;
    }

    public ItemAccessBase _To(boolean bOff) {
        bTo = !bOff;
        return this;
    }

    /**
     * Установить/снять режим авто-отчистки карты параметров, полученных при
     * чтении строки запроса
     *
     * @param bRowHolderClearingNew true - включить
     * @return this
     */
    public ItemAccessBase _MapCleaning(boolean bRowHolderClearingNew) {
        bRowHolderClearing = bRowHolderClearingNew;
        resetAnswer();
        return this;
    }

    /**
     * Получена-ли новая строка запроса (с помещением параметров строки вкарту)
     *
     * @return true - получена
     */
    public boolean bMapRow() {
        return bMapRow(oRowset);
    }

    /**
     * Получена-ли новая указанная строка запроса (с помещением параметров
     * строки вкарту)
     *
     * @return true - получена
     */
    public boolean bMapRow(ResultSet oRowset) {
        boolean b = false;
        HashMap oMapRow = new HashMap();
        int nM = 0, n = 0;
        String sName = "", sValue = "";
        try {
            if (oRowset != null) {
                b = oRowset.next();
                if (b) {
                    nRow++;
                    nM = oRowset.getMetaData().getColumnCount();
                    for (n = 1; n <= nM; n++) {
                        sName = oRowset.getMetaData().getColumnName(n);
                        sValue = oRowset.getString(n);
                        oMapRow.put(sName, sValue);
                    }
                }
            }
            if (bRowHolderClearing) {
                _m(oMapRow);
            } else {
                _mUpdate(oMapRow);
            }
        } catch (Exception _) {
            _RiseErrorTrace(_, "bMapRow", "b=" + b + ",n=" + n + ",nM=" + nM + ",sName=" + sName + ",sValue=" + sValue);
        }
        return b;
    }

    /**
     * Получить присвоенный новой(добавленной) записи - номер/идентификатор
     *
     * @return номер
     */
    public int nID() {
        int n = 0;
        try {
            oRowset = oConnection.prepareStatement("SELECT @@identity").executeQuery();
            n = oRowset.next() ? Integer.parseInt(oRowset.getString(1)) : 0;
        } catch (Exception _) {
            _RiseErrorTrace(_, "nID", "n=" + n);
        }
        return n;
    }

    /**
     * Получить сформированный или заданный SQL-запрос
     *
     * @return строка
     */
    public String sSQL() {
        return sSQL;
    }

    private void makeSQL(HashMap oMap, String sFrom, String sNames, String sKeys, String sWhere) {
        _m(oMap);
        _FROM("".equals(sFrom) ? this.sFrom : sFrom);
        _Names(sNames);
        _Keys(sKeys);
        _WHERE(sWhere);
    }

    public ItemAccessBase _SQL(String sFrom, String sNames) {
        makeSQL(m(), sFrom, sNames, sKeys, sWhere);
        return this;
    }

    public ItemAccessBase _SQL(String sFrom, String sNames, String sKeys) {
        makeSQL(m(), sFrom, sNames, sKeys, sWhere);
        return this;
    }

    public ItemAccessBase _SQL(String sFrom, String sNames, String sKeys, String sWhere) {
        makeSQL(m(), sFrom, sNames, sKeys, sWhere);
        return this;
    }

    public ItemAccessBase _SQL(HashMap oMap) {
        makeSQL(oMap, sFrom, sNames, sKeys, sWhere);
        return this;
    }

    public ItemAccessBase _SQL(HashMap oMap, String sFrom) {
        makeSQL(oMap, sFrom, sNames, sKeys, sWhere);
        return this;
    }

    public ItemAccessBase _SQL(HashMap oMap, String sFrom, String sNames) {
        makeSQL(oMap, sFrom, sNames, sKeys, sWhere);
        return this;
    }

    public ItemAccessBase _SQL(HashMap oMap, String sFrom, String sNames, String sKeys) {
        makeSQL(oMap, sFrom, sNames, sKeys, sWhere);
        return this;
    }

    public ItemAccessBase _SQL(HashMap oMap, String sFrom, String sNames, String sKeys, String sWhere) {
        makeSQL(oMap, sFrom, sNames, sKeys, sWhere);
        return this;
    }

    public String sSelect() {
        return sSelect(sNames);
    }

    public String sSelect(String sNames) {
        StringTokenizer oS, oSs;
        String sAt, sTo;
        int n = 0;
        oS = new StringTokenizer(sNames, ",");
        String s = "";
        while (oS.hasMoreTokens()) {
            n++;
            sTo = "";
            oSs = new StringTokenizer(oS.nextToken(), "=");
            if (oSs.hasMoreTokens()) {
                sTo = oSs.nextToken().trim();
            }
            sAt = sTo;
            if (oSs.hasMoreTokens()) {
                sAt = oSs.nextToken().trim();
            }
            s += (n > 1 ? "," : "") + sAt;
        }
        return "SELECT " + ("".equals(s) ? "*" : s);
    }

    private String sNames(HashMap oMap, String sNames) {
        Iterator oIT = oMap.values().iterator();
        String sNew = sNames.trim(), s = "";
        int i;
        sNew = "".equals(sNew) ? "," : sNew;
        boolean bPre = ",".equals(sNew.charAt(0) + "");
        if (bPre || ",".equals(sNew.charAt(sNew.length() - 1) + "")) {
            for (i = 0; oIT.hasNext(); i++) {
                s += (i > 0 ? "," : "") + oIT.next();
            }
            if (bPre) {
                sNew = s + (sNew.length() > 1 ? "".equals(s) ? sNew.substring(1) : sNew : "");
            } else {
                sNew += ("".equals(s) ? "" : ",") + s;
            }
        } else {
            while (sNew.indexOf("?") >= 0) {
                if (oIT.hasNext()) {
                    sNew = sNew.replaceFirst("\\?", oIT.next() + "");
                }
            }
        }
        return sNew;
    }

    public ItemAccessBase _Names(String saNameNew) {
        sNames = saNameNew;
        return this;
    }

    public String sFrom() {
        return sFrom(sFrom);
    }

    public String sFrom(String sFrom) {
        return " FROM " + ("".equals(sFrom) ? this.sFrom : sFrom);
    }

    public ItemAccessBase _FROM(String sFromNew) {
        sFrom = sFromNew;
        return this;
    }

    public String sWhere() {
        return sWhere(sWhere);
    }

    public String sWhere(String sWhere) {
        String sNew = sWhere, sS, s;
        if (!"".equals(sKeys)) {
            StringTokenizer oS = new StringTokenizer(sKeys, ",");
            int n = 0;//sAt,sTo,//,oSs
            while (oS.hasMoreTokens()) {
                n++;
                s = oS.nextToken().trim();
                if (!"".equals(s)) {
                    sS = s.charAt(0) + "";
                    if (!Character.isLowerCase(s.charAt(0))) {
                        sS = "";
                    }
                    sNew += ("".equals(sNew) ? "" : " AND ") + s + "=" + ("n".equals(sS) ? n(s) + "" : ("b".equals(sS) ? b(s) + "" : ("d".equals(sS) ? d(s) + "" : "'" + s(s) + "'")));
                }
            }//sWhere="".equals(sWhere)?"":" AND "+sReqKeys;
        }
        if (!"".equals(sReqFind)) {
            sNew += ("".equals(sNew) ? "" : " AND ") + sReqFind;
        }
        return "".equals(sNew) ? "" : " WHERE " + sNew;
    }

    public ItemAccessBase _WHERE(String sWhereNew) {
        sWhere = sWhereNew;
        return this;
    }

    public ItemAccessBase _Keys(String saKeyNew) {
        sKeys = saKeyNew;
        return this;
    }

    public String sOrder() {
        return sOrder(sOrder);
    }

    public String sOrder(String sOrderNew) {
        String s = "".equals(sOrderNew) ? sOrder : sOrderNew;
        return "".equals(s) ? "" : " ORDER BY " + s;
    }

    public ItemAccessBase _ORDER(String sOrderNew) {
        sOrder = sOrderNew;
        return this;
    }

    public String sGET() {
        return sGET(sSQL);
    }

    public String sGET(String sSQL) {
        String sNamesAll = sNames(m(), sNames);
        _RiseCase("sGET", "sNamesAll=" + sNamesAll, "0");
        return sGet(sNamesAll, oGET(sSQL));
    }

    public ItemAccessBase _GET() {
        oGET();
        return this;
    }

    public ItemAccessBase _GET(String sSQL) {
        oGET(sSQL);
        return this;
    }

    public ResultSet oGET() {
        return oGET(sSQL);
    }

    public ResultSet oGET(String sSQL) {
        resetLogged();
        String sSQLnew = "".equals(sSQL) ? sSelect() + sFrom() + sWhere() + sOrder() : sSQL;
        oRowset = null;
        if (!"".equals(sSQLnew)) {
            if (nCount > 0) {
                sSQLnew = ("SELECT TOP " + nCount + (sSQLnew.replaceFirst("\\QSELECT\\E", "")));
            }
            try {
                nRow = 0;
                _RiseCase("oGET", "sSQL=" + sSQLnew, "try...");
                oRowset = oConnection.prepareStatement(sSQLnew).executeQuery();
                _RiseCase("oGET", "", "ok!");
            } catch (Exception _) {
                _RiseErrorTrace(_, "oGET", "sSQLnew=" + sSQLnew);
            }
        }
        resetQuery();
        resetAnswer();
        return oRowset;
    }

    private String sGet(String sNames, ResultSet oRowset) {
        String sNamesNew = sNames, sGot = "", sAt, sTo, s = "";
        StringTokenizer oS, oSs, oSss;
        ArrayList osAt = new ArrayList(), osTo = new ArrayList();
        _RiseCase("sGet", "osAt.size=" + osAt.size() + ",osTo.size=" + osTo.size() + ",oRowset != null:" + (oRowset != null), "1");
        int nCol = 0, n = 0, i = 0, nM = 0;
        try {
            if (oRowset != null) {
                nM = oRowset.getMetaData().getColumnCount();
                if ("*".equals(sNamesNew)) {
                    for (n = 1; n < nM; n++) {
                        s += ",";
                        sNamesNew = s;
                    }
                }
                oS = new StringTokenizer(sNamesNew, ",");
                while (oS.hasMoreTokens()) {
                    nCol++;
                    sTo = "";
                    s = "";
                    oSs = new StringTokenizer(oS.nextToken(), "=");
                    n = 0;
                    if (oSs.hasMoreTokens()) {
                        sTo = oSs.nextToken().trim();
                    }
                    oSss = new StringTokenizer(sTo, ".");
                    while (oSss.hasMoreTokens()) {
                        sTo = oSss.nextToken().trim();
                    }
                    sAt = sTo;
                    if (oSs.hasMoreTokens()) {
                        sAt = oSs.nextToken().trim();
                    }
                    oSss = new StringTokenizer(sAt, ".");
                    while (oSss.hasMoreTokens()) {
                        sAt = oSss.nextToken().trim();
                    }
                    if (sAt.length() > 0) {
                        if (Character.isDigit(sAt.charAt(0))) {
                            n = Integer.parseInt(sAt);
                        }
                    }
                    if (n > 0) {
                        s = n > nM ? "" : oRowset.getMetaData().getColumnLabel(n);
                        sAt = s;
                    } else if ("?".equals(sAt)) {
                        i++;
                        s = i > nM ? "" : oRowset.getMetaData().getColumnLabel(i);
                        sAt = s;
                    } else if ("".equals(sAt)) {
                        s = nCol > nM ? "" : oRowset.getMetaData().getColumnLabel(nCol);
                        sAt = s;
                    } else {
                        for (n = 1; n <= nM; n++) {
                            s = oRowset.getMetaData().getColumnLabel(n);
                            if (s.equals(sAt)) {
                                break;
                            }
                            s = "";
                        }
                    }
                    if (!"".equals(s)) {
                        osAt.add(sAt);
                        osTo.add(sTo);
                    }
                }
                if ("json".equals(sFormat)) {
                    sGot = sGetJSON(oRowset, osAt, osTo);
                }
            }
        } catch (Exception _) {
            _RiseErrorTrace(_, "sGet", "sNamesNew=" + sNamesNew + ",s=" + s + ",nCol=" + nCol + ",nM=" + nM + ",n=" + n + ",i=" + i);
        }
        return sGot;
    }

    private String sGetJSON(ResultSet oRowset, ArrayList osAt, ArrayList osTo) {
        String s = "", sV = "";
        int n = 0, i = 0;
        nRow = 0;
        ArrayList osToNew = osTo;
        String sRes = "";
        try {
            if (osAt.isEmpty()) {
                int nM = oRowset.getMetaData().getColumnCount();
                for (i = 1; i <= nM; i++) {
                    osAt.add(oRowset.getMetaData().getColumnLabel(i));//getColumnName(i)
                }
                osToNew = osAt;//System.out.println("sCol#"+i+":"+oRowset.getMetaData().getColumnName(i));
            }
            while (oRowset.next()) {
                nRow++;
                i = 0;
                s = "";
                i++;
                for (n = 0; n < osAt.size(); n++) {
                    sV = oRowset.getString(osAt.get(n) + "");
                    if (sV == null) {
                        sV = "";
                    }
                    s += (n > 0 ? "," : "") + "\"" + osToNew.get(n) + "\":\"" + sV.replaceAll("\"", "\\\\\"") + "\"";//.replaceAll("\"","´")//.replaceAll("\"","&quot;")
                }
                sRes += (nRow > 1 ? "," : "") + "{" + s + "}";
            }
            nRows = nRow;
            _Rise("sGetJSON", "nRows=" + nRows, sRes.length() + "-bytes");
            _RiseCase("sGetJSON", "sRes=" + sRes, "_");
        } catch (Exception _) {
            _RiseErrorTrace(_, "sGetJSON", "sNames=" + sNames + ",s=" + s + ",sV=" + sV + ",n=" + n + ",i=" + i);
        }
        return sRes;
    }
}