package com.bw.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

/**
 * Класс работы с REST-сервисами, и реализацией fluent-interface
 *
 * @author Belyavtsev Vladimir Vladimirovich (BW)
 */
public class ItemAccessREST extends ItemAccessData {

    /**
     * Пропуск валидации SSL-сертификата для HTTPS.
     */
    private boolean bSkipValidationSSL;

    /**
     * Методы отправки запросов.
     */
    public static enum Method {

        POST,
        GET
    }
    /**
     * Метод по умолчанию.
     */
    private Method oMethodDefault;
    /**
     * Таймаут по умолчанию (в милисекундах).
     */
    private int nTimeoutMS;
    /**
     * Остальные строковые параметры по умолчанию.
     */
    private String sDefaultURL, sHost, sPage;
    /**
     * Остальные параметры по умолчанию в виде карты.
     */
    private Map mParamDefault, mHeaderDefault;
    /**
     * Остальные параметры по умолчанию в виде карты.
     */
    private Serializable oDataSerializedDefault;
    private String sData;
    /**
     * Возвращенный обьект соединения.
     */
    private HttpURLConnection oConnectionDefault;
    /**
     * Возвращенный код статуса.
     */
    private int nStatus;
    /**
     * Возвращенное название статуса.
     */
    private String sStatus;
    /**
     * Возвращенный текст ошибки (из потока).
     */
    private String sReturnError;
    /**
     * Возвращенный ответ в виде строки. (задается только при использовании
     * одного из методов sRequest)
     */
    private String sReturn;
    /**
     * Возвращенная карта хедера.
     */
    private Map mReturnHeader;

    /**
     * Конструкторы.
     */
    public ItemAccessREST() {
        resetDefault();
    }

    public ItemAccessREST(HttpServletRequest o) {
        resetDefault();
        _Request(o);
    }

    public ItemAccessREST(String sURL) {
        resetDefault();
        _URL(sURL);
    }

    public ItemAccessREST(String sURL, String saParam) {
        resetDefault();
        _URL(sURL);
        _Param(sURL);
    }

    /**
     * Сброс всех базовых параметров по умолчанию
     */
    public void resetDefault() {
        super.resetDefault();
        bSkipValidationSSL = true;//!!! Временно будем пропускать валидацию по умолчанию (со временем ее пропускать нужно будет только на тестовом)
        oMethodDefault = Method.POST;
        nTimeoutMS = 0;//2000
        sDefaultURL = "";
        sHost = "";
        sPage = "";
        mParamDefault = new HashMap();
        oDataSerializedDefault = null;
        sData = null;
        mHeaderDefault = new HashMap();
        oConnectionDefault = null;
        _HeaderItem("Content-Type", "application/x-www-form-urlencoded");
        resetReturn();
    }

    /**
     * Сброс возвращенных данных по умолчанию
     */
    public void resetReturn() {
        sStatus = "";
        sReturnError = "";
        nStatus = 0;
        sReturn = null;
        mReturnHeader = new HashMap();
    }

    /**
     * Получение кода ответа от сервера (без повторного запроса)
     *
     * @return число-код ответа
     */
    public int nStatus() {
        return nStatus;
    }

    /**
     * Получение названия кода ответа от сервера (без повторного запроса)
     *
     * @return строка-название ответа
     */
    public String sStatus() {
        return sStatus + (bErrors() ? " (" + saLoggedError() + ")" : "");
    }

    /**
     * Получение ответа от сервера. (без повторного запроса)
     *
     * @return строка-название ответа
     */
    public String sReturn() {
        _RiseCase("sReturn=" + sReturn);
        return sReturn;

    }

    /**
     * Получение текста ошибки ответа от сервера, с кодом и статусом. (без
     * повторного запроса)
     *
     * @return строка-текста ошибки ответа
     */
    public String sReturnError() {
        return nStatus + "-" + sStatus + ":" + sReturnError;
    }

    /**
     * Подтверждение успешности запроса (без повторного запроса)
     *
     * @return true если ошибок небыло и сервер вернул в ответ код 200 (т.е.
     * абсолютно все в порядке )
     */
    public boolean bReturnedOk() {
        if (nStatus() == 0) {
            sRequest();
        }
        return nStatus() == 200 && !bErrors() && sReturn != null;//super.bOk() && 
    }

    /**
     * Подтверждение пропуска валидации SSL-сертификата для HTTPS.
     *
     * @return true = пропускать валидацию.
     */
    public boolean bSkipValidationSSL() {
        return bSkipValidationSSL;
    }

    /**
     * Установка пропуска валидации SSL-сертификата для HTTPS по умолчанию.
     *
     * @param bSkip true = пропускать валидацию.
     * @return this
     */
    public ItemAccessREST _SkipValidationSSL(boolean bSkip) {
        bSkipValidationSSL = bSkip;
        return this;
    }

    /**
     * Установка метода по умолчанию
     *
     * @return this
     */
    public ItemAccessREST _Method(Method o) {
        if (o != null) {
            oMethodDefault = o;
        }
        return this;
    }

    /**
     * Установка метода GET по умолчанию
     *
     * @return this
     */
    public ItemAccessREST _GET() {
        oMethodDefault = Method.GET;
        return this;
    }

    /**
     * Установка метода POST по умолчанию
     *
     * @return this
     */
    public ItemAccessREST _POST() {
        oMethodDefault = Method.POST;
        return this;
    }

    /**
     * Получение таймаута по умолчанию = 0).
     *
     * @return число милисекунд
     */
    public int nTimeout() {
        return nTimeoutMS;
    }

    /**
     * Установка таймаута по умолчанию (изначально = 0).
     *
     * @param nMillisecond число милисекунд
     * @return this
     */
    public ItemAccessREST _Timeout(int nMillisecond) {
        nTimeoutMS = nMillisecond;
        return this;
    }

    /**
     * Получение хоста по умолчанию.
     *
     * @return строка хоста
     */
    public String sHost() {
        return sHost;
    }

    /**
     * Установка хоста по умолчанию.
     *
     * @param sProtocolHostPort хост (возможно с протоколом и портом)
     * @return строка хоста
     */
    public ItemAccessREST _Host(String sProtocolHostPort) {
        sHost = sProtocolHostPort;
        return this;
    }

    /**
     * Установка хоста по умолчанию по обьекту запроса.
     *
     * @param oRequest обьект запроса
     * @return строка хоста
     */
    public ItemAccessREST _Host(HttpServletRequest oRequest) {
        _Request(oRequest);
        if (bRequest()) {
            sHost = oRequest.getScheme() + "://" + oRequest.getServerName() + ":" + oRequest.getServerPort() + oRequest.getContextPath();
        }
        return this;
    }

    /**
     * Получение пути страницы по умолчанию.
     *
     * @return this
     */
    public String sPage() {
        return sPage;
    }

    /**
     * Установка пути страницы по умолчанию.
     *
     * @param sPath путь страницы (без хоста, порта и т.д.)
     * @return строка пути страницы
     */
    public ItemAccessREST _Page(String sPath) {
        sPage = sPath;
        return this;
    }

    /**
     * Получение полного адреса по умолчанию.
     *
     * @return this
     */
    public String sURL() {
        return sDefaultURL;
    }

    /**
     * Получение полного адреса, с установкой его по умолчанию.
     *
     * @param sHostPageParam полный адрес (по необходимости включая: хост, порт,
     * путь и параметры(энкодированные))
     * @return строка адреса
     */
    private String sURL(String sHostPageParam) throws Exception {
        _LogCase("sURL(sHostPageParam)");
        String sURL = null;
        if (sHostPageParam != null) {
            sDefaultURL = sHostPageParam;
        }
        boolean bURL = bIs(sDefaultURL),//sDefaultURL != null && !"".equals(sDefaultURL.trim())
                bHost = bIs(sHost),//sHost != null && !"".equals(sHost.trim()),
                bPage = bIs(sPage);//sPage != null && !"".equals(sPage.trim());//,
        try {
            if (!bURL && !bHost && bRequest()) {
                bHost = true;
                _RiseCase("TryGetURLByRequest");
                sHost = oRequest().getScheme() + "://" + oRequest().getServerName() + ":" + oRequest().getServerPort() + oRequest().getContextPath();
            }
            if (!bURL && !bHost) {
                _RiseError("Invalid URL(need entire sURL or separated sHost and sPage)!").doThrow();
            }
            sURL = (bURL ? sDefaultURL : (bHost ? sHost : "") + (bPage ? sPage : ""));
            _RiseCase("sHostPageParam=" + sHostPageParam + ",sDefaultURL=" + sDefaultURL + ",sHost=" + sHost + ",sPage=" + sPage + ",bRequest=" + bRequest(), "Built sURL:" + sURL);
        } catch (Exception _) {
            _RiseErrorTrace(_, "sHostPageParam=" + sHostPageParam + ",sDefaultURL=" + sDefaultURL + ",sHost=" + sHost + ",sPage=" + sPage + ",bRequest=" + bRequest(), "Fail building sURL(" + sURL + ")").doThrow();
        }
        if (!bIs(sURL)) {//sURL == null || "".equals(sURL)
            _RiseError("sURL is empty!").doThrow();
        }
        return sURL;
    }

    /**
     * Установка полного адреса по умолчанию.
     *
     * @param sHostPageParam полный адрес (по необходимости включая: хост, порт,
     * путь и параметры(энкодированные))
     * @return this
     */
    public final ItemAccessREST _URL(String sHostPageParam) {
        sDefaultURL = sHostPageParam;
        return this;
    }

    /**
     * Получение энкодированной строки параметров, заданных по умолчанию (пар
     * название-значение), с кодировкой по умолчанию.
     *
     * @return this строка
     */
    public String saParam() {
        return saPair(mParamDefault, sCodepage());
    }

    /**
     * Получение карты параметров, заданных по умолчанию).
     *
     * @return карта
     */
    public Map mParam() {
        return mParamDefault;
    }

    /**
     * Установка параметров по умолчанию через карту.
     *
     * @param mPair карта параметров (если null то игнорируется)
     * @return this
     */
    public ItemAccessREST _Param(Map mPair) {
        if (mPair != null) {
            mParamDefault = mPair;
        }
        return this;
    }

    /**
     * Установка параметров по умолчанию через энкодированную строку пар.
     *
     * @param saPairEncoded энкодированная строка пар параметров (если null то
     * игнорируется)
     * @return this
     */
    public ItemAccessREST _Param(String saPairEncoded) {
        _RiseCase("_Param", "saPair=" + saPairEncoded, "WillPutAllToMap");
        if (saPairEncoded != null) {
            mParamDefault = mPair(saPairEncoded, sCodepage());
        }
        return this;
    }

    /**
     * Добавление параметра по умолчанию.
     *
     * @param sName название параметра
     * @param sValue значение параметра
     * @return this
     */
    public ItemAccessREST _Param(String sName, String sValue) {
        String sValueNew = sMerged(sValue);
        _RiseCase("_Param", "sName=" + sName + ",sValue=" + sValue + ",sValueNew=" + sValueNew, "WillPutToMap");
        mParamDefault.put(sName, sValueNew);
        return this;
    }

    /**
     * Установка тела запроса по умолчанию.
     *
     * @param oDataSerialized сериализованные даные (по умолчанию = null)
     * @return this
     */
    public ItemAccessREST _Body(Serializable oDataSerialized) {
        oDataSerializedDefault = oDataSerialized;
        return this;
    }

    /**
     * Получение карты свойств хедера, заданных по умолчанию.
     *
     * @return карта
     */
    public Map mHeader() {
        return mHeaderDefault;
    }

    /**
     * Установка свойств хедера по умолчанию через карту.
     *
     * @param mPair карта хедеров (если null то игнорируется)
     * @return this
     */
    public ItemAccessREST _Header(HashMap mPair) {
        if (mPair != null) {
            mHeaderDefault = mPair;
        }
        return this;
    }

    /**
     * Дополнение(с перезаписью совпадающих) свойств хедера по умолчанию через
     * карту.
     *
     * @param mPair карта хедеров (если null то игнорируется)
     * @return this
     */
    public ItemAccessREST _HeaderUpdate(HashMap mPair) {
        if (mPair != null) {
            mHeaderDefault.putAll(mPair);
        }
        return this;
    }

    /**
     * Добавление свойства хедера по умолчанию.
     *
     * @param sName название хедера
     * @param sValue значение хедера
     * @return this
     */
    public ItemAccessREST _HeaderItem(String sName, String sValue) {
        _RiseCase("_Header", "sName=" + sName + ",sValue=" + sValue, "WillPutToMap");
        mHeaderDefault.put(sName, sValue);
        return this;
    }

    /**
     * Получение карты свойств полученного хедера.
     *
     * @return карта
     */
    public Map mReturnHeader() {
        return mReturnHeader;
    }

    /**
     * Получение строки свойств полученного хедера.
     *
     * @return строка
     */
    public String sReturnHeader() {
        String sReturnHeader = "";
        Iterator om = mReturnHeader.keySet().iterator();
        for (int n = 1; om.hasNext(); n++) {
            String sName = om.next() + "", sValue = "";
            if (sName != null && !"null".equalsIgnoreCase(sName)) {
                List<String> as = (List<String>) mReturnHeader.get(sName);
                for (int nSub = 1; nSub <= as.size(); nSub++) {
                    sValue += (nSub > 1 ? ";" : "") + as.get(nSub - 1);
                }
                sReturnHeader += (n > 1 ? "\r\n" : "") + sName + ": " + sValue;
                //_Rise("sName="+sName+",sValue="+sValue);
            }
        }
        _Rise("sReturnHeader=" + sReturnHeader);
        return sReturnHeader;
    }

    /**
     * Запрос получения строки.
     *
     * @return строка с ответом
     */
    public String sRequest() {
        return sRequest(null, null, null, null);
    }

    /**
     * Запрос получения строки (с возможностью задать любые параметры).
     *
     * @param sHostPageParamCustom адрес, возможно, с портом,путем и
     * параметрами(тогда обязательно энкоденными) (если null то дефолт)
     * @param oDataSerializedCustom сериализованные данные для отсылки/параметры
     * или бинарные (тогда обязательно задать метод пост) (если null то дефолт)
     * @param mHeaderCustom заголовки (если null то дефолт)
     * @param oMethodCustom метод (если null то дефолт(обычно POST))
     * @return строка с ответом
     */
    public String sRequest(String sHostPageParamCustom, Serializable oDataSerializedCustom, Map<String, String> mHeaderCustom, Method oMethodCustom) {
        sReturn = sStream(oStreamRequest(sHostPageParamCustom, oDataSerializedCustom, mHeaderCustom, oMethodCustom));
        return sReturn;
    }

    /**
     * Запрос получения потока (с возможностью задать любые параметры).
     *
     * @param sHostPageParamCustom адрес, возможно, с портом,путем и
     * параметрами(тогда обязательно энкоденными) (если null то дефолт)
     * @param oDataSerializedCustom сериализованные данные для отсылки/параметры
     * или бинарные (тогда обязательно задать метод пост) (если null то дефолт)
     * @param mHeaderCustom заголовки (если null то дефолт)
     * @param oMethodCustom метод (если null то дефолт(POST))
     * @return поток с ответом
     */
    public InputStream oStreamRequest(String sHostPageParamCustom, Serializable oDataSerializedCustom, Map<String, String> mHeaderCustom, Method oMethodCustom) {
        _LogCase("oStreamRequest(sHostPageParamCustom,oDataSerializedCustom,mHeaderCustom,oMethodCustom)");
        resetLogged();
        resetReturn();
        InputStream oStreamAt = null;
        String sURL = null;//, sData = "";
        Serializable oDataSerialized = oDataSerializedCustom == null ? (oDataSerializedDefault == null ? "" : oDataSerializedDefault) + saParam() : oDataSerializedCustom;
        Map mHeader = mHeaderCustom == null ? mHeaderDefault : mHeaderCustom;
        Method oMethod = oMethodCustom == null ? oMethodDefault : oMethodCustom;
        try {
            _RiseCase("sHostPageParamCustom=" + sHostPageParamCustom, "TryDeSerialize");
            sData = oDataSerialized.toString();
            if (sData.length() > 1024 && oMethod == Method.GET) {
                _RiseWarn("oMethod=" + oMethod, "sData.length()>1024(" + sData.length() + "), and will switch to POST method!");
                _POST();
            }
            _RiseCase("oMethod=" + oMethod, "sData=" + sData, "DeSerialized! TryCheckURL");
            sURL = sURL(sHostPageParamCustom);
            if (oMethod == Method.GET && bIs(sData)) {
                _RiseWarn("oMethod=" + oMethod + ",sURL=" + sURL, "sData.length()>0(" + sData.length() + "), and will attached to sURL!");
                sURL = sURL + (sURL.indexOf("?") < 0 ? "?" : "&") + sData;
            }
            _RiseCase("sURL=" + sURL, "CheckURL! TryMakeURL");
            URL oURL = new URL(sURL);
            _URL(sURL);
            _Method(oMethod);
            _RiseCase("TryMakeConnect");
            URLConnection oConnectAbstract = oURL.openConnection();

            if (oConnectAbstract instanceof HttpsURLConnection) {
                _RiseCase("TryConnectHTTPS");
                simplifySSLConnection(bSkipValidationSSL ? null : (HttpsURLConnection) oConnectAbstract);
                _RiseCase("ConnectedHTTPS");
            }
            HttpURLConnection oConnect = (HttpURLConnection) oConnectAbstract;
            oConnect.setDoOutput(oMethod != Method.GET);
            oConnect.setDoInput(true);
            oConnect.setUseCaches(false);
            //((HttpURLConnection) oConnect).setRequestMethod(oMethod.name());
            oConnect.setRequestMethod(oMethod.name());
            addRequestHeader(mHeader, oConnect);
            _RiseCase("mHeader=" + mHeader + ",nTimeoutMS=" + nTimeoutMS, "GotHeaders, try set Timeout");
            oConnect.setConnectTimeout(nTimeoutMS);
            oConnect.setReadTimeout(nTimeoutMS);
            if (oMethod == Method.POST) {
                byte[] oData = sData.getBytes(sCodepage());
                _RiseCase("oData.length=" + oData.length, "TrySetLen");
                oConnect.setRequestProperty("Content-Length", "" + oData.length);
                _RiseCase("TryConnect...");
                oConnect.connect();
                OutputStream oStreamTo = null;
                try {
                    _RiseCase("Connected! TryWriteToStream");
                    oStreamTo = oConnect.getOutputStream();
                    oStreamTo.write(oData);//write data to stream:
                    oStreamTo.flush();
                    oStreamTo.close();
                    oStreamTo = null;
                } catch (Exception _) {
                    _RiseErrorTrace(_, "Can't write to stream").doThrow();//_.printStackTrace();
                    //throw new Exception("Can't write to stream: " + _.getMessage());
                } finally {
                    if (oStreamTo != null) {
                        oStreamTo.close();
                    }
                }
            } else {
                _RiseCase("TryConnect...");
                oConnect.connect();
                _RiseCase("Connected!");
            }
            _RiseCase("Ok!");
            //oConnectionDefault=oConnect;
            oStreamAt = oStreamResponse(oConnect);
            //oConnectionDefault=null;
        } catch (Exception _) {
            sReturn = null;
            _RiseError(_, "sMethod=" + oMethod.name() + ",sData.length=" + sData.length() + ",sURL=" + sURL);//_.printStackTrace();
        }
        return oStreamAt;
    }

    /**
     * Заполнение заголовка запроса в открытом соединении.
     *
     * @param oConnectCustom соединение (если null, то возьмется дефолтное)
     * @param mHeader добавляемые свойства (или null)
     */
    private HttpURLConnection oConnect(HttpURLConnection oConnectCustom) throws Exception {
        HttpURLConnection oConnect = oConnectCustom == null ? oConnectionDefault : oConnectCustom;
        if (oConnect == null) {
            throw new Exception("oConnect==null");
        }
        return oConnect;
    }

    /**
     * Дополнение(с перезаписью) заголовка запроса в открытом соединении,
     * значениями из карты.
     *
     * @param oConnectCustom соединение (если null, то возьмется дефолтное)
     * @param mHeader добавляемые свойства (если null, то пропускается)
     */
    private void addRequestHeader(Map<String, String> mHeader, HttpURLConnection oConnectCustom) throws Exception {
        HttpURLConnection oConnect = oConnect(oConnectCustom);
        if (mHeader != null && oConnect != null) {
            for (Map.Entry<String, String> e : mHeader.entrySet()) {
                oConnect.setRequestProperty(e.getKey(), e.getValue());
            }
        }
    }

    /**
     * Анализ потока ответа соединения и возврат потока.
     *
     * @param oConnectCustom соединение (если null, то берется дефолт)
     * @return поток ответа.
     */
    private InputStream oStreamResponse(HttpURLConnection oConnectCustom) {
        _LogCase("oStreamResponse(oConnectCustom)");
        InputStream oStreamAt = null;
        HttpURLConnection oConnectHTTP = null;
        try {//URLConnection oConnect=oConnect(oConnectCustom);
            oConnectHTTP = oConnect(oConnectCustom);
            //oConnectHTTP=(HttpURLConnection)oConnect;//oConnect(oConnectCustom)
            sStatus = oConnectHTTP.getResponseMessage();
            nStatus = oConnectHTTP.getResponseCode();
            mReturnHeader = oConnectHTTP.getHeaderFields();
            boolean bErrorHi = oConnectHTTP.getResponseCode() >= 400;
            oStreamAt = bErrorHi ? oConnectHTTP.getErrorStream() : oConnectHTTP.getInputStream();
            java.io.ByteArrayOutputStream oStreamTo = oStreamCopy(oStreamAt);
            String sStreamTo = sStream(oStreamTo);
            _RiseCase("nStatus=" + nStatus + ",sStatus=" + sStatus, "Got stream content: " + sStreamTo);
            if (nStatus != 200) {
                sReturnError = sStreamTo;
                _RiseWarn("nStatus=" + nStatus + ",sStatus=" + sStatus + ",bErrorHi=" + bErrorHi + ",sMethod=" + oMethodDefault.name() + ",sURL=" + sURL() + ",sData=" + sData, "Got error stream content: " + sStreamTo);
            }
            oStreamAt = new ByteArrayInputStream(oStreamTo.toByteArray());
        } catch (Exception _) {
            sReturn = null;
            _RiseErrorTrace(_, "sStatus=" + sStatus + ",nStatus=" + nStatus + ",sMethod=" + oMethodDefault.name() + ",sURL=" + sURL() + ",sData=" + sData, "");
        } finally {
            if (oConnectHTTP != null) {
                oConnectHTTP.disconnect();
            }
        }
        return oStreamAt;
    }

    /**
     * Веривикация сертификата при HTTPS-соединении.
     *
     * @param oConnectHTTPS соединение (если null, то верификация будет
     * пропущенна)
     */
    public void simplifySSLConnection(HttpsURLConnection oConnectHTTPS) {
        if (oConnectHTTPS != null) {
            //HttpsURLConnection oConnectHTTPS = (HttpsURLConnection) oConnectAbstract;
            oConnectHTTPS.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } else {
            TrustManager[] trustAllCerts = {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
            };
            try {
                SSLContext oSSLContext = SSLContext.getInstance("SSL");
                oSSLContext.init(null, trustAllCerts, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(oSSLContext.getSocketFactory());
            } catch (Exception _) {
                _RiseWarn(_, "simplifySSLConnection", "", "Fail getting SSLContext");
            }
            HostnameVerifier oHostnameVerifier = new HostnameVerifier() {
                public boolean verify(String urlHostName, SSLSession session) {
                    _RiseWarn("simplifySSLConnection", "URL Host(urlHostName)=" + urlHostName, " vs. " + session.getPeerHost());
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(oHostnameVerifier);
        }
    }
}
