package com.bw.io;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Класс уровня хранения, обмена и обработки данных.
 *
 * @author Belyavtsev Vladimir Vladimirovich
 */
public class ItemDataHold extends ItemHold {

    /**
     * Параметры
     */
    private HashMap mDataDefault = new HashMap();
    private String sSpliterEqualDefault, sSpliterPairDefault;

    /**
     * Конструкторы
     */
    public ItemDataHold() {
        resetDefault();
    }

    public ItemDataHold(HashMap mData) {
        resetDefault();
        _m(mData);
    }

    public ItemDataHold(String saPair, String sCodepage) {
        resetDefault();
        _m(mPair(saPair, sCodepage));
    }

    /**
     * Сбросить все параметры по умолчанию
     */
    @Override
    public void resetDefault() {
        super.resetDefault();
        mDataDefault = new HashMap();
        sSpliterEqualDefault = "=";
        sSpliterPairDefault = "&";
    }

    /**
     * Получить энкодированную строку параметров из хранящихся параметров, с
     * кодировкой по умолчанию.
     *
     * @return страка списка(массива) пар
     */
    public String saPair() {
        return saPair(mDataDefault, sCodepage());
    }

    /**
     * Получить энкодированную строку параметров из указанной карты и указанием
     * кодировки
     *
     * @param m карта
     * @param sCodepage кодовая страница
     * @return строка (знак разделения название-значение: "=", знак разделения
     * пар: "&")
     */
    public String saPair(Map m, String sCodepage) {
        _LogCase("saPair()");
        _RiseCase("mPair.size()=" + m.size(), "Started...");
        String saPair = "";
        try {
            Iterator om = m.keySet().iterator();
            for (int n = 1; om.hasNext(); n++) {
                String sName = om.next() + "", sValue;
                try {
                    sValue = m.get(sName).toString();
                    String sIncluded = sIncluded(sName, sSpliterEqualDefault, URLEncoder.encode(sValue, sCodepage));
                    _RiseCase("sName=" + sName + ",sValue=" + sValue + ",sCodepage=" + sCodepage + ",sIncluded=" + sIncluded, "TryEncodeAndAppend");
                    saPair += sRight(n > 1 ? sSpliterPairDefault : "", sIncluded);
                } catch (Exception _) {
                    _RiseError(_, "sName=" + sName + ",mPair.size=" + m.size() + ",n=" + n).doThrow();
                }
            }
        } catch (Exception _) {
            _RiseError(_, "m=" + m + ",sCodepage=" + sCodepage);
        }
        _RiseCase("saPair=" + saPair, "Finished!");
        return saPair;
    }

    /**
     * Получить декодированную карту из энкодированной строки пар, с кодировкой
     * по умолчанию.
     *
     * @param saPairEncoded энкодированная строка
     * @return карта
     */
    public HashMap mPair(String saPairEncoded) {
        return mPair(saPairEncoded, sCodepage());
    }

    /**
     * Получить декодированную карту из энкодированной строки пар, с указанием
     * кодировки.
     *
     * @param saPairEncoded энкодированная строка
     * @param sCodepageCustom кодировка
     * @return карта
     */
    public HashMap mPair(String saPairEncoded, String sCodepageCustom) {
        _LogCase("mPairDecoded(saPair,sCodepageCustom)");
        HashMap m = new HashMap();
        if (saPairEncoded != null && !"".equals(saPairEncoded)) {
            try {
                String[] asPair = saPairEncoded.split(sSpliterPairDefault);
                for (int n = 0; n < asPair.length; n++) {
                    String sPair = asPair[n], sName = null, sValue = null;
                    try {
                        int nIndex = sPair.indexOf(sSpliterEqualDefault);
                        sName = nIndex < 0 ? sPair : sPair.substring(0, nIndex);
                        sName = URLDecoder.decode(sName, sCodepageCustom);
                        sValue = nIndex < 0 ? null : sPair.substring(nIndex + 1);
                        sValue = URLDecoder.decode(sValue, sCodepageCustom);
                        m.put(sName, sValue);
                    } catch (Exception _) {
                        _RiseErrorTrace(_, "n=" + n + ",sName=" + sName + ",sValue=" + sValue).doThrow();
                    }
                }
            } catch (Exception _) {
                _RiseErrorTrace(_, "saPairEncoded=" + saPairEncoded, "Breaked iteration!");
            }
        }
        return m;
    }

    /**
     * Преобразовать экранированную строку пар имя-значение в карту
     *
     * @param saPairSheduled строка пар имя-значение (разделенные перечисления
     * "<&>" или "&", разделенные пары "<=>" или "=")
     * @return карта
     */
    public HashMap mPairUnsheduled(String saPairSheduled) {
        HashMap mNew = new HashMap();
        String sSpliterComma = null, sSpliterEqual = null;
        try {
            sSpliterComma = saPairSheduled.indexOf("<&>") < 0 ? "\\&" : "\\<\\&\\>";
            sSpliterEqual = saPairSheduled.indexOf("<=>") < 0 ? "\\=" : "\\<\\=\\>";
            String[] asPair = saPairSheduled.split(sSpliterComma);
            _RiseCase("mPair", "sSpliterComma=" + sSpliterComma + ",sSpliterEqual=" + sSpliterEqual
                    + ",asPair.length=" + asPair.length + ",saPairSheduled=" + saPairSheduled, "GotArrayOfPairs");
            for (int n = 0; n < asPair.length; n++) {
                String sName = null, sValue = null;
                String[] as = null;
                try {
                    as = asPair[n].split(sSpliterEqual);
                    sName = as.length > 0 ? as[0] : "";
                    sValue = as.length > 1 ? as[1] : "";
                    _RiseCase("mPair", "as.length=" + as.length + ",sName=" + sName + ",sValue=" + sValue, "GotPairArray #" + n);
                    mNew.put(sName, sValue);
                    _RiseCase("PutedPairToMap");
                } catch (Exception _) {
                    _RiseErrorTrace(_, "mPair(for)", "as.length=" + as.length + ",sName=" + sName + ",sValue=" + sValue
                            + ",sValue=" + sValue, "Breaked emunerating on #" + n).doThrow();
                }
            }
            _RiseCase("PutedAllPairsToMap");
        } catch (Exception _) {
            _RiseErrorTrace(_, "mPair(saPairSheduled)", "saPairSheduled=" + saPairSheduled + ",sSpliterComma=" + sSpliterComma + ",sSpliterEqual=" + sSpliterEqual);
        }
        return mNew;
    }

    /**
     * @return карта по умолчанию
     */
    public HashMap m() {
        return mDataDefault;
    }

    /**
     * Установить карту по умолчанию
     *
     * @param mDataNew карта
     * @return this
     */
    public ItemDataHold _m(HashMap mDataNew) {
        mDataDefault = mDataNew;
        return this;
    }

    /**
     * Дополнить карту по умолчанию
     *
     * @param mDataUpdate карта
     * @return this
     */
    public ItemDataHold _mUpdate(HashMap mDataUpdate) {
        mDataDefault.putAll(mDataUpdate);
        return this;
    }

    /**
     * @param sName Название элемента из обьекта по умолчанию
     * @return Значение
     */
    public String s(String sName) {
        return s(sName, "");
    }

    /**
     * @param sName Название элемента из обьекта по умолчанию
     * @param sDefault Значение по умолчанию, которое будет возвращено если
     * элемента нет или он пустой
     * @return Значение
     */
    public String s(String sName, String sDefault) {
        String s = sDefault;
        try {
            Object oValue = mDataDefault.containsKey(sName) ? mDataDefault.get(sName) : null;
            s = oValue != null ? oValue + "" : s;//(String)
        } catch (Exception _) {
            _RiseError(_, "s", "sName=" + sName + ",sDefault=" + sDefault);
        }
        return s;
    }

    /**
     * @param sName Название элемента из обьекта по умолчанию
     * @return Значение
     */
    public float d(String sName) {
        return d(sName, 0);
    }

    /**
     * @param sName Название элемента из обьекта по умолчанию
     * @param dDefault Значение по умолчанию, которое будет возвращено если
     * элемента нет или он пустой
     * @return Значение
     */
    public float d(String sName, float dDefault) {
        float d = dDefault;
        try {
            String sValue = s(sName, null);
            d = sValue != null ? Float.parseFloat(!"".equals(sValue) ? sValue.trim() : "0") : d;
        } catch (Exception _) {
            _RiseError(_, "d(float)", "sName=" + sName + ",dDefault=" + dDefault);
        }
        return d;
    }

    /**
     * @param sName Название элемента из обьекта по умолчанию
     * @return Значение
     */
    public int n(String sName) {//Integer
        return n(sName, 0);//Integer.valueOf(0)
    }

    /**
     * @param sName Название элемента из обьекта по умолчанию
     * @param nDefault Значение по умолчанию, которое будет возвращено если
     * элемента нет или он пустой
     * @return Значение
     */
    public int n(String sName, int nDefault) {
        int n = nDefault;
        try {
            String sValue = s(sName, null);
            n = sValue != null ? Integer.parseInt(!"".equals(sValue) ? sValue.trim() : "0") : n;
        } catch (Exception _) {
            _RiseError(_, "n(int)", "sName=" + sName + ",nDefault=" + nDefault);
        }
        return n;
    }

    /**
     * @param sName Название элемента из обьекта по умолчанию
     * @param nDefault Значение по умолчанию, которое будет возвращено если
     * элемента нет или он пустой
     * @return Значение
     */
    public Integer n(String sName, Integer nDefault) {
        Integer n = nDefault;
        try {
            String sValue = s(sName, null);
            n = sValue != null ? Integer.valueOf(!"".equals(sValue) ? sValue.trim() : "0") : n;
        } catch (Exception _) {
            _RiseError(_, "n(Integer)", "sName=" + sName + ",nDefault=" + nDefault);
        }
        return n;
    }

    /**
     * @param sName Название элемента из обьекта по умолчанию
     * @return Значение
     */
    public boolean b(String sName) {
        return b(sName, false);
    }

    /**
     * @param sName Название элемента из обьекта по умолчанию
     * @param bDefault Значение по умолчанию, которое будет возвращено если
     * элемента нет или он пустой
     * @return Значение
     */
    public boolean b(String sName, boolean bDefault) {
        boolean b = bDefault;//|| !Boolean.parseBoolean(s)
        try {
            String sValue = s(sName, null);
            b = !(sValue == null || "".equalsIgnoreCase(sValue)
                    || "false".equalsIgnoreCase(sValue) || "null".equalsIgnoreCase(sValue)
                    || "no".equalsIgnoreCase(sValue) || "not".equalsIgnoreCase(sValue)
                    || "none".equalsIgnoreCase(sValue) || "!".equalsIgnoreCase(sValue));// || !b
        } catch (Exception _) {
            _RiseError(_, "b", "sName=" + sName + ",bDefault=" + bDefault);
        }
        return b;
    }

    /**
     * @param sName Название элемента из обьекта по умолчанию
     * @return Значение
     */
    public Object o(String sName) {
        return o(sName, null);
    }

    /**
     * @param sName Название элемента из обьекта по умолчанию
     * @param oDefault Значение по умолчанию, которое будет возвращено если
     * элемента нет или он пустой
     * @return Значение
     */
    public Object o(String sName, Object oDefault) {
        Object o = oDefault;
        try {
            Object oValue = mDataDefault.containsKey(sName) ? mDataDefault.get(sName) : null;
            o = oValue != null ? oValue : o;
        } catch (Exception _) {
            _RiseError(_, "o", "sName=" + sName + ",oDefault=" + oDefault);
        }
        return o;
    }

    /**
     * @param sName Название элемента из обьекта по умолчанию
     * @return Значение
     */
    public HashMap m(String sName) {
        return m(sName, new HashMap());
    }

    /**
     * @param sName Название элемента из обьекта по умолчанию
     * @param mDefault Значение по умолчанию, которое будет возвращено если
     * элемента нет или он пустой
     * @return Значение
     */
    public HashMap m(String sName, HashMap mDefault) {
        HashMap m = mDefault;
        try {
            Object oValue = mDataDefault.containsKey(sName) ? mDataDefault.get(sName) : null;
            m = oValue != null ? (HashMap) oValue : m;
        } catch (Exception _) {
            _RiseError(_, "m", "sName=" + sName + ",mDefault=" + mDefault);
        }
        return m;
    }

    /**
     * Добавить элемент в обьект по умолчанию
     *
     * @param sName Название элемента
     * @param sValue Значение
     * @return this
     */
    public ItemDataHold _(String sName, String sValue) {
        mDataDefault.put(sName, sValue);
        return this;
    }

    /**
     * Добавить элемент в обьект по умолчанию
     *
     * @param sName Название элемента
     * @param dValue Значение
     * @return this
     */
    public ItemDataHold _(String sName, Float dValue) {
        mDataDefault.put(sName, dValue);
        return this;
    }

    /**
     * Добавить элемент в обьект по умолчанию
     *
     * @param sName Название элемента
     * @param nValue Значение
     * @return this
     */
    public ItemDataHold _(String sName, Integer nValue) {
        mDataDefault.put(sName, nValue);
        return this;
    }

    /**
     * Добавить элемент в обьект по умолчанию
     *
     * @param sName Название элемента
     * @param nValue Значение
     * @return this
     */
    public ItemDataHold _(String sName, int nValue) {
        mDataDefault.put(sName, Integer.valueOf(nValue));
        return this;
    }

    /**
     * Добавить элемент в обьект по умолчанию
     *
     * @param sName Название элемента
     * @param bValue Значение
     * @return this
     */
    public ItemDataHold _(String sName, boolean bValue) {
        mDataDefault.put(sName, Boolean.valueOf(bValue));
        return this;
    }

    /**
     * Добавить элемент в обьект по умолчанию
     *
     * @param sName Название элемента
     * @param oValue Значение
     * @return this
     */
    public ItemDataHold _(String sName, Object oValue) {
        mDataDefault.put(sName, oValue);
        return this;
    }

    /**
     * Получить строку, с подставленными данными вместо тэгов из массива, и
     * определением их по номерам этих тэгов и порядковым номерам названий в
     * массиве (например: <1> - первое значение в массиве)
     *
     * @param sData Исходная строка
     * @param asValue Массив значений
     * @return Результирующая строка
     */
    public String sMergedByIndex(String sData, String[] asValue) {
        String sDataMerged = sData;
        for (int n = 0; n < asValue.length; n++) {
            sDataMerged = sInsert(sDataMerged, n + "", asValue[n]);
        }
        return sDataMerged;
    }

    /**
     * Получить строку, с подставленными данными вместо тэгов, и определением их
     * названий по номерам этих тэгов и названиям в массиве (например: <і> -
     * первое название в массиве)
     *
     * @param sData Исходная строка
     * @param asName Массив названий
     * @return Результирующая строка
     */
    public String sMergedByName(String sData, String[] asName) {
        String sDataMerged = sData;
        for (int n = 0; n < asName.length; n++) {
            sDataMerged = sInsert(sDataMerged, asName[n], s(asName[n]));
        }
        return sDataMerged;
    }

    /**
     * Получить строку, с подставленными данными вместо тэгов, по названиям
     * элементов из элементов обьекта по умолчанию.
     *
     * @param sData Исходная строка
     * @return Результирующая строка
     */
    public String sMerged(String sData) {
        return sMerged(sData, null);
    }

    /**
     * Получить строку, с подставленными данными вместо тэгов, по названиям
     * элементов из элементов обьекта по умолчанию. (с возможностью указания
     * исключающихся названий, хаданных в массиве)
     *
     * @param sData Исходная строка
     * @param asExcludeName массив исключающихся названий параметров
     * @return Результирующая строка
     */
    public String sMerged(String sData, String[] asExcludeName) {
        Iterator oaName = mDataDefault.keySet().iterator();
        String sMerged = sData, sName;
        while (oaName.hasNext()) {
            sName = oaName.next() + "";
            if (asExcludeName != null) {
                boolean bFound = false;
                for (int n = 0; n < asExcludeName.length; n++) {
                    if (sName.equals(asExcludeName[n])) {
                        bFound = true;
                        break;
                    }
                }
                if (!bFound) {
                    sMerged = sInsert(sMerged, sName, s(sName));
                }
            }
        }
        return sMerged;
    }

    /**
     * Получить енкодированную строку с кодировкой по умолчанию
     *
     * @param sDecoded декодированная строка
     * @return энкодированная строка
     * @throws Exception
     */
    public String sEncoded(String sDecoded) throws Exception {
        String sEncoded = "";
        try {
            if (sDecoded != null) {
                sEncoded = URLDecoder.decode(sDecoded, sCodepage());
            }
        } catch (Exception _) {
            _RiseError(_, "sEncoded", "sCodepage=" + sCodepage()).doThrow();
        }
        return sEncoded;
    }

    /**
     * Получить декодированную строку с кодировкой по умолчанию
     *
     * @param sEncoded энкодированная строка
     * @return декодированная строка
     * @throws Exception
     */
    public String sDecoded(String sEncoded) throws Exception {
        String sDecoded = "";
        try {
            if (sEncoded != null) {
                sDecoded = URLDecoder.decode(sEncoded, sCodepage());
            }
        } catch (Exception _) {
            _RiseError(_, "sDecode", "sCodepage=" + sCodepage()).doThrow();
        }
        return sDecoded;
    }

    /**
     * Есть-ли элемент с таким названием, в обьекте по умолчанию
     *
     * @param sName Название элемента
     * @return true, если есть элемент с таким названием
     */
    public boolean bName(String sName) {
        return m().containsKey(sName);
    }

    /**
     * Есть-ли элемент с таким значением, в обьекте по умолчанию
     *
     * @param sValue Название элемента
     * @return true, если есть элемент с таким значением
     */
    public boolean bValue(String sValue) {
        return m().containsValue(sValue);
    }

    /**
     * Получим строку, представляющую обьект в JSON-формате, даже если
     * изначальная была в формате XML
     *
     * @param sData исходная строка, представляющая обьект
     * @return результирующая строка, представляющая обьект
     */
    public String sJSON(String sData) {
        String sJSON = "{}";
        if (sData != null) {
            String sDataNew = sData.trim();
            if (sDataNew.length() > 0) {
                String sCharFirst = sDataNew.charAt(0) + "";
                if ("{".equals(sCharFirst) || "[".equals(sCharFirst)) {
                    sJSON = sDataNew;
                } else if ("<".equals(sCharFirst)) {
                    sJSON = new ItemDataXML(sDataNew).sGetJSON();
                }
            }
        }
        return sJSON;
    }
}