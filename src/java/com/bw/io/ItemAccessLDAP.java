package com.bw.io;

import netscape.ldap.*;

/**
 * Доступ/валидация по логину и паролю LDAP.<br>
 *
 * @author Belyavtsev Vladimir Vladimirovich
 */
public class ItemAccessLDAP extends ItemHold {

    /**
     * Параметры
     */
    private boolean bPassed = false;
    private int nPort;
    private String sHost;
    private String sLevel;
    private String sAnswer;
    private String sAnswerLabel;
    private LDAPConnection oConnectionLDAP;

    /**
     * Конструкторы
     */
    public ItemAccessLDAP() {
        resetDefault();
    }

    /**
     * Сбросить все параметры по умолчанию
     */
    @Override
    public void resetDefault() {
        super.resetDefault();
        bPassed = false;
        sAnswer = null;
        sAnswerLabel = null;
        oConnectionLDAP = null;
    }

    /**
     * @return хост сервера по умолчанию
     */
    public String sHost() {
        return sHost;
    }

    /**
     * Установить хост сервера по умолчанию
     *
     * @param sHostNew хост (без порта)
     * @return this
     */
    public ItemAccessLDAP _Host(String sHostNew) {
        sHost = sHostNew;
        return this;
    }

    /**
     * @return порт сервера по умолчанию
     */
    public int nPort() {
        return nPort;
    }

    /**
     * Установить порт сервера по умолчанию
     *
     * @param nPortNew порт
     * @return this
     */
    public ItemAccessLDAP _Port(int nPortNew) {
        nPort = nPortNew;
        return this;
    }

    /**
     * @return уровень по умолчанию
     */
    public String sLevel() {
        return sLevel;
    }

    /**
     * Установить уровень по умолчанию
     *
     * @return this
     */
    public ItemAccessLDAP _Level(String sLevelNew) {
        sLevel = sLevelNew;
        return this;
    }

    /**
     * Проверка пропуска авторизации (без запроса)
     *
     * @return true, если есть
     */
    public boolean bPassed() {
        return bPassed;
    }

    /**
     * @return лэйбл ответа авторизации (без запроса)
     */
    public String sAnswerLabel() {
        return sAnswerLabel != null ? sAnswerLabel : "Сбой авторизации!";
    }

    /**
     * @return ответ авторизации (без запроса)
     */
    public String sAnswer() {
        return sAnswer != null ? sAnswer : "Breaked authorization!";
    }

    /**
     * Проверка авторизации
     *
     * @param sLogin логин
     * @param sPassword пароль
     * @return ответ авторизации (с запросом)
     */
    public String sAnswer(String sLogin, String sPassword) {
        oConnectionLDAP = null;
        sAnswer = null;
        sAnswerLabel = null;
        bPassed = false;
        if (sLogin == null || sLogin.length() == 0) {
            sAnswer = "Login not entered!";
            sAnswerLabel = "Не введен логин!";
            _RiseError("sAnswer", "", sAnswer);
        } else if (sPassword == null || sPassword.length() == 0) {
            sAnswer = "Password not entered!";
            sAnswerLabel = "Не введен пароль!";
            _RiseError("sAnswer", "sLogin=" + sLogin, sAnswer);
        } else {
            String sDN = null;
            try {
                sDN = sDN(sLogin);
                if (sDN == null || sDN.length() == 0) {
                    if (sAnswer == null) {
                        sAnswer = "User not found!";
                    }
                    if (sAnswerLabel == null) {
                        sAnswerLabel = "Не найден пользователь!";
                    }
                    _RiseError("sAnswer", "sLogin=" + sLogin + ",sDN=" + sDN, sAnswer);
                } else {
                    oConnectionLDAP.connect(sHost(), nPort());
                    oConnectionLDAP.authenticate(3, sDN, sPassword);
                    sAnswer = "Success";
                    sAnswerLabel = "Успешная авторизация!";
                    bPassed = true;
                }
            } catch (LDAPException _) {
                int nCode = _.getLDAPResultCode();
                if (nCode == 49) {
                    sAnswer = "Invalid password";
                    sAnswerLabel = "Неверный пароль!";
                    _RiseError("sAnswer", "sLogin=" + sLogin + ",sDN=" + sDN + ",nCode=" + nCode, sAnswer);
                } else if (nCode == 32) {
                    sAnswer = "No such user";
                    sAnswerLabel = "Нет такого пользователя!";
                    _RiseError("sAnswer", "sLogin=" + sLogin + ",sDN=" + sDN + ",nCode=" + nCode, sAnswer);
                } else if (nCode == 19) {
                    sAnswer = "Exceed password retry limit. Please try later.";
                    sAnswerLabel = "Превышен лимит попыток ввода пароля! Попробуйте позже!";
                    _RiseError("sAnswer", "sLogin=" + sLogin + ",sDN=" + sDN + ",nCode=" + nCode, sAnswer);
                } else {
                    sAnswer = "Error on authentication: #" + nCode + "-" + (_.toString() == null ? "unknown" : _.toString());
                    sAnswerLabel = "Ошибка аутентификации! (#" + nCode + "-" + (_.toString() == null ? "неизвестная" : _.toString()) + ")";
                    _RiseError(_, "sAnswer", "sLogin=" + sLogin + ",sDN=" + sDN, sAnswer);
                }
            }
            if (oConnectionLDAP != null && oConnectionLDAP.isConnected()) {
                try {
                    oConnectionLDAP.disconnect();
                } catch (LDAPException _) {
                    int nCode = _.getLDAPResultCode();
                    if (!bPassed) {
                        sAnswer += "(LDAPException=" + _.toString() + ")";
                        sAnswerLabel = "(LDAPException:" + _.getMessage() + ")";
                    }
                    _RiseWarn("sAnswer", "sLogin=" + sLogin + ",sDN=" + sDN + ",nCode=" + nCode, "on oConnectionLDAP.disconnect():" + _.getMessage());
                }
            }
            if (bPassed) {
                _Rise("sAnswer", "sLogin=" + sLogin + ",sDN=" + sDN, sAnswer());
            }
        }
        return sAnswer();
    }

    private String sDN(String sUID) throws LDAPException {
        String sFilter = "(uid=" + sUID + ")", sReturn = null;
        oConnectionLDAP = new LDAPConnection();
        oConnectionLDAP.connect(sHost(), nPort());
        oConnectionLDAP.authenticate(null, null);
        for (LDAPSearchResults oaResult = oConnectionLDAP.search(sLevel(), 2, sFilter, null, false); oaResult.hasMoreElements();) {
            LDAPEntry oEntry = oaResult.next();
            String sLockAccount = null;
            LDAPAttribute oAttribute = oEntry.getAttribute("passwordLockout");
            if (oAttribute != null) {
                String asValue[] = oAttribute.getStringValueArray();
                if (asValue != null && asValue.length != 0) {
                    sLockAccount = asValue[0];
                }
            }
            if (sLockAccount != null && sLockAccount.equals("1")) {
                String sReason = "\u0412\u0430\u0448\u0430 \u0443\u0447\u0435\u0442\u043D\u0430\u044F \u0437\u0430\u043F\u0438\u0441\u044C \u0437\u0430\u0431\u043B\u043E\u043A\u0438\u0440\u043E\u0432\u0430\u043D\u0430 ";
                oAttribute = oEntry.getAttribute("pbPasswordLockedBy");
                if (oAttribute != null) {
                    String asValue[] = oAttribute.getStringValueArray();
                    if (asValue != null && asValue.length != 0) {
                        sReason += asValue[0];
                    }
                }
                oAttribute = oEntry.getAttribute("pbPasswordLockoutReason");
                if (oAttribute != null) {
                    String asValue[] = oAttribute.getStringValueArray();
                    if (asValue != null && asValue.length != 0) {
                        sReason += " \u043F\u043E \u043F\u0440\u0438\u0447\u0438\u043D\u0435: '" + asValue[0] + "'";
                    }
                }
                _RiseWarn("sDN", "sUID=" + sUID + ",sLockAccount=1", "Locked:" + sReason);
                sAnswer = "Account Locked! (" + sReason + ")";
                sAnswerLabel = "Заблокиновано!";
            } else {
                sReturn = oEntry.getDN();
            }
        }
        try {
            oConnectionLDAP.disconnect();
        } catch (LDAPException _) {
            int nCode = _.getLDAPResultCode();
            _RiseWarn("sDN", "sUID=" + sUID + ",nCode=" + nCode, "on oConnectionLDAP.disconnect():" + _.getMessage());
        }
        return sReturn;
    }
}
