package com.bw.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Класс уровня доступа, авторизации и валидации прав.
 *
 * @author Belyavtsev Vladimir Vladimirovich
 */
public class ItemAccessData extends ItemDataHold {

    /**
     * Параметры
     */
    private HttpServletRequest oRequestDefault;
    private HttpSession oSessionDefault;
    private HttpServletResponse oResponseDefault;
    private HashMap mGrant = new HashMap(), mRequestDefault = new HashMap(), mSessionDefault = new HashMap();
    private String sGrant, sAuth, sLogin, sRole, sAccessBy, sRedirectURL, sNameAuth, sNameGrant, sNameLogin, sNameRole, sRoleAdmin, sLoginDebug;
    private boolean bGranted, bAgreed, bIncludeSessionData;
    private int nGrant;

    /**
     * Конструкторы
     */
    public ItemAccessData() {
        super();
        resetDefault();
    }

    public ItemAccessData(HashMap mData) {
        super(mData);
        resetDefault();
    }

    public ItemAccessData(String saPair, String sCodepageCustom) {
        super(saPair, sCodepageCustom);
        resetDefault();
    }

    public ItemAccessData(HttpServletRequest oRequest, HttpServletResponse oResponse) {
        resetDefault();
        _Request(oRequest);
        _Response(oResponse);
    }

    /**
     * Получение ответа - является-ли указанный сервер тестовым.
     *
     * @param sServer ip-адрес или домен сервера для проверки
     * @return true, если тестовый
     */
    public boolean bTestServer(String sServer) {
        return sServer != null && (sServer.equalsIgnoreCase("localhost"));
    }

    /**
     * Получение ответа - является-ли указанный логин тестовым.
     *
     * @param sLogin логин для проверки
     * @return true, если тестовый
     */
    public boolean bTestLogin(String sLogin) {
        return sLogin != null && (sLogin.equalsIgnoreCase(sLoginDebug()));
    }

    /**
     * Сбросить все параметры по умолчанию
     */
    @Override
    public void resetDefault() {
        super.resetDefault();
        sRedirectURL = sS(sRedirectURL);
        sNameAuth = sNull(sNameAuth, "sAuth");
        sNameGrant = sNull(sNameGrant, "sGrant");
        sNameLogin = sNull(sNameLogin, "sLogin");
        sNameRole = sNull(sNameRole, "sRole");
        sRoleAdmin = sNull(sRoleAdmin, "9");
        sLoginDebug = sNull(sLoginDebug, "DN310780BVV");
        mRequestDefault = new HashMap();
        mSessionDefault = new HashMap();
        oRequestDefault = null;
        oSessionDefault = null;
        oResponseDefault = null;
        bIncludeSessionData = false;
        resetAccessFull();
    }

    /**
     * Сбросить доступ (отменить полученный)
     */
    public void resetAccess() {
        bGranted = false;
        bAgreed = false;
    }

    /**
     * Сбросить доступ полностью (включая логин, и др.параметры)
     */
    public void resetAccessFull() {
        resetAccess();
        sGrant = null;
        sAuth = null;
        sLogin = null;
        sRole = null;
        sAccessBy = "";
    }

    /**
     * Выполнение действий перед сбросом сессии! (сброс сессии: resetSession(),
     * обработка после - resetSessionAfter())
     */
    public void resetSessionBefore() {
        //pdoc.immortal.CookieManager.delete(o.oRequest(),oResponse);
    }

    /**
     * Выполнение действий сброса сессии! (обработка перед -
     * resetSessionBefore(), обработка после - resetSessionAfter())
     */
    public void resetSession() {
        resetSessionBefore();
        resetAccessFull();
        _RiseCase("resetSession()");
        _SessionValue(sNameLogin, null);
        _SessionValue(sNameRole, null);
        resetSessionAfter();
    }

    /**
     * Выполнение действий после сброса сессии! (сброс сессии: resetSession(),
     * обработка перед - resetSessionBefore())
     */
    public void resetSessionAfter() {
        //o.oSession().invalidate();
    }

    /**
     * Проверка - есть-ли обьект запроса
     *
     * @return true, если есть
     */
    public boolean bRequest() {
        return oRequest() != null;
    }

    /**
     * Получить обьект запроса.
     *
     * @return обьект
     */
    public HttpServletRequest oRequest() {
        return oRequestDefault;
    }

    /**
     * Получить карту с параметрами текущего запроса по умолчанию
     *
     * @return карта
     */
    public HashMap mRequest() {
        return mRequest(false);
    }

    /**
     * Получить карту с параметрами текущего запроса по умолчанию, и
     * возможностью парсинга тела
     *
     * @return карта
     */
    public HashMap mRequest(boolean bParseBody) {
        HashMap m = mRequestDefault;
        if (bParseBody) {
            m.putAll(mRequestParsedPostBody());
            mRequestDefault = m;
            _mUpdate(m);
        }
        return m;
    }

    /**
     * Парсит тело запроса(поток), вытягивает от туда параметры и помещает их в
     * карту (используется в случаях не работоспособности автоматизированной
     * функции стандартных библиотек Апликэйшин-сервера (например Jaguar))
     */
    public HashMap mRequestParsedPostBody() {
        String sBody = null;
        if (bRequest()) {
            StringBuilder osBuffer = new StringBuilder();
            String sLine = null;
            try {
                BufferedReader oReader = new BufferedReader(new InputStreamReader(oRequest().getInputStream(), Charset.forName(sCodepage())));//"UTF-8"
                while (null != (sLine = oReader.readLine())) {
                    osBuffer.append(sLine);
                }
            } catch (Exception _) {
                _RiseErrorTrace(_, "mRequestParsedPostBody", "sLine=" + sLine, "Breaked iteration!");
            }
            sBody = osBuffer.toString().trim();
        }
        return mPair(sBody);
    }

    /**
     * Получить карту с параметрами заданного обьекта запроса
     *
     * @return карта
     */
    public HashMap m(HttpServletRequest oRequest) {
        _LogCaseUp("m(oRequest)");
        HashMap m = new HashMap();
        if (oRequest != null) {
            try {
                Enumeration aName = oRequest.getParameterNames();
                for (int n = 0; aName.hasMoreElements(); n++) {
                    String sName = (String) aName.nextElement(), sValue = null;
                    try {
                        String[] asValue = oRequest.getParameterValues(sName);
                        if (asValue == null) {
                            sValue = null;
                        } else {
                            if (asValue.length == 1) {
                                sValue = asValue[0].length() > 0 ? asValue[0] : "";
                            } else {
                                sValue = "[";
                                for (int nSub = 0; nSub < asValue.length; nSub++) {
                                    sValue += (nSub > 0 ? "," : "") + asValue[nSub];
                                }
                                sValue += "]";
                            }
                        }
                        m.put(sName, sValue);
                    } catch (Exception _) {
                        _RiseErrorTrace(_, "n=" + n + ",sName=" + sName + ",sValue=" + sValue).doThrow();
                    }
                }
            } catch (Exception _) {
                _RiseErrorTrace(_, "oRequest!=null:" + (oRequest != null), "Breaked iteration!");
            }
        }
        return m;
    }

    /**
     * Установить обьект запроса.
     *
     * @return обьект
     */
    public ItemDataHold _Request(HttpServletRequest oRequestNew) {
        oRequestDefault = oRequestNew;
        mRequestDefault = m(oRequestNew);
        _mUpdate(mRequestDefault);
        try {
            if (bRequest()) {
                if (oRequestNew.getCharacterEncoding() != null) {
                    _Codepage(oRequestNew.getCharacterEncoding());
                }
                _Test(bTestServer(oRequest().getServerName()));
                if (bTest()) {
                    _RiseCase("_Request(oRequestNew)", "sLogin=" + sLogin() + ",sLoginDebug=" + s("sLoginDebug"), "Checking debug mode...");
                    if (sLogin() == null && bIs(s("sLoginDebug")) && bTestLogin(s("sLoginDebug"))) {
                        _Login(s("sLoginDebug"));
                        _Rise("_Request(oRequestNew)", "sLoginDebug=" + sLoginDebug() + ",sLogin=" + sLogin(), "Setup debug-login from parameter!");
                    }
                }
                String sOnLogCase = s("sToLogs", null);
                //_Rise("_Request(oRequestNew)", "sOnLogCase=" + sOnLogCase, "");
                if (sOnLogCase != null) {
                    onLogCase(sOnLogCase);
                    _Rise("_Request(oRequestNew)", "sOnLogCase=" + sOnLogCase, "Setup logging mode(onLogCase) from parameter!");
                }
            }
            _Session();
        } catch (Exception _) {
            _RiseWarn(_, "_Request(oRequestNew)", "oRequestNew!=null:" + (oRequestNew != null));
        }
        return this;
    }

    public ItemDataHold _mUpdate(HttpServletRequest oRequestUpdate) {
        oRequestDefault = oRequestUpdate;
        mRequestDefault.putAll(m(oRequestUpdate));
        _mUpdate(mRequestDefault);
        return this;
    }

    /**
     * Проверка - есть-ли обьект сессии
     *
     * @return true, если есть
     */
    public boolean bSession() {
        return oSession() != null;
    }

    /**
     * Получить обьект текущей сессии
     *
     * @return обьект сессии
     */
    public HttpSession oSession() {
        return oSessionDefault;
    }

    /**
     * Получить обьект сессии, по указанному логину (создается, если его нет)
     *
     * @param sLogin логин
     * @return обьект сессии
     */
    public HttpSession oSession(String sLogin) {
        _Session(true);
        if (bSession()) {
            _SessionValue(sNameLogin(), sLogin());
            _SessionValue(sNameRole(), sRole());
        }
        return oSession();
    }

    /**
     * Получить карту с параметрами текущей сессии по умолчанию
     *
     * @return карта
     */
    public HashMap mSession() {
        return mSessionDefault;
    }

    /**
     * Получить карту с параметрами заданного обьекта сессии
     *
     * @return карта
     */
    public HashMap m(HttpSession oSession) {
        _LogCaseUp("m(oSession)");
        HashMap m = new HashMap();
        if (oSession != null) {
            try {
                Enumeration aName = oSession.getAttributeNames();
                for (int n = 0; aName.hasMoreElements(); n++) {
                    String sName = (String) aName.nextElement(), sValue = null;
                    try {
                        Object oValue = oSession.getAttribute(sName);
                        if (oValue != null) {
                            try {
                                sValue = oValue.toString();
                                m.put(sName, sValue);
                            } catch (Exception _) {
                                m.put(sName, oValue);
                            }
                        }//sValue = oValue == null ? null : oValue.toString();
                    } catch (Exception _) {
                        _RiseErrorTrace(_, "n=" + n + ",sName=" + sName + ",sValue=" + sValue).doThrow();
                    }
                }
            } catch (Exception _) {
                _RiseErrorTrace(_, "oSession!=null:" + (oSession != null), "Breaked iteration!");
            }
        }
        return m;
    }

    /**
     * Установить сессию, по обьекту запроса (не создавать, если ее нет)
     *
     * @return this
     */
    public ItemDataHold _Session() {
        _Session(false);
        return this;
    }

    /**
     * Установить сессию, по обьекту запроса (с возможностью создавать, если ее
     * нет)
     *
     * @param bCreateIfAbsant создать сессию если ее нет
     * @return this
     */
    public ItemDataHold _Session(boolean bCreateIfAbsant) {
        if (bRequest()) {
            _Session(oRequestDefault.getSession(bCreateIfAbsant));
        }
        return this;
    }

    /**
     * Установить сессию, по обьекту запроса (создать, если ее нет) и установить
     * логин.
     *
     * @param sLogin логин
     * @return this
     */
    public ItemAccessData _Session(String sLogin) {
        _Session(true);
        _SessionValue(sNameLogin(), sLogin);//sLogin()
        //_SessionValue(sNameRole(), sRole());
        return this;
    }

    /**
     * Установить сессию, по обьекту.
     *
     * @param oSessionNew обьект сессии
     * @return this
     */
    public ItemDataHold _Session(HttpSession oSessionNew) {
        oSessionDefault = oSessionNew;
        _Login(sSessionValue(sNameLogin()));
        _Role(sSessionValue(sNameRole()));
        mSessionDefault = m(oSessionNew);
        if (bIncludeSessionData) {
            _mUpdate(mSessionDefault);
        }
        return this;
    }

    public ItemDataHold _mUpdate(HttpSession oSessionUpdate) {
        oSessionDefault = oSessionUpdate;
        mSessionDefault.putAll(m(oSessionUpdate));
        _mUpdate(mSessionDefault);
        return this;
    }

    /**
     * Получить значение параметра в сессии, если она есть (иначе null).
     *
     * @param sName название параметра
     * @return значение параметра
     */
    public String sSessionValue(String sName) {
        return sO(bSession() ? oSession().getAttribute(sName) : null);

    }

    /**
     * Установить значение параметра в сессии, если она есть.
     *
     * @param sName название параметра
     * @param oValue значение параметра
     * @return this
     */
    public ItemAccessData _SessionValue(String sName, Object oValue) {
        if (bSession() && bIs(sName)) {
            oSession().setAttribute(sName, oValue);
            mSession().put(sName, oValue);
            String sValue = sO(oValue);
            if (sName.equals(sNameRole())) {//bIs(sValue) && 
                _RiseCase("_Role(sValue)=" + sValue);
                _Role(sValue);
            }
            if (sName.equals(sNameLogin())) {//bIs(sValue) && 
                _RiseCase("_Login(sValue)=" + sValue);
                _Login(sValue);
            }
        }
        return this;
    }

    /**
     * Проверка - есть-ли обьект ответа
     *
     * @return true, если есть
     */
    public boolean bResponse() {
        return oResponse() != null;
    }

    /**
     * @return обьект ответа
     */
    public HttpServletResponse oResponse() {
        return oResponseDefault;
    }

    /**
     * Установить обьект ответа.
     *
     * @param oResponseNew обьект ответа
     * @return this
     */
    public ItemDataHold _Response(HttpServletResponse oResponseNew) {
        oResponseDefault = oResponseNew;
        return this;
    }

    /**
     * Получить логин по умолчанию
     *
     * @return строка
     */
    public String sLogin() {
        return sLogin;
    }

    /**
     * Получить логин по коду авторизации
     *
     * @param sAuthID код авторизации
     * @return строка
     */
    public String sLogin(String sAuthID) {
        return bSession() ? sNull(sSessionValue(sNameAuth()), null) : sLogin();
    }

    /**
     * Установить логин по умолчанию
     *
     * @return this
     */
    public ItemAccessData _Login(String sID) {
        resetAccess();
        sLogin = sS(sID).trim();
        return this;
    }

    /**
     * Получить название параметра логина по умолчанию
     *
     * @return строка
     */
    public String sNameLogin() {
        return sNameLogin;
    }

    /**
     * Установить название параметра логина по умолчанию
     *
     * @return this
     */
    public ItemAccessData _NameLogin(String sName) {
        resetAccess();
        sNameLogin = sS(sName);
        return this;
    }

    /**
     * Получить debug-логин по умолчанию
     *
     * @return строка
     */
    public String sLoginDebug() {
        return sLoginDebug;
    }

    /**
     * Установить debug-логин по умолчанию
     *
     * @return this
     */
    public ItemAccessData _LoginDebug(String sID) {
        resetAccess();
        sLoginDebug = sS(sID);
        return this;
    }

    /**
     * Получить роль по умолчанию
     *
     * @return строка
     */
    public String sRole() {
        return sRole;
    }

    /**
     * Установить роль по умолчанию
     *
     * @return this
     */
    public ItemAccessData _Role(String sID) {
        sRole = sS(sID).trim();
        return this;
    }

    /**
     * Получить название параметра роли по умолчанию
     *
     * @return строка
     */
    public String sNameRole() {
        return sNameRole;
    }

    /**
     * Установить название параметра роли по умолчанию
     *
     * @return this
     */
    public ItemAccessData _NameRole(String sName) {
        resetAccess();
        sNameRole = sS(sName);
        return this;
    }

    /**
     * Определить установлена-ли роль администратора по умолчанию
     *
     * @return true, если установлена
     */
    public boolean bAdmin() {
        return (sRoleAdmin().equalsIgnoreCase(sRole()));
    }

    /**
     * Получить роль администратора по умолчанию
     *
     * @return строка
     */
    public String sRoleAdmin() {
        return sRoleAdmin;
    }

    /**
     * Установить роль администратора по умолчанию
     *
     * @return this
     */
    public ItemAccessData _RoleAdmin(String sID) {
        sRoleAdmin = sS(sID).trim();
        return this;
    }

    /**
     * Получить код авторизации по умолчанию
     *
     * @return строка
     */
    public String sAuth() {
        return sAuth;
    }

    /**
     * Установить код авторизации по умолчанию
     *
     * @return this
     */
    public ItemAccessData _Auth(String sID) {
        sAuth = sS(sID).trim();
        return this;
    }

    /**
     * Получить название код авторизации по умолчанию
     *
     * @return строка
     */
    public String sNameAuth() {
        return sNameAuth;
    }

    /**
     * Установить название код авторизации по умолчанию
     *
     * @return this
     */
    public ItemAccessData _NameAuth(String sName) {
        resetAccess();
        sNameAuth = sS(sName);
        return this;
    }

    /**
     * Определить получен-ли грант/допуск (из кэша) по умолчанию
     *
     * @return true, если получен
     */
    public boolean bGranted() {
        return bGranted;
    }

    /**
     * Установить получен-ли грант/допуск по умолчанию (в кэш)
     *
     * @return true, если получен
     */
    public ItemAccessData _Granted(boolean bGrantedNew) {
        bGranted = bGrantedNew;
        return this;
    }

    /**
     * Получить гранта/допуска по умолчанию, в виде числа
     *
     * @return строка
     */
    public int nGrant() {
        return nGrant;
    }

    /**
     * Установить гранта/допуска по умолчанию, в виде числа
     *
     * @return this
     */
    public ItemAccessData _Grant(int nID) {
        resetAccess();
        nGrant = nID;
        _Agreed(false);
        _Granted(false);
        return this;
    }

    /**
     * Получить гранта/допуска по умолчанию, в виде строки
     *
     * @return строка
     */
    public String sGrant() {
        return sGrant;
    }

    /**
     * Установить гранта/допуска по умолчанию, в виде строки
     *
     * @return this
     */
    public ItemAccessData _Grant(String sID) {
        resetAccess();
        sGrant = sID;
        _Agreed(false);
        _Granted(false);
        return this;
    }

    /**
     * Получить название гранта/допуска по умолчанию
     *
     * @return строка
     */
    public String sNameGrant() {
        return sNameGrant;
    }

    /**
     * Установить название гранта/допуска по умолчанию
     *
     * @return this
     */
    public ItemAccessData _NameGrant(String sName) {
        resetAccess();
        sNameGrant = sS(sName);
        return this;
    }

    /**
     * Получение ответа о наличии гранта(допуска) в хранилищи (кэш)
     *
     * @return true, если есть
     */
    public boolean bGrantHold() {
        _Granted("true".equals(mGrant.get(nGrant + sGrant)));
        return mGrant.containsKey(nGrant + sGrant);
    }

    /**
     * Установка гранта(допуска) в хранилище (кэш)
     *
     * @return this
     */
    public ItemAccessData _GrantHold() {
        mGrant.put(nGrant + sGrant, bGranted() ? "true" : "");
        return this;
    }

    /**
     * Получение гранта(допуска), через проверку умолчательного ID (и
     * запоминание его, если нужно его попробовать получить).
     *
     * @return true, если получен
     */
    public boolean bGrant() {
        return bGranted() || (sGrant() == null && nGrant() <= 0) || bGrant(nGrant()) || bGrant(sGrant());
    }

    /**
     * Получение допуска, через проверку указанного ID в сессии
     *
     * @param nID Номер-ID, по которому проверяется допуск
     * @return true, если получен
     */
    public boolean bGrant(int nID) {
        return (nID + "").equals(sSessionValue(sNameGrant()));
    }

    /**
     * Получение допуска, через проверку указанного ID в сессии и ответа об
     * успехе
     *
     * @param sID Строка-ID, по которому проверяется допуск
     * @return true, если получен
     */
    public boolean bGrant(String sID) {
        return sID.equals(sSessionValue(sNameGrant()));
    }

    /**
     * Определить получено-ли согласие доступа по умолчанию (из кэша)
     *
     * @return true, если получен
     */
    public boolean bAgreed() {
        return bAgreed;
    }

    /**
     * Установить получено-ли согласие доступа по умолчанию (в кэш)
     *
     * @return this
     */
    public ItemAccessData _Agreed(boolean bAgreedNew) {
        bAgreed = bAgreedNew;
        return this;
    }

    /**
     * Получение согласия доступа, через проверку указанного номера ID в сессии
     * и ответа об успехе (и запоминание его).
     *
     * @param nGrant Номер-ID, по которому проверяется согласие
     * @return true, если получен
     */
    public boolean bAgree(int nGrant) {
        _Grant(nGrant);
        return bAgree();
    }

    /**
     * Получение согласия доступа, через проверку указанной строки ID в сессии и
     * ответа об успехе (и запоминание его).
     *
     * @param sGrant Строка-ID, по которому проверяется согласие
     * @return true, если получен
     */
    public boolean bAgree(String sGrant) {
        _Grant(sGrant);
        return bAgree();
    }

    /**
     * Получение согласия доступа (и запоминание его). (ядро)
     *
     * @return true, если получен
     */
    final public boolean bAgree() {
        if (!bAgreed()) {
            _AccessBy("");
            resetAccess();
            _LogCaseUp("bAgree");
            boolean bLogout = false;
            try {
                _Auth(s(sNameAuth(), sAuth()));
                bLogout = b("bLogout");
                _RiseCase("sNameLogin=" + sNameLogin() + ",sNameAuth=" + sNameAuth() + ",sAuth=" + sAuth() + ",bLogout=" + bLogout);
                if (bLogout) {
                    String sAuth = sAuth();
                    resetSession();
                    _Auth(s(sNameAuth(), sAuth));
                }
                bLogout |= "-1".equals(sAuth());
                String sSessionValue = sSessionValue(sNameLogin());
                boolean bSessionValid = bIs(sSessionValue);
                _RiseCase("bSessionValid=" + bSessionValid + ",sSessionValue=" + sSessionValue + ",sNameRole=" + sNameRole() + ",sRole=" + sRole() + ",sNameLogin=" + sNameLogin() + ",sLogin=" + sLogin() + ",sNameAuth=" + sNameAuth() + ",sAuth=" + sAuth());
                if (bIs(sLogin()) && !bLogout) {//&&bRole()
                    _AccessBy("Login");
                } else if (bSessionValid) {
                    if (bLogout) {
                        _AccessBy("Cancel");
                        resetSession();
                        if (!bIs(sRedirectURL()) && bRequest()) {
                            _RiseCase("_RedirectURL is empty, setup default!");
                            _RedirectURL(oRequest().getScheme() + "://" + oRequest().getServerName() + ":" + oRequest().getServerPort()
                                    + "/" + (oRequest().getRequestURI().toString().replaceAll("\\Q" + sNameAuth() + "=-1\\E", "").replaceAll("\\Q&&\\E", "&")));
                        }
                    } else {
                        _AccessBy("Session");
                        _Login(sSessionValue);
                    }
                } else if (bIs(sAuth())) {
                    _AccessBy("AuthID");//session.isNew()&//!"".equals(sAuth)&&sAuth!=null
                    _Login(sLogin(sAuth()));
                } else {
                    _AccessBy("Nothing");
                }
                //_RiseCase("sRedirectURL()="+sRedirectURL()+",bResponse()="+bResponse()+",bIs(sRedirectURL())="+bIs(sRedirectURL())+",bIs(sLogin())="+bIs(sLogin()),"sendRedirect");
                if (bIs(sLogin())) {
                    if (!bSessionValid) {//Создание, если нужно, и заполнение параметрами.
                        _Session(oSession(sLogin()));
                    }
                    //--- ТОЛЬКО ДЛЯ РЕЖИМА ОТЛАДКИ !!! (НАЧАЛО)
                    if (bTestLogin(sLogin()) && bTestServer((bRequest() ? oRequest().getRemoteAddr() : null))) {
                        _SessionValue(sNameRole(), sRoleAdmin());
                        _Granted(true);
                    }//--- ТОЛЬКО ДЛЯ РЕЖИМА ОТЛАДКИ !!! (КОНЕЦ)
                } else if (bIs(sRedirectURL()) && bResponse()) {
                    _RiseCase("sRedirectURL()=" + sRedirectURL(), "sendRedirect");
                    if (sRedirectURL().indexOf("index_test.jsp") >= 0) {
                        _Rise("sRedirectURL()=" + sRedirectURL(), "sendRedirect(YES_index_test.jsp)");
                    } else {
                        _Rise("sRedirectURL()=" + sRedirectURL(), "sendRedirect(NO_index_test.jsp)");
                        oResponse().sendRedirect(sRedirectURL());
                    }
                }
                _Granted(bGranted() || (bGrantHold() && bGranted()) || bGrant());
                _GrantHold();
            } catch (Exception _) {
                _RiseError(_, "sLogin=" + sLogin() + ",sRole=" + sRole() + ",bAdmin=" + bAdmin() + ",bGranted=" + bGranted() + ",sAuth=" + sAuth() + ",bLogout=" + bLogout + ",sAccessBy=" + sAccessBy() + ",IP=" + (bRequest() ? oRequest().getRemoteAddr() : "null") + ",Referer=" + (bRequest() ? oRequest().getHeader("Referer") : "null") + ",bAgreed=" + bAgreed());
            } finally {
                _Agreed(bIs(sLogin()) && bGranted());
                _Rise("sLogin=" + sLogin() + ",sRole=" + sRole() + (bAgreed() ? "" : ",bGranted=" + bGranted()) + ",sAuth=" + sAuth() + ",bLogout=" + bLogout + ",sAccessBy=" + sAccessBy() + ",IP=" + (bRequest() ? oRequest().getRemoteAddr() : "null") + ",Referer=" + (bRequest() ? oRequest().getHeader("Referer") : "null"), bAgreed() ? "Accepted" : "Rejected");
            }
        }
        return bAgreed();
    }

    /**
     * Получение тектового описания состояния доступа (есть доступ или нет, и
     * почему)
     *
     * @return строка с описанием
     */
    public String sAccessAgree() {
        return !bIs(sLogin()) ? "Нет авторизации, пожалуйста авторизируйтесь!"
                + "<br>[No Session] Not authorization, Please authorize!"
                + "<br>(sLogin='" + sLogin() + "',sInfo='" + sAccessBy() + "',sErr='" + saLoggedError() + "')"
                : bGranted() ? "Доступ разрешен!"
                : "Ваш логин отсутствует в справочнике разрешений к этому ресурсу, обратитесь к администратору для получения доступа!"
                + "<br>[ItemAccessData Deny] Your login is absant in GrantList to this resource, call to administrator for granting access!"
                + "<br>(sLogin='" + sLogin() + "',ID='" + nGrant() + "')";
    }

    /**
     * Получение тектового описания источника получения доступа
     *
     * @return строка с описанием
     */
    public String sAccessBy() {
        return sAccessBy;
    }

    /**
     * Установка тектового описания источника получения доступа
     *
     * @return this
     */
    public ItemAccessData _AccessBy(String sText) {
        sAccessBy = sText;
        return this;
    }

    /**
     * Получение пути страницы с ошибкой, по умолчанию (на нее происходит
     * переход в случае ошибки)
     *
     * @return строка с описанием
     */
    public String sRedirectURL() {
        return sRedirectURL;
    }

    /**
     * Установка пути страницы с ошибкой, по умолчанию (на нее происходит
     * переход в случае ошибки)
     *
     * @return строка с описанием
     */
    public ItemAccessData _RedirectURL(String sURL) {
        sRedirectURL = sURL;
        return this;
    }
}
