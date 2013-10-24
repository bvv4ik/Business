package com.bw.io;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * Базовый класс в котором реализованы основные примитивные статические функции
 *
 * @author Belyavtsev Vladimir Vladimirovich
 */
public abstract class _ {

    abstract public String sName();

    /**
     * @param sFormat формат даты
     * @return строка форматированной текущей даты
     */
    public static String sDT(String sFormat) {
        //return sFormat != null ? new SimpleDateFormat(sFormat).format(GregorianCalendar.getInstance().getTime()) : null;
        return sDT(sFormat, GregorianCalendar.getInstance().getTime());
    }

    
     /**
      *  Объединяет два объекта Json (содержащих экранирование)
      * @param sInput  1-я строка, например: String s = "{\"sReturn\":\"Error, ошибка в сервлете \"}";
      * @param sAdd   - 2-я строка 
      * @return
      */
     public static String ConcatJson (String sInput, String sAdd) {
     String s = "";
     s = sInput.substring(0, sInput.length()-1) +", "+ sAdd.substring(1,sAdd.length());    
     //   String s = "{\"sReturn\":\"Error, ошибка в сервлете \"}";
     //   {"name":"foo","num":100,"balance":1000.21,"is_vip":true,"nickname":null}
          return s;
     }
     
    
    
    /**
     * @param sFormat формат даты
     * @return строка форматированной текущей даты
     */
    public static String sDT(String sFormat, java.util.Date oDate) {
        return sFormat != null ? new SimpleDateFormat(sFormat).format(oDate) : null;
    }

    /**
     * @param sFormat формат даты
     * @return строка форматированной текущей даты
     */
    public static String sDateMax(java.util.Date oDate) {
        return sDT("yyyy-MM-dd_HH:mm:ss.SSS", oDate);
    }

    /**
     * Получить строку, окружонную префиксом и постфиксом, если она не пустая
     *
     * @param sPrefix строка - префикс
     * @param sMessage основная, обравляемая строка
     * @param sPostfix строка - постфикс
     * @return результирующая строка
     */
    public static String sIncluded(String sPrefix, String sMessage, String sPostfix) {
        return bIs(sMessage) ? sPrefix + sMessage + sPostfix : "";
    }

    /**
     * Есть-ли содержимое или нет
     *
     * @param sValue строка с проверяемым значением
     * @return true, если не пустая строка и не null
     */
    public static boolean bIs(String sValue) {
        return sValue != null && !"".equals(sValue);
    }

    /**
     * Есть-ли содержимое или нет
     *
     * @param cValue мапа с проверяемым значением
     * @return true, если не пустая строка и не null
     */
    public static <T> boolean bIs(Collection<T> cValue) {
        return cValue != null && !cValue.isEmpty();
    }

    /**
     * Есть-ли содержимое или нет
     *
     * @param mValue мапа с проверяемым значением
     * @return true, если не пустая строка и не null
     */
    public static <T> boolean bIs(Map<T, T> mValue) {
        return mValue != null && !mValue.isEmpty();
    }

    /**
     * Возврат только строки(вместо null будет пустая строка)
     *
     * @param sValue проверяемая строка
     * @return строка
     */
    public static String sS(String sValue) {
        return sNull(sValue, "");
    }

    /**
     * Возврат только строки(вместо null будет пустая строка)
     *
     * @param oValue проверяемый обьект, который будет превращен в строку
     * @return строка
     */
    public static String sO(Object oValue) {
        return sNull(oValue, "");
    }

    /**
     * Возврат только числа(вместо null будет ноль)
     *
     * @param oValue проверяемый обьект, который будет превращен в число
     * @return строка
     */
    public static int nO(Object oValue) {
        String s = sNull(oValue, "0");
        int n = 0;
        try {
            n = Integer.valueOf(s);
        } catch (Exception ex) {
        }
        return n;
    }

    /**
     * Возврат строки значения, а если оно = null, то будет возвращена строка
     * "по умолчанию")
     *
     * @param sValue проверяемая строка
     * @param sDefault строка "по умолчанию", которая вернется вместо null
     * @return строка
     */
    public static String sNull(String sValue, String sDefault) {
        return sValue != null ? sValue : sDefault;
    }

    /**
     * Возврат строки значения, а если оно = null, то будет возвращена строка
     * "по умолчанию")
     *
     * @param oValue проверяемый обьект, который будет превращен в строку
     * @param sDefault строка "по умолчанию", которая вернется вместо null
     * @return строка
     */
    public static String sNull(Object oValue, String sDefault) {
        return oValue != null ? oValue.toString() : sDefault;
    }

    /**
     * Возврат строки значения, а если она "пустая" или null, то будет
     * возвращена строка "по умолчанию")
     *
     * @param sValue проверяемая строка
     * @param sDefault строка "по умолчанию", которая вернется вместо "пустой"
     * строки или null
     * @return строка
     */
    public static String sNone(String sValue, String sDefault) {
        return bIs(sValue) ? sValue : sDefault;
    }

    /**
     * Возврат строки, с заменой встречающегося тэга на указанное значение (два
     * основных варианта префиксов и постфиксов)
     *
     * @param sInto исходная строка, в которой будет произведена подмена
     * @param sName название тэга
     * @param sValue строка, которая будет подставлена на место встретевшигося
     * тэга
     * @return обработанная строка
     */
    public static String sInsert(String sInto, String sName, String sValue) {
        return sInsert(sInto, sName, sValue, "<,&lt;".split(","), ">,&gt;".split(","));
    }

    /**
     * Возврат строки, с заменой встречающегося тэга на указанные значение, с
     * разными обромлениями, которые указаны в массиве
     *
     * @param sInto исходная строка, в которой будет произведена подмена
     * @param sName название тэга
     * @param sValue строка, которая будет подставлена на место встретевшигося
     * тэга
     * @param asPrefix массив префиксов тэга
     * @param asPostfix массив постфиксов тэга
     * @return обработанная строка
     */
    public static String sInsert(String sInto, String sName, String sValue, String[] asPrefix, String[] asPostfix) {
        int nCount = asPrefix.length < asPostfix.length ? asPrefix.length : asPostfix.length;
        String sNew = sInto;
        for (int n = 0; n < nCount; n++) {
            sNew = sInsert(sNew, sName, sValue, asPrefix[n], asPostfix[n]);
        }
        return sNew;
    }

    /**
     * Возврат строки, с заменой встречающегося тэга на указанное значение (один
     * вариант префикса и постфикса)
     *
     * @param sInto исходная строка, в которой будет произведена подмена
     * @param sName название тэга
     * @param sValue строка, которая будет подставлена на место встретевшигося
     * тэга
     * @return обработанная строка
     */
    public static String sInsert(String sInto, String sName, String sValue, String sPrefix, String sPostfix) {
        return sInto.replaceAll("\\Q" + sPrefix + sName + sPostfix + "\\E", sValue);
    }

    /**
     * Прикрепить одну строку к другой - справа (через указанный префикс)
     * (только при не пустом и одном и другом параметре))
     *
     * @param sBuffer строка, к которой будет прикрепление (другая)
     * @param sPrefix префикс между соединяемыми строками (вставится только если
     * и одна и другая строка не пустая)
     * @param sAttach прикрепляемая строка (одна)
     * @return обработанная строка
     */
    public static String sRight(String sBuffer, String sPrefix, String sAttach) {
        return sS(sBuffer) + (bIs(sAttach) && bIs(sBuffer) ? sPrefix : "") + sS(sAttach);
    }

    /**
     * Прикрепить одну строку к другой - слева (через указанный префикс) (только
     * при не пустом и одном и другом параметре))
     *
     * @param sAttach прикрепляемая строка (одна)
     * @param sPrefix префикс между соединяемыми строками (вставится только если
     * и одна и другая строка не пустая)
     * @param sBuffer строка, к которой будет прикрепление (другая)
     * @return обработанная строка
     */
    public static String sLeft(String sAttach, String sPrefix, String sBuffer) {
        return sS(sAttach) + (bIs(sAttach) && bIs(sBuffer) ? sPrefix : "") + sS(sBuffer);
    }

    /**
     * Вернуть исходную строку с префиксом, если не пустая.
     *
     * @param sPrefix строка префикса
     * @param sAttach исходная строка
     * @return this
     */
    public static String sRight(String sPrefix, String sAttach) {
        return bIs(sAttach) ? sS(sPrefix) + sAttach : "";
    }

    /**
     * Вернуть исходную строку с постфиксом, если не пустая.
     *
     * @param sPrefix строка префикса
     * @param sAttach исходная строка
     * @return this
     */
    public static String sLeft(String sAttach, String sPrefix) {
        return bIs(sAttach) ? sAttach + sS(sPrefix) : "";
    }

    public static String sReplaced(String sWhat, String sAt, String sTo) {
        String sReturn = sWhat;
        if (null != sReturn && null != sAt && null != sTo) {
            for (int indx = 0, last = 0; (indx = sReturn.indexOf(sAt, last)) != -1;) {
                sReturn = sReturn.substring(0, indx) + sTo + sReturn.substring(indx + sAt.length());
                last = indx + sTo.length();
            }
        }
        return sReturn;
    }

    public static boolean bNumber(String sValue) throws NumberFormatException {
        boolean b=false;
        try {
            Integer.parseInt(sValue);
            b=true;
        } catch (NumberFormatException e) {
        } return b;
    }

}
