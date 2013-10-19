package com.bw.io;

import java.io.InputStream;

/**
 * Базовый класс в котором реализованы основные примитивные функции, в т.ч.
 * логирование При дальнейшем наследовании возможно переопределение двух
 * методов-логеров, для вывода через другой инструмент (а не в системный лог)
 *
 * @author Belyavtsev Vladimir Vladimirovich
 */
public class ItemHold extends _ {

    public String sName() {
        return "";
    }
    /**
     * Параметры
     */
    private Exception oExceptionDefault;
    private String saLoggedAlert, saLoggedHoldAlert, saLoggedHoldInfo, saLoggedHoldAll, saLoggedError, saLoggedWarn, saLog, saBuffer, sLog, sLogged, sLogType, sAttach, sPrefix, sLogCase, sOnLogAll, saOnLogCase, sOnLogCase, sClass = getClass().getName(), sCaseDefault, saCase;
    private String sCodepageDefault;
    private String[] asOnLogCase;
    private int nErrors, nWarns, nLogged;
    private boolean bLogHoldDefault, bTest = false;

    /**
     * Конструкторы
     */
    public ItemHold() {
        resetDefault();
    }

    public ItemHold(String sClassName) {
        resetDefault();
        sClass = sClassName;
    }

    public ItemHold(Object oClassName) {
        resetDefault();
        sClass = oClassName.getClass().getName();
    }

    /**
     * Сбросить все логирование ошибок по умолчанию
     */
    public void resetLoggedErrors() {
        nErrors = 0;
        saLoggedError = "";
    }

    /**
     * Сбросить все логирование предупреждений по умолчанию
     */
    public void resetLoggedWarns() {
        nWarns = 0;
        saLoggedWarn = "";
    }

    /**
     * Сбросить все логирование по умолчанию
     */
    public void resetLogged() {
        resetLoggedErrors();
        resetLoggedWarns();
        nLogged = 0;
        saLoggedAlert = "";
        saLoggedHoldAlert = "";
        saLoggedHoldInfo = "";
        saLoggedHoldAll = "";
        saLog = "";
        sLog = "";
        sAttach = "";
        saBuffer = "";
        saCase = "";
    }

    /**
     * Сбросить все параметры по умолчанию
     */
    public void resetDefault() {
        sCodepageDefault = "UTF-8";
        sPrefix = "<br>\n";
        sLogCase = null;
        sCaseDefault = "";
        bLogHoldDefault = false;
        resetLogged();
    }

    /**
     * Обновить данные логирования из другого обьекта
     *
     * @param o внешний обьект
     */
    public void updateLogged(ItemHold o) {
        nErrors += o.nErrors();
        nWarns += o.nWarns();
        nLogged += o.nLogged();
        saLoggedAlert = sRight(o.saLoggedAlert(), saLoggedAlert);
        saLoggedHoldAlert = sRight(o.saLoggedHoldAlert(), saLoggedHoldAlert);
        saLoggedHoldInfo = sRight(o.saLoggedHoldInfo(), saLoggedHoldInfo);
        saLoggedHoldAll = sRight(o.saLoggedHoldAll(), saLoggedHoldAll);
        saLoggedError = sRight(o.saLoggedError(), saLoggedError);
        saLoggedWarn = sRight(o.saLoggedWarn(), saLoggedWarn);
        saLog = sRight(o.saLog(), saLog);
        sLog = o.sLog();
        _Right(o.saBuffer());
        onLogCase(o.saOnLogCase());
        onLogAll(o.sOnLogAll());
    }

    /**
     * Проверка включенности режима тестирования
     *
     * @return true, если режим тестировани включен
     */
    public boolean bTest() {
        return bTest;
    }

    /**
     * Влючение режима тестирования
     *
     * @return this
     */
    public ItemHold _Test() {
        bTest = true;
        return this;
    }

    /**
     * Установка включенности/выключенности режима тестирования
     *
     * @return this
     */
    public ItemHold _Test(boolean bTestNew) {
        bTest = bTestNew;
        if (bTest) {
            _Rise("_ItemHold: Enabled test mode!");
        }
        return this;
    }

    /**
     * @return Получить название кодовой страницы по умолчанию
     */
    public String sCodepage() {
        return sCodepageDefault;

    }

    /**
     * Установить кодовую страницу по умолчанию
     *
     * @return this
     */
    public ItemHold _Codepage(String sCodepageNew) {
        if (sCodepageNew != null) {
            sCodepageDefault = sCodepageNew;
        }
        return this;
    }

    /**
     * @return Получить название класса по умолчанию
     */
    public String sClass() {
        return sClass;
    }

    /**
     * Установить название класса по умолчанию
     *
     * @param sClassNameNew название класса
     * @return this
     */
    public ItemHold _Class(String sClassNameNew) {
        sClass = sClassNameNew;
        return this;
    }

    /**
     * Установить название класса по умолчанию
     *
     * @param oClassNew обьект класса
     * @return this
     */
    public ItemHold _Class(Object oClassNew) {
        sClass = oClassNew.getClass().getName();
        return this;
    }

    /**
     * @return Получить случай/тэг по умолчанию
     */
    public String sCase() {
        return sCaseDefault;
    }

    /**
     * Установить случай/тэг по умолчанию
     *
     * @param sCaseNew строка-тэг
     * @return this
     */
    public ItemHold _Case(String sCaseNew) {
        sCaseDefault = sCaseNew;
        return this;
    }

    /**
     * @return Получить строку-список случаев/тэгов, по которым была проверка
     */
    public String saCase() {
        return saCase;
    }

    /**
     * Проверка полного совпадения случая/тэга по умолчанию с указанной строкой
     *
     * @param sCompareName строка для сравнения
     * @return true, если случай/тэг по умолчанию совпал с указанным
     */
    public boolean bCase(String sCompareName) {
        return bCase(sCompareName, sCaseDefault);
    }

    /**
     * Проверка полного совпадения указанного случая/тэга с указанной строкой
     *
     * @param sCompareName строка для сравнения
     * @param sCaseCustom строка случая для сравнения
     * @return true, если случай/тэг совпал с указанной строкой
     */
    public boolean bCase(String sCompareName, String sCaseCustom) {
        saCase = sRight(saCase, ",", sCompareName);
        return sCaseCustom == null ? sCompareName == null : sCaseCustom.equals(sCompareName);
    }

    /**
     * Проверка совпадения случая/тэга по умолчанию с указанной строкой
     * (безразличен регистр букв)
     *
     * @param sCompareName строка для сравнения
     * @return true, если случай/тэг по умолчанию совпал с указанным
     */
    public boolean bCaseAs(String sCompareName) {
        return bCaseAs(sCompareName, sCaseDefault);
    }

    /**
     * Проверка совпадения указанного случая/тэга с указанной строкой
     * (безразличен регистр букв)
     *
     * @param sCompareName строка для сравнения
     * @param sCaseCustom строка случая для сравнения
     * @return true, если случай/тэг совпал с указанной строкой
     */
    public boolean bCaseAs(String sCompareName, String sCaseCustom) {
        saCase = sRight(saCase, ",", sCompareName);
        return sCaseCustom == null ? sCompareName == null : sCaseCustom.equalsIgnoreCase(sCompareName);
    }

    /**
     * Проверка совпадения случая/тэга по умолчанию с указанной строкой (по
     * окончанию)
     *
     * @param sCompareName строка для сравнения
     * @return true, если случай/тэг по умолчанию совпал с указанным
     */
    public boolean bCaseAsTo(String sCompareName) {
        return bCaseAsTo(sCompareName, sCaseDefault);
    }

    /**
     * Проверка совпадения указанного случая/тэга с указанной строкой (по
     * окончанию)
     *
     * @param sCompareName строка для сравнения
     * @param sCaseCustom строка случая для сравнения
     * @return true, если случай/тэг совпал с указанной строкой
     */
    public boolean bCaseAsTo(String sCompareName, String sCaseCustom) {
        return sCaseCustom == null ? sCompareName == null : sCaseCustom.endsWith(sCompareName);
    }

    /**
     * Проверка совпадения случая/тэга по умолчанию с указанной строкой (по
     * окончанию)
     *
     * @param sCompareName строка для сравнения
     * @return true, если случай/тэг по умолчанию совпал с указанным
     */
    public boolean bCaseAsAt(String sCompareName) {
        return bCaseAsAt(sCompareName, sCaseDefault);
    }

    /**
     * Проверка совпадения указанного случая/тэга с указанной строкой (по
     * началу)
     *
     * @param sCompareName строка для сравнения
     * @param sCaseCustom строка случая для сравнения
     * @return true, если случай/тэг совпал с указанной строкой
     */
    public boolean bCaseAsAt(String sCompareName, String sCaseCustom) {
        return sCaseCustom == null ? sCompareName == null : sCaseCustom.startsWith(sCompareName);
    }

    private String sLogCase() {
        return sLogCase;
    }

    private String sLogCase(String sCaseUp) {
        String sCaseNew = sLogCase(), sCaseUpNew = sCaseUp;
        if (sCaseUpNew != null && sCaseNew != null) {
            if (!sCaseNew.equals(sCaseUpNew)) {
                if (sCaseNew.endsWith(":" + sCaseUpNew)) {
                    sCaseUpNew = null;
                }
            }
        }
        sCaseNew = sRight(sCaseNew, ":", sS(sCaseUpNew));
        return sCaseNew;
    }

    /**
     * Установить случай/тэк по умолчанию, для фильтра логирования
     *
     * @param sCaseNew идентификатор
     * @return this
     */
    public ItemHold _LogCase(String sCaseNew) {
        sLogCase = sCaseNew;
        return this;
    }

    /**
     * Доростить случай/тэк по умолчанию, для фильтра логирования (к
     * существующему)
     *
     * @param sCaseUp идентификатор-дополнение
     * @return this
     */
    public ItemHold _LogCaseUp(String sCaseUp) {
        sLogCase = sLogCase(sCaseUp);
        return this;
    }

    /**
     * Установить хранение полноформатного логирования
     *
     * @param bHoldNew true = включить
     * @return this
     */
    public ItemHold _LogHold(boolean bHoldNew) {
        bLogHoldDefault = bHoldNew;
        return this;
    }

    /**
     * @return true, если были-ли ошибки или предупреждения
     */
    public boolean bAlerts() {
        return bErrors() || bWarns();
    }

    /**
     * @return true, если были-ли ошибки
     */
    public boolean bErrors() {
        return bErrors(0);
    }

    private boolean bErrors(int nErrorsWas) {
        return nErrors() != nErrorsWas;
    }

    /**
     * @return true, если были-ли предупреждения
     */
    public boolean bWarns() {
        return bWarns(0);
    }

    private boolean bWarns(int nWarnsWas) {
        return nWarns() != nWarnsWas;
    }

    /**
     * @return true, если были-ли логирования
     */
    public boolean bLogged() {
        return bLogged(0);
    }

    private boolean bLogged(int nLoggedWas) {
        return nLogged() != nLoggedWas;
    }

    /**
     * @return Число ошибок
     */
    public int nErrors() {
        return nErrors;
    }

    /**
     * @return Число предупреждений
     */
    public int nWarns() {
        return nWarns;
    }

    /**
     * @return Число сделанных логов
     */
    public int nLogged() {
        return nLogged;
    }

    /**
     * @return Последняя строка логирования
     */
    public String sLogged() {
        return sS(sLogged);
    }

    /**
     * @return Последняя строка логирования (кратко)
     */
    public String sLog() {
        return sS(sLog);
    }

    /**
     * @return Строка-список логирования (кратко)
     */
    public String saLog() {
        return sS(saLog);
    }

    /**
     * @return Строка-список логирования ошибок
     */
    public String saLoggedError() {
        return sS(saLoggedError);
    }

    /**
     * @return Строка-список логирования предупреждений
     */
    public String saLoggedWarn() {
        return sS(saLoggedWarn);
    }

    /**
     * @return Строка-список логирования ошибок и предупреждений
     */
    public String saLoggedAlert() {
        return sS(saLoggedAlert);
    }

    /**
     * @return Строка-список сохраненного логирования ошибок и предупреждений
     * (полноформатного)
     */
    public String saLoggedHoldAlert() {
        return sS(saLoggedHoldAlert);
    }

    /**
     * @return Строка-список сохраненного логирования без ошибок и
     * предупреждений (полноформатного)
     */
    public String saLoggedHoldInfo() {
        return sS(saLoggedHoldInfo);
    }

    /**
     * @return Строка-список сохраненного всего логирования (полноформатного)
     */
    public String saLoggedHoldAll() {
        return sS(saLoggedHoldAll);
    }

    /**
     * Новый-ли лог/ошибка/предупреждение/трэйс, или это проброс предыдущего
     */
    private boolean bLogNew() {
        if (oExceptionDefault != null) {
            if (sLog == null || !sLog.equals(oExceptionDefault.getMessage())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Сделать "бросок" (throw new Exception, с установленной последней строкой
     * логирования sLog())
     *
     * @throws Exception
     */
    public void doThrow() throws Exception {
        throw new Exception(sLog());
    }

    /**
     * Поднять ошибку
     *
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseError(String sMessage) {
        return _RiseError(null, null, null, sMessage);
    }

    /**
     * Поднять ошибку
     *
     * @param saArgument аргументы, в контексте
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseError(String saArgument, String sMessage) {
        return _RiseError(null, null, saArgument, sMessage);
    }

    /**
     * Поднять ошибку
     *
     * @param sCase случай/тэг, характеризующую место происшествия
     * @param saArgument аргументы, в контексте
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseError(String sCase, String saArgument, String sMessage) {
        return _RiseError(null, sCase, saArgument, sMessage);
    }

    /**
     * Поднять ошибку и вывести TraceStack
     *
     * @param _ обьект Exception
     * @return this
     */
    public ItemHold _RiseErrorTrace(Exception _) {
        return _RiseErrorTrace(_, null, null, null);
    }

    /**
     * Поднять ошибку и вывести TraceStack
     *
     * @param _ обьект Exception
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseErrorTrace(Exception _, String sMessage) {
        return _RiseError(_, null, null, sMessage);
    }

    /**
     * Поднять ошибку и вывести TraceStack
     *
     * @param _ обьект Exception
     * @param saArgument аргументы, в контексте
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseErrorTrace(Exception _, String saArgument, String sMessage) {
        return _RiseError(_, null, saArgument, sMessage);
    }

    /**
     * Поднять ошибку и вывести TraceStack
     *
     * @param _ обьект Exception
     * @param sCase случай/тэг, характеризующую место происшествия
     * @param saArgument аргументы, в контексте
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseErrorTrace(Exception _, String sCase, String saArgument, String sMessage) {
        return _RiseError(_, sCase, saArgument, sMessage);
    }

    /**
     * Поднять ошибку
     *
     * @param _ обьект Exception
     * @return this
     */
    public ItemHold _RiseError(Exception _) {
        return _RiseError(_, null, null, null);
    }

    /**
     * Поднять ошибку
     *
     * @param _ обьект Exception
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseError(Exception _, String sMessage) {
        return _RiseError(_, null, null, sMessage);
    }

    /**
     * Поднять ошибку
     *
     * @param _ обьект Exception
     * @param saArgument аргументы, в контексте
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseError(Exception _, String saArgument, String sMessage) {
        return _RiseError(_, null, saArgument, sMessage);
    }

    /**
     * Поднять ошибку
     *
     * @param _ обьект Exception
     * @param sCase случай/тэг, характеризующую место происшествия
     * @param saArgument аргументы, в контексте
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseError(Exception _, String sCase, String saArgument, String sMessage) {
        sLogType = "ERROR";
        return _Log(sCase, saArgument, sMessage, _, true);
    }

    /**
     * Поднять предупреждение и вывести TraceStack
     *
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseWarn(String sMessage) {
        return _RiseWarn(null, null, null, sMessage);
    }

    /**
     * Поднять предупреждение и вывести TraceStack
     *
     * @param saArgument аргументы, в контексте
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseWarn(String saArgument, String sMessage) {
        return _RiseWarn(null, null, saArgument, sMessage);
    }

    /**
     * Поднять предупреждение и вывести TraceStack
     *
     * @param sCase случай/тэг, характеризующую место происшествия
     * @param saArgument аргументы, в контексте
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseWarn(String sCase, String saArgument, String sMessage) {
        return _RiseWarn(null, sCase, saArgument, sMessage);
    }

    /**
     * Поднять предупреждение и вывести TraceStack
     *
     * @param _ обьект Exception
     * @return this
     */
    public ItemHold _RiseWarnTrace(Exception _) {
        return _RiseWarnTrace(_, null, null, null);
    }

    /**
     * Поднять предупреждение и вывести TraceStack
     *
     * @param _ обьект Exception
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseWarnTrace(Exception _, String sMessage) {
        return _RiseWarn(_, null, null, sMessage);
    }

    /**
     * Поднять предупреждение и вывести TraceStack
     *
     * @param _ обьект Exception
     * @param saArgument аргументы, в контексте
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseWarnTrace(Exception _, String saArgument, String sMessage) {
        return _RiseWarn(_, null, saArgument, sMessage);
    }

    /**
     * Поднять предупреждение и вывести TraceStack
     *
     * @param _ обьект Exception
     * @param sCase случай/тэг, характеризующую место происшествия
     * @param saArgument аргументы, в контексте
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseWarnTrace(Exception _, String sCase, String saArgument, String sMessage) {
        return _RiseWarn(_, sCase, saArgument, sMessage);
    }

    /**
     * Поднять предупреждение
     *
     * @param _ обьект Exception
     * @return this
     */
    public ItemHold _RiseWarn(Exception _) {
        return _RiseWarn(_, null, null, null);
    }

    /**
     * Поднять предупреждение
     *
     * @param _ обьект Exception
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseWarn(Exception _, String sMessage) {
        return _RiseWarn(_, null, null, sMessage);
    }

    /**
     * Поднять предупреждение
     *
     * @param _ обьект Exception
     * @param saArgument аргументы, в контексте
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseWarn(Exception _, String saArgument, String sMessage) {
        return _RiseWarn(_, null, saArgument, sMessage);
    }

    /**
     * Поднять предупреждение
     *
     * @param _ обьект Exception
     * @param sCase случай/тэг, характеризующую место происшествия
     * @param saArgument аргументы, в контексте
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseWarn(Exception _, String sCase, String saArgument, String sMessage) {
        sLogType = "WARN";
        return _Log(sCase, saArgument, sMessage, _, true);
    }

    /**
     * Поднять сообщение при наступлении указанного случая/тэга
     *
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseCase(String sMessage) {
        return _RiseCase(null, null, sMessage);
    }

    /**
     * Поднять сообщение при наступлении указанного случая/тэга
     *
     * @param saArgument аргументы, в контексте
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseCase(String saArgument, String sMessage) {
        return _RiseCase(null, saArgument, sMessage);
    }

    /**
     * Поднять сообщение при наступлении указанного случая/тэга
     *
     * @param sCase случай/тэг, характеризующую место происшествия
     * @param saArgument аргументы, в контексте
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _RiseCase(String sCase, String saArgument, String sMessage) {
        sLogType = "Case";
        return _Log(sCase, saArgument, sMessage, null, false);
    }

    /**
     * Поднять сообщение (всегда)
     *
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _Rise(String sMessage) {
        return _Rise(null, null, sMessage);
    }

    /**
     * Поднять сообщение (всегда)
     *
     * @param saArgument аргументы, в контексте
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _Rise(String saArgument, String sMessage) {
        return _Rise(null, saArgument, sMessage);
    }

    /**
     * Поднять сообщение (всегда)
     *
     * @param sCase случай/тэг, характеризующую место происшествия
     * @param saArgument аргументы, в контексте
     * @param sMessage основное сообщение
     * @return this
     */
    public ItemHold _Rise(String sCase, String saArgument, String sMessage) {
        sLogType = null;//Always
        return _Log(sCase, saArgument, sMessage, null, false);
    }

    /**
     * Ядро подготовки и сохраниние строки логирования (краткой)
     */
    private ItemHold _Log(String sCase, String saArgument, String sMessage, Exception _, boolean bAlert) {
        oExceptionDefault = _;
        String sMessageNew = sMessage == null ? "" : sMessage;
        boolean bLogNew = bAlert && bLogNew();
        if (bLogNew) {
            sMessageNew += ":" + _.getMessage();
        }
        String sCaseNew = sLogCase(sCase);
        if ("ERROR".equals(sLogType)) {
            nErrors++;
            _Log_("", sCaseNew, saArgument, sMessageNew);
            log(sLogType + (bLogNew ? "" : "(throw)") + "_" + sClass + sLog, bAlert);
            saLoggedError = sRight(sLogged, saLoggedError);
            saLoggedAlert = sRight(sLogged, saLoggedAlert);
        } else if ("WARN".equals(sLogType)) {
            nWarns++;
            _Log_("", sCaseNew, saArgument, sMessageNew);
            log(sLogType + (bLogNew ? "" : "(throw)") + "_" + sClass + sLog, bAlert);
            saLoggedWarn = sRight(sLogged, saLoggedWarn);
            saLoggedAlert = sRight(sLogged, saLoggedAlert);
        } else if ("Case".equals(sLogType)) {
            if (bOnLogAll() || bOnLogCase(sCaseNew)) {
                _Log_(bOnLogAll() ? "" : sIncluded("@", sOnLogCase(), "@"), sCaseNew, saArgument, sMessageNew);
                log(sLogType + "{" + sS(sOnLogAll) + "}" + sClass + sLog, bAlert);
            } else {
                clearLog();
            }
        } else {
            _Log_("", sCaseNew, saArgument, sMessageNew);
            log("{" + sS(sOnLogAll) + "}" + sClass + sLog, bAlert);
        }
        return this;
    }

    /**
     * Подготовка и сохраниние строки логирования (краткой)
     */
    private ItemHold _Log_(String sPrefix, String sCaseNew, String saArgument, String sMessage) {
        sLog = sPrefix + sIncluded("[", sCaseNew, "]") + sIncluded("(", saArgument, ")") + ":" + sMessage;
        saLog = sRight(sLog, saLog);
        return this;
    }

    /**
     * Подготовка и сохраниние строки логирования
     */
    private void log(String sMessage, boolean bAlert) {
        nLogged++;
        sLogged = "Rise" + sMessage;//"_Log"+
        if (bLogHoldDefault) {
            String sMessageFull = sLogPrefix() + sMessage;
            if (bAlert) {
                saLoggedHoldAlert = sRight(sMessageFull, saLoggedHoldAlert);
            } else {
                saLoggedHoldInfo = sRight(sMessageFull, saLoggedHoldInfo);
            }
            saLoggedHoldAll = sRight(sMessageFull, saLoggedHoldAll);
        }
        logging(sLogged, oExceptionDefault, bAlert, bLogNew());
        oExceptionDefault = null;
        sLogType = null;
    }

    /**
     * (для переопределения) Логирование (общий оконечный метод)
     *
     * @param sMessage данные для логирования
     * @param _ обьект Exception
     * @param bAlert true, если нужно логировать как ошибку
     * @param bTrace true, если нужно выводить StackTrace
     */
    public void logging(String sMessage, Exception _, boolean bAlert, boolean bTrace) {
        if (bAlert && bTrace && _ != null) {
            _.printStackTrace(System.err);
        }
        if (sMessage != null) {
            if (bAlert) {
                System.err.println(sMessage);
            } else {
                System.out.println(sMessage);
            }
        }
    }

    /**
     * Отчистка текцщей строки логирования
     */
    private void clearLog() {
        sLog = "";
    }

    /**
     * @return префикс, при логировании
     */
    public String sLogPrefix() {
        String sLogPrefix = sDT("yyyy-MM-dd_HH:mm:ss.SSS");
        return sLogPrefix + (sLogPrefix == null ? "" : "_");
    }

    /**
     * Прикрепить одну строку к другой (по умолчанию) - справа (через указанный
     * префикс, указанный по умолчанию) (только при не пустом и одном и другом
     * параметре)) Новая строка станет умолчательной (другой)
     *
     * @param sAttach прикрепляемая строка (одна)
     * @return this
     */
    public ItemHold _Right(String sAttach) {
        saBuffer = sRight(saBuffer, sPrefix, sAttach);
        return this;
    }

    /**
     * Прикрепить одну строку к другой (по умолчанию) - слева (через указанный
     * префикс, указанный по умолчанию) (только при не пустом и одном и другом
     * параметре)) Новая строка станет умолчательной (другой)
     *
     * @param sAttach прикрепляемая строка (одна)
     * @return this
     */
    public ItemHold _Left(String sAttach) {
        saBuffer = sLeft(sAttach, sPrefix, saBuffer);
        return this;
    }

    /**
     * Прикрепить одну строку (по умолчанию) к другой (по умолчанию) - справа
     * (через указанный префикс, указанный по умолчанию) (только при не пустом и
     * одном и другом параметре)) Новая строка станет умолчательной (другой)
     *
     * @return this
     */
    public ItemHold _Right() {
        saBuffer = sRight(saBuffer, sPrefix, sAttach);
        return this;
    }

    /**
     * Прикрепить одну строку (по умолчанию) к другой (по умолчанию) - слева
     * (через указанный префикс, указанный по умолчанию) (только при не пустом и
     * одном и другом параметре)) Новая строка станет умолчательной (другой)
     *
     * @return this
     */
    public ItemHold _Left() {
        saBuffer = sLeft(sAttach, sPrefix, saBuffer);
        return this;
    }

    /**
     * Получить прикрепляемую строку по умолчанию
     *
     * @return this
     */
    public String sAttach() {
        return sAttach;
    }

    /**
     * Установить прикрепляемую строку по умолчанию
     *
     * @param sNew новая строка
     * @return this
     */
    public ItemHold _Attach(String sNew) {
        sAttach = sNew;
        return this;
    }

    /**
     * Получить строку-массив(список) к которой будет прикрепление по умолчанию
     *
     * @return this
     */
    public String saBuffer() {
        return saBuffer;
    }

    /**
     * Установить строку-массив(список) к которой будет прикрепление по
     * умолчанию
     *
     * @param saNew новая строка
     * @return this
     */
    public ItemHold _Buffer(String saNew) {
        saBuffer = saNew;
        return this;
    }

    /**
     * Получить строку-префикс, для вставки между двумя прикрепляемыми строками
     * по умолчанию
     *
     * @return this
     */
    public String sPrefix() {
        return sPrefix;
    }

    /**
     * Установить строку-префикс, для вставки между двумя прикрепляемыми
     * строками по умолчанию
     *
     * @param sNew глвый префикс
     * @return this
     */
    public ItemHold _Prefix(String sNew) {
        sPrefix = sNew;
        return this;
    }

    /**
     * Включено-ли тотальное логирование
     *
     * @return true, если включено
     */
    public boolean bOnLogAll() {
        return sOnLogAll != null;
    }

    /**
     * Включено-ли логирование "по случаям/тэгам"
     *
     * @param sFindCase искомый кейс/случая
     * @return true, если включено
     */
    public boolean bOnLogCase(String sFindCase) {
        if (asOnLogCase != null) {
            for (int n = 0; n < asOnLogCase.length; n++) {
                if (asOnLogCase[n].matches(sFindCase)) {
                    sOnLogCase = sFindCase;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Получение строки последнего "случая/тэга" логирования
     *
     * @return строка
     */
    public String sOnLogCase() {
        return sOnLogCase;
    }

    /**
     * Получение строки "случаев/тэгов" логирования
     *
     * @return строка-список
     */
    public String saOnLogCase() {
        return saOnLogCase;
    }

    /**
     * Получение строки названия, для отображения в логе, при тотальном
     * логировании
     *
     * @return строка
     */
    public String sOnLogAll() {
        return sOnLogAll;
    }

    /**
     * Включить тотальное логирование
     *
     * @param sName название, для отображения в логе
     */
    public void onLogAll(String sName) {
        sOnLogAll = sName;
    }

    /**
     * Включить логирование "по случаям/тэгам"
     *
     * @param saCaseName строка-список(массив) стучаев/кейсов
     */
    public void onLogCase(String saCaseName) {
        saOnLogCase = saCaseName;
        if (saOnLogCase == null) {
            asOnLogCase = null;
            return;
        }
        if (saOnLogCase.indexOf("debug") >= 0) {
            onLogAll(saOnLogCase.replaceAll("\\Qdebug\\E", ""));
        } else {
            asOnLogCase = saOnLogCase.split("\\,");
        }
    }

    /**
     * Выключить тотальное логирование
     */
    public void offLogAll() {
        onLogAll(null);
    }

    /**
     * Выключить логирование "по случаям/тэгам"
     */
    public void offLogCase() {
        onLogCase(null);
    }

    /**
     * Выключить логирование (вообще)
     */
    public void offLog() {
        offLogAll();
        offLogCase();
    }

    /**
     * Копирование данных из входящего потока в исходящий, и возврат его.
     *
     * @param oStreamAt входящий поток
     * @return исходящий поток
     */
    public java.io.ByteArrayOutputStream oStreamCopy(InputStream oStreamAt) {
        java.io.ByteArrayOutputStream oStreamTo = new java.io.ByteArrayOutputStream();
        int nData, n = 0;
        byte oByte[] = new byte[1024];
        try {
            for (; (nData = oStreamAt.read(oByte, 0, 1024)) > 0; n++) {
                oStreamTo.write(oByte, 0, nData);
            }
        } catch (Exception _) {
            _RiseError(_, "oStreamCopy(oStreamAt)", "n=" + n, "");
        }
        return oStreamTo;
    }

    /**
     * Преобразовать исходящий поток в строку (с кодировкой по умолчанию).
     *
     * @param oStream поток вывода
     * @return строка
     */
    public String sStream(java.io.ByteArrayOutputStream oStream) {
        return sStream(oStream, sCodepage());
    }

    /**
     * Преобразовать исходящий поток в строку, с заданной кодировкой.
     *
     * @param oStream конвертируемый поток
     * @param sCodepage название кодировки
     * @return строка
     */
    public String sStream(java.io.ByteArrayOutputStream oStream, String sCodepage) {
        String sStream = null;
        try {
            sStream = new String(oStream.toByteArray(), sCodepage);
        } catch (Exception _) {
            _RiseError(_, "sStream(oStreamTo,sCodepage)", "sCodepage=" + sCodepage, "");
        }
        return sStream;
    }

    /**
     * Преобразовать поток ввода в строку (с кодировкой по умолчанию).
     *
     * @param oStreamAt поток ввода
     * @return строка
     */
    public String sStream(InputStream oStreamAt) {
        return sStream(oStreamCopy(oStreamAt));
    }
}
