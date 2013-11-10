package business.model;

import business.AccessDB;
import business.ManagerSQL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/******
 go
 drop table Access
 
 go
 CREATE table Access (        
     nID INT identity,
     nID_TheSubjectHuman INT not null,
     sLogin VARCHAR(25) not null,            --  СДЕЛАТЬ уникально UNIQUE!
     sPassword VARCHAR(25) not null,         -- 
     nDisabled INT default -1,               -- (вырубить доступ)  0 - доступ открыт, 1 - закрыт, -1 - неопределено
     sDateMake DATETIME default getDate() null,
     sDateEdit DATETIME default getDate() null,
     PRIMARY KEY(nID),
 )

-- Все поля индексировать?
--CREATE INDEX IndexID ON Access (nID)
--DROP INDEX Access.Index_nID
CREATE unique INDEX Index_nID_TheSubjectHuman ON Access (nID_TheSubjectHuman)
DROP INDEX Access.Index_nID_TheSubjectHuman

 
 
--ALTER TABLE Access ADD sDateMake DATETIME default getDate() null 
--ALTER TABLE Access ADD sDateEdit DATETIME default getDate() null
--alter table Access drop bDisabled
--alter table Access add nDisabled INT default -1
-- alter table Access  modify nID_TheSubjectHuman INT null
-- alter table Access  modify sLogin INT UNIQUE (sLogin)
    
**/


public class Access {

      public static ArrayList<String[]> aResult = new ArrayList<String[]>();
     public static ArrayList<String> aResult2 = new ArrayList<String>();
     public static ArrayList<String> aResult3 = new ArrayList<String>();
     public static String sObject = "";
     
    private Logger oLog = Logger.getLogger(getClass());
    private int nID;
    private int nID_TheSubjectHuman;
    private String sLogin;   // ( сделать уникальным)
    private String sPassword;
    private int nDisabled = -1; // (вырубить доступ. 0 доступ открыт, 1 доступ закрыт, -1 неопределно)
    private String sDateMake;   // дата создания записи
    private String sDateEdit;   // дата редактирпования записи
    
     public int nID() {
          return nID;
     }

     public Access _ID(int i) {
          nID = i;
          return this;
     }

     public int nID_TheSubjectHuman() {
          return nID_TheSubjectHuman;
     }

     public Access _ID_TheSubjectHuman(int i) {
          nID_TheSubjectHuman = i;
          return this;
     }

     public String sLogin() {
          return sLogin;
     }

     public Access _Login(String s) {
          sLogin = s;
          return this;
     }

     public String sPassword() {
          return sPassword;
     }

     public Access _Password(String s) {
          sPassword = s;
          return this;
     }

     public int nDisabled() {
          return nDisabled;
     }

     /**
      * Выключить/заблокировать юзера (0 доступ открыт, 1 закрыт, -1 неопределено)
      *
      * @param n
      * @return
      */
     public Access _Disabled(int n) {
          nDisabled = n;
          return this;
     }

     public String sDateMake() {
          return sDateMake;
     }

     public Access _sDateMake(String s) {
          sDateMake = s;
          return this;
     }

     public String sDateEdit() {
          return sDateEdit;
     }

     public Access _sDateEdit(String s) {
          sDateEdit = s;
          return this;
     }


    
    
    
    
    //================================================
    public Access() {
    }

    public Access(String sLogin) throws Exception {
        load(sLogin);
    }

    /**
     * Инициализируем класс данными прользователя по Логину
     *
     * @param sLogin - Емаил пользователя
     * @throws Exception
     */
    public void load(String sLogin) throws Exception {
        String sCase = "load(sLogin)";
        Connection oConnection = null;
        Statement oStatement = null;
        try {
            oConnection = AccessDB.oConnectionStatic(sCase);
            oStatement = AccessDB.oStatementStatic(oConnection, sCase);
            ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT TOP 1 *  FROM Access where sLogin = '" + sLogin + "'", oLog);
            if (oRowset.next()) {
                nID = oRowset.getInt("nID");
                nID_TheSubjectHuman = oRowset.getInt("nID_TheSubjectHuman");
                sLogin = oRowset.getString("sLogin");
                sPassword = oRowset.getString("sPassword");
                nDisabled = oRowset.getInt("nDisabled");
            }
        } catch (Exception oException) {
            oLog.error("[" + sCase + "] (sLogin= " + sLogin + "): Ошибка Конструктора ", oException);
        } finally {
            AccessDB.close(sCase, oStatement);
            AccessDB.closeConnectionStatic(sCase, oConnection);
        }
    }

    /**
     * По Емэйлу пользователя меняем его данные в базе
     *
     * @param sLogin
     * @throws Exception
     */
    public void save(String sLoginOld) throws Exception {
        long start = System.currentTimeMillis(); // Вычисляем время выполнения метода
        String sCase = "save";
        Connection oConnection = null;
        Statement oStatement = null;
        //String sLoginOld = sLogin(); 
        try {
            oConnection = AccessDB.oConnectionStatic(sCase);
            oStatement = AccessDB.oStatementStatic(oConnection, sCase);
            String sSQL = new ManagerSQL()
                    ._Pair("sDateEdit = getDate()")
                    ._Pair("sLogin = '" + sLogin() + "'", sLogin() != null)
                    ._Pair("sPassword = '" + sPassword() + "'", sPassword() != null)
                    ._Pair("nDisabled = " + nDisabled(), nDisabled() != -1)
                    .sSQL("UPDATE Access SET ", " WHERE sLogin='" + sLoginOld + "'"); //sLoginOld
            AccessDB.nRowsetUpdate(oStatement, sCase, sSQL, oLog);

                    
            /*if (sLogin() != null) {
             AccessDB.nRowsetUpdate(oStatement, sCase, "UPDATE Access SET sLogin = '" + sLoginNew + "' WHERE sLogin='" + sLoginOld + "'", oLog);
             } */

        } catch (Exception oException) {
            oLog.error("[" + sCase + "]( sLogin()= " + sLogin() + " sLogin()= " + sLogin() 
                    + " nDisabled()= " + nDisabled() + " ): Ошибка записи!", oException);
        } finally {
            AccessDB.close(sCase, oStatement);
            AccessDB.closeConnectionStatic(sCase, oConnection);
            long stop = System.currentTimeMillis();  // Вычисляем время выполнения метода
            String sTime = ((double)(stop-start)/1000 + " seconds");
            oLog.info("[" +sCase+ "](Время выполнения: " +sTime);
        }
    }

    /**
     * Возвращает nID из таблицы Access по Емайлу из этой же таблицы
     *
     * @param sLogin - Емаил пользователя
     * @return
     * @throws Exception
     */
    public int nGetIdAccess(String sLogin) throws Exception {
        String sCase = "nGetIdAccess";
        int nID = 0;
        Connection oConnection = null;
        Statement oStatement = null;
        try {
            oConnection = AccessDB.oConnectionStatic(sCase);
            oStatement = AccessDB.oStatementStatic(oConnection, sCase);
            ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT TOP 1 nID FROM Access where sLogin = '" + sLogin + "'", oLog);
            if (oRowset.next()) {
                nID = oRowset.getInt(1);
            }
        } catch (Exception oException) {
            oLog.error("[" + sCase + "](nID= " + nID + " sLogin= " + sLogin + "): Ошибка получения записи! Класс Access", oException);
        } finally {
            AccessDB.close(sCase, oStatement);
            AccessDB.closeConnectionStatic(sCase, oConnection);
            return nID;     // Вернет ID, а если Емайла нету то вернет 0 
        }
    }

    /**
     * Регистрирует Юзера по введенным Логину и Паролю 1. 1.Проверяет Логин и
     * Пароль на валидность - Основная проверка осуществляется на клиенте а, эта
     * проверка на всякий случай на сервере, чтобы отключив JS, нельзя было
     * отправить прямой запрос и зарегистрироватся с неверными данными иди
     * создать ошибку. 2. Прописывает (целую цепочку) в Базу данные о
     * регистрации
     *
     * @param sEmail - Емайл пользователя
     * @param sPassword - Пароль пользователя
     * @return - Возвращает Строку либо с описанием ошибки или с подтверждением
     * регистрации.
     * @throws Exception
     */
    public String sUserRegistration(String sEmail, String sPassword) throws Exception {
        long start = System.currentTimeMillis();  // Вычисляем время выполнения метода
        String sCase = "sUserRegistration";

        if ((sEmail == null) | (sPassword == null)) {
            return "Одна или несколько строк Null";
        } else if ((sEmail.equals("")) | (sPassword.equals(""))) {//проверка на пустые строки
            return "Внимание, заполните поля!";
        } else if ((!bValidString(sEmail)) | (!bValidString(sPassword))) {//Если true то прис. недопустимые символы 
            return "Логин или Пароль содержат недопустимые символы!";
        } else if (sPassword.length() < 10) {//проверка длинны пароля
            return "Длинна пароля должна быть больше 10 символов!";
        } else if (sLoginExists(sEmail)!=null) {//если != null то логин уже существует в Базе
            return "Этот Логин уже занят!";
        }

        Statement oStatement = null;
        Connection oConnection = null;
        try {
            oConnection = AccessDB.oConnectionStatic(sCase); //ОБРАЗЕЦ
            oStatement = AccessDB.oStatementStatic(oConnection, sCase); //ОБРАЗЕЦ
            AccessDB.transactBegin(oStatement, sCase, oLog);    //Начинаем транзакцию  ОБРАЗЕЦ//  

            // вставляем по умолчанию запись "1" т.е "человек"
            AccessDB.nRowsetUpdate(oStatement, sCase, "INSERT INTO TheSubject (nID_OfSubject) VALUES (1)", oLog);
            int nLastIdentityTheSubject = AccessDB.nRowsetID(oStatement, sCase, oLog);//ОБРАЗЕЦ!!!

            //TODO: -- Поправить поля таблицы черег AlterTable, сделав допустимость null-значений в полях, и соотв. раскомментировать строку
            AccessDB.nRowsetUpdate(oStatement, sCase, "INSERT INTO TheSubjectHuman(nID_TheSubject, nSex) "
                    + "VALUES (" + nLastIdentityTheSubject + ",1)", oLog);
            //AccessDB.nRowsetUpdate(oStatement, sCase, "INSERT INTO TheSubjectHuman(nID_TheSubject, sTheSubjectHuman, sLastName, sFirstName, sSurName, sDTbirth, sDTdeath, nSex ) "
            //           + "VALUES (" + nLastIdentityTheSubject + ",'Человек','Фамилия','Имя','Отчество','1900-11-11 11:11:11','1900-11-11 11:11:11',1)", oLog);
            int nLastIdentityTheSubjectHuman = AccessDB.nRowsetID(oStatement, sCase, oLog);//ОБРАЗЕЦ!!!

            AccessDB.nRowsetUpdate(oStatement, sCase, "INSERT INTO Access (nID_TheSubjectHuman, sLogin, sPassword, nDisabled )"
                    + " VALUES (" + nLastIdentityTheSubjectHuman + ",'" + sEmail + "','" + sPassword + "',0)", oLog);

            AccessDB.transactCommit(oStatement, sCase, oLog);    //Подтверждаем транзакцию   ОБРАЗЕЦ//
            return "Добро пожаловать на сайт!";     // нельзя менять т.к работает как Колбэк "Учетная запись создана !"
        } catch (Exception oException) {
            oLog.error("[" + sCase + "](sEmail= " + sEmail + "  : Ошибка Регистрации пользователя! Класс Access", oException);     //ОБРАЗЕЦ   //так делать всегда!
            AccessDB.transactRollback(oStatement, sCase, oLog);    //ОБРАЗЕЦ//
            return "Ошибка Регистрации пользователя!: Класс Access";
        } finally {
            AccessDB.close(sCase, oStatement);    //ОБРАЗЕЦ
            AccessDB.closeConnectionStatic(sCase, oConnection);    //ОБРАЗЕЦ
            
            long stop = System.currentTimeMillis();  // Вычисляем время выполнения метода
            String sTime = ((double)(stop-start)/1000 + " seconds");
            oLog.info("[" +sCase+ "](Время выполнения: " +sTime);
        }
    }

    /**
     * Заключительный этап после регистрации Юзера - Внести его данные в Сессию,
     * создать ему Куку и сохранить ее в Базу
     *
     * @param sEmail
     * @param sPassword
     * @param oSession
     * @param sIP - IP данного пользователя
     * @return - Возвращает сгенерированную Куку пользователя
     * @throws Exception
     */
    public String sAfretRegister(String sEmail, String sPassword, HttpSession oSession, String sIP) throws Exception {
        String sCase = "sAfretRegister";
        Access A = new Access();
        Date d = new Date();          // узнаем текущую дату
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String sTimeLogin = df.format(d);

        int nID = A.nGetIdAccess(sEmail);      // Получаем NID пользователя (из Access) по его Емайлу
        if (nID == 0) {
            oLog.error("[" + sCase + "](sEmail= " + sEmail + " sIP= " + sIP + "  :  Ошибка! nID из Access вернулся пустой! Класс Access");
            return "Ошибка sUserRegistration, nID == 0";       // прерываем дальнейший вход на сайт
        }
        oSession.setAttribute("sEmail", sEmail);
        oSession.setAttribute("sPassword", sPassword);

        AccessAuth oAccessAuth = new AccessAuth();
        String sGenerate = oAccessAuth.generateString();   // генерируем строку 50 символов для куки
        String sCreateCookie = nID + "&" + sGenerate;    // соединяем в одну куку
        oAccessAuth.saveCookieToDB(nID, sCreateCookie, sTimeLogin, 3);

        // Запись в базу инфы о пользователе при попытке его Входа (при первой регистрации)
        AccessOf oAccessOf = new AccessOf();
        oAccessOf.saveInfo(sEmail, sIP, 1); // 1 - доступ был открыт т.к первый раз входит

        return sCreateCookie;
    }

    /**
     * Возвращает Пароль по Логину. Возвращает null если такого Пароля у Логина
     * нету
     *
     * @param sLogin
     * @return
     */
    public String sGetPassword(String sLogin) {
        String sCase = "sGetPassword";
        String sPassword = null;
        Statement oStatement = null;
        Connection oConnection = null;
        try {
            oConnection = AccessDB.oConnectionStatic(sCase);
            oStatement = AccessDB.oStatementStatic(oConnection, sCase);
            ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT TOP 1 sPassword FROM Access WHERE sLogin='" + sLogin + "'", oLog); //ОБРАЗЕЦ
            if (oRowset.next()) {
                sPassword = oRowset.getString(1);
            }

        } catch (Exception oException) {
            oLog.error("[" + sCase + "](sLogin= " + sLogin + " :Ошибка получения Пароля!", oException);
            throw oException;
        } finally {
            AccessDB.close(sCase, oStatement);
            AccessDB.closeConnectionStatic(sCase, oConnection);
            return sPassword;
        }

    }

    /**
     * Возвращает Емайл есои он в Базе присутствует, null - если отсутствует
     *
     * @param sLogin - Емаил Пользователя
     * @return -  Емайл пользователя или null
     */
    public String sLoginExists(String sLogin) {
        long start = System.currentTimeMillis(); // Вычисляем время выполнения метода
        String sCase = "bLoginExists";
        String sResult = null;
        Statement oStatement = null;
        Connection oConnection = null;
        try {
            oConnection = AccessDB.oConnectionStatic(sCase); //ОБРАЗЕЦ
            oStatement = AccessDB.oStatementStatic(oConnection, sCase); //ОБРАЗЕЦ
            ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT TOP 1 sLogin FROM Access WHERE sLogin=lower('" + sLogin + "')", oLog); //ОБРАЗЕЦ            
            if (oRowset.next()) {
                sResult = oRowset.getString(1);
            }
               //Thread.sleep(2000);
        } catch (Exception oException) {
            oLog.error("[" + sCase + "](sLogin= " + sLogin + " : Ошибка проверки Логина!", oException);     //ОБРАЗЕЦ//так делать всегда!                 // String sErr = _.getMessage();  System.err.println("ERROR: " + sErr + "_" + " ---- bLoginExists");   //это вывод в лог-файл
        } finally {
            AccessDB.close(sCase, oStatement);
            AccessDB.closeConnectionStatic(sCase, oConnection);
            long stop = System.currentTimeMillis();  // Вычисляем время выполнения метода
            String sTime = ((double)(stop-start)/1000 + " seconds");
            oLog.info("[" +sCase+ "](Время выполнения: " +sTime);
            return sResult;    // возвращаем Логин
        }

//        if ( (sResult != null) && (!sResult.equals("")) && (sLogin.equalsIgnoreCase(sResult))) {    // Если Логин есть в базе
//            return true;    // true - такой Логин уже существует       
//        } else {
//            return false;   // false - Логин свободен
//        }
    }


    /**
     * Проверка строки на наличие в ней только символов:
     * ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_-
     *
     * @.
     * @param sVerify
     * @return True - значит строка содержит только перечисленные символы
     */
    public boolean bValidString(String sVerify) {
        String s = sVerify; //"ascDFMSes"; // проверяемая строка
        String check = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_-@."; // допустимые символы
        for (int n = 0; n < s.length(); n++) {
            char sChar = s.charAt(n);
            if (check.indexOf(String.valueOf(sChar)) == -1) { //в строке есть неразрешенный символ
                return false;//"В строке содержатся недопустимые символы!";
            }
        }
        return true; // строка валидная
    }

    /**
     * Проверка Емайла на синтаксическую валидность
     *
     * @param sVerify
     * @return True - Значит валидный Емайл
     */
    public boolean bValidMail(String sVerify) {
        final Pattern pattern = Pattern.compile("^[A-Za-z0-9.%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}");
        final Matcher matcher = pattern.matcher(sVerify);
        //"mail@sd.com"
        if (matcher.find()) {
            return true;    //"Емаил правильный!"; //Емаил правильный!
        } else {
            return false;    //""; //Ошибочный!
        }
    }
    //----------------------------------------------------------------
    
    
    // Получаем список всех городов по одному выбранному региону 
     public String getStringAddressPolis(String sPolis, int nID_PlaceCountry)  {
        //   DOMConfigurator.configure("D:/My Documents/NetBeansProjects/Business/web/WEB-INF/config/log4j.xml");
          String sCase = "getStringAddressPolis";
          String s = "";
         // String s2 = "";
          int i_count = 1;

          aResult.clear();

          Connection oConnection = null;
          Statement oStatement = null;
          //Connection oConnection = AccessDB.oConnectionStatic("");
          //ResultSet oRowset = oConnection.prepareStatement(
          try {
               oConnection = AccessDB.oConnectionStatic(sCase);
               oStatement = AccessDB.oStatementStatic(oConnection, sCase);
               ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase,
                       " SELECT sPRT6=PRT6.sRegionType,  sPR6=PR6.sRegion, sPPT66=PPT66.sPolisType, sPPP6=PPP6.sPolis, "
                       + " sPRT5=PRT5.sRegionType,  sPR5=PR5.sRegion, sPPT55=PPT55.sPolisType, sPPP5=PPP5.sPolis, "
                       + " sPRT4=PRT4.sRegionType,  sPR4=PR4.sRegion, sPPT44=PPT44.sPolisType, sPPP4=PPP4.sPolis, "
                       + " sPRT3=PRT3.sRegionType,  sPR3=PR3.sRegion, sPPT33=PPT33.sPolisType, sPPP3=PPP3.sPolis, "
                       + " sPRT2=PRT2.sRegionType,  sPR2=PR2.sRegion, sPPT22=PPT22.sPolisType, sPPP2=PPP2.sPolis, "
                       + " sPRT1=PRT1.sRegionType,  sPR1=PR1.sRegion, sPPT11=PPT11.sPolisType, sPPP1=PPP1.sPolis, "
                       + " sPPT1=PPT1.sPolisType,  PP.sPolis,  PP.nID "
                       //                "SELECT  PP.sPolis,  PP.nID, sPPT1=PPT1.sPolisType, sPR1=PR1.sRegion " 

                       + "  FROM PlacePolis PP  "
                       + "left JOIN PlacePolisType PPT1 ON PPT1.nID = PP.nID_PlacePolisType  " // только 1 раз для типа полиса 

                       + "left JOIN PlaceRegion PR1 ON PR1.nID = (SELECT nID_PlaceRegion  FROM PlaceRegionTree where nID_PlaceRegion = PP.nID_PlaceRegion) "
                       + "left JOIN PlaceRegionType PRT1 ON PRT1.nID = PR1.nID_PlaceRegionType " //  -- тип 1-го региона 
                       + "left JOIN PlacePolis PPP1 ON PPP1.nID = PR1.nID " //-- полис на замену региона 
                       + "left JOIN PlacePolisType PPT11 ON PPT11.nID = PPP1.nID_PlacePolisType " //-- тип полиса на замену региона 

                       + "left JOIN PlaceRegion PR2 ON PR2.nID = (SELECT  nID_PlaceRegion_Node  FROM PlaceRegionTree where nID_PlaceRegion = PR1.nID) "
                       + "left JOIN PlaceRegionType PRT2 ON PRT2.nID = PR2.nID_PlaceRegionType  " //-- тип 2-го региона 
                       + "left JOIN PlacePolis PPP2 ON PPP2.nID = PR2.nID " //-- полис на замену региона 
                       + "left JOIN PlacePolisType PPT22 ON PPT22.nID = PPP2.nID_PlacePolisType " //-- тип полиса на замену региона 

                       + "left JOIN PlaceRegion PR3 ON PR3.nID = ( SELECT  nID_PlaceRegion_Node  FROM PlaceRegionTree where nID_PlaceRegion = PR2.nID) "
                       + "left JOIN PlaceRegionType PRT3 ON PRT3.nID = PR3.nID_PlaceRegionType  " //-- тип 3-го региона 
                       + "left JOIN PlacePolis PPP3 ON PPP3.nID = PR3.nID " //-- полис на замену региона 
                       + "left JOIN PlacePolisType PPT33 ON PPT33.nID = PPP3.nID_PlacePolisType " //-- тип полиса на замену региона 

                       + "left JOIN PlaceRegion PR4 ON PR4.nID = ( SELECT  nID_PlaceRegion_Node  FROM PlaceRegionTree where nID_PlaceRegion = PR3.nID) "
                       + "left JOIN PlaceRegionType PRT4 ON PRT4.nID = PR4.nID_PlaceRegionType  " // -- тип 4-го региона 
                       + "left JOIN PlacePolis PPP4 ON PPP4.nID = PR4.nID " //-- полис на замену региона 
                       + "left JOIN PlacePolisType PPT44 ON PPT44.nID = PPP4.nID_PlacePolisType " //-- тип полиса на замену региона 

                       + "left JOIN PlaceRegion PR5 ON PR5.nID = ( SELECT  nID_PlaceRegion_Node  FROM PlaceRegionTree where nID_PlaceRegion = PR4.nID) "
                       + "left JOIN PlaceRegionType PRT5 ON PRT5.nID = PR5.nID_PlaceRegionType  " //-- тип 5-го региона 
                       + "left JOIN PlacePolis PPP5 ON PPP5.nID = PR5.nID " //-- полис на замену региона 
                       + "left JOIN PlacePolisType PPT55 ON PPT55.nID = PPP5.nID_PlacePolisType " //-- тип полиса на замену региона 

                       + "left JOIN PlaceRegion PR6 ON PR6.nID = ( SELECT  nID_PlaceRegion_Node  FROM PlaceRegionTree where nID_PlaceRegion = PR5.nID) "
                       + "left JOIN PlaceRegionType PRT6 ON PRT6.nID = PR6.nID_PlaceRegionType " //-- тип 6-го региона 
                       + "left JOIN PlacePolis PPP6 ON PPP6.nID = PR6.nID " //-- полис на замену региона 
                       + "left JOIN PlacePolisType PPT66 ON PPT66.nID = PPP6.nID_PlacePolisType " //-- тип полиса на замену региона  

                       //+ "where  PP.sPolis LIKE \'"+sPolis+"%\' " 
                       + "where PR1.nID_PlaceCountry = " + nID_PlaceCountry + " and  PP.sPolis LIKE \'" + sPolis + "%\' ", oLog);

               while (oRowset.next() & i_count < 100) { // не загружаем больше 100 записей
                    i_count++;
                    ////// s += (  ",\"a"+ i +"\":" + "\"" +oRowset.getString(1) + "\"");
//1 2 3 4
//null null null null 
//5 6 7 8
//обл. ПОЛТАВСЬКА null null 
//9 10 11 12
//г.п РАЙОНИ ПОЛТАВСЬКОЇ ОБЛАСТІ null null 
//13 14 15 16
//р-н. ПИРЯТИНСЬКИЙ null null 
//17 18 19 20
//г.п МІСТА РАЙОННОГО ПІДПОРЯДКУВАННЯ ПИРЯТИНСЬКОГО Р-НУ null null 
//21 22 23 24
//м.р. ПИРЯТИН м. ПИРЯТИН 
//25 26 27
//с. ЗАМОСТИЩЕ 26466        


                    String[] sArr2 = {oRowset.getString(1), oRowset.getString(2), oRowset.getString(3), oRowset.getString(4),
                         oRowset.getString(5), oRowset.getString(6), oRowset.getString(7), oRowset.getString(8),
                         oRowset.getString(9), oRowset.getString(10), oRowset.getString(11), oRowset.getString(12),
                         oRowset.getString(13), oRowset.getString(14), oRowset.getString(15), oRowset.getString(16),
                         oRowset.getString(17), oRowset.getString(18), oRowset.getString(19), oRowset.getString(20),
                         oRowset.getString(21), oRowset.getString(22), oRowset.getString(23), oRowset.getString(24),
                         oRowset.getString(25), oRowset.getString(26), oRowset.getString(27)
                    };

                    aResult.add(sArr2);

               }
              // AccessDB.closeConnectionStatic("", oConnection);



               // String s = "";
               // String s1 = "";

               //String[] sArr3

               sObject = "";

               aResult2.clear();

               for (int i = 0; i <= aResult.size() - 1; i++) {
                    String[] sArr = aResult.get(i);

                    s = "";

                    //for (String temp: aResult_PlaceRegionTree){ System.out.println(temp); }
                    //    System.out.println( sArr[0]+" "+sArr[1]+" "+sArr[2]+" "+sArr[3]+"   "+sArr[4]+" "+sArr[5]+" "+sArr[6]+" "+sArr[7] ); 


                    if ((sArr[0] != null)
                            & (!"г.п".equals(sArr[0]))) {
                         if (sArr[2] != null) { // если есть замена
                              s = s + "  " + sArr[2] + " " + sArr[3]; // если есть замена
                         } else {
                              s = s + "  " + sArr[0] + " " + sArr[1]; // если нет замены
                         }
                    }

                    if ((sArr[4] != null)
                            & (!"г.п".equals(sArr[4]))) {
                         if (sArr[6] != null) { // если есть замена
                              s = s + "  " + sArr[6] + " " + sArr[7]; // если есть замена
                         } else {
                              s = s + "  " + sArr[4] + " " + sArr[5]; // если нет замены
                         }
                    }

                    if ((sArr[8] != null)
                            & (!"г.п".equals(sArr[8]))) {
                         if (sArr[10] != null) { // если есть замена
                              s = s + "  " + sArr[10] + " " + sArr[11]; // если есть замена
                         } else {
                              s = s + "  " + sArr[8] + " " + sArr[9]; // если нет замены
                         }
                    }


                    if ((sArr[12] != null)
                            & (!"г.п".equals(sArr[12]))) {
                         if (sArr[14] != null) { // если есть замена
                              s = s + "  " + sArr[14] + " " + sArr[15]; // если есть замена
                         } else {
                              s = s + "  " + sArr[12] + " " + sArr[13]; // если нет замены
                         }
                    }

                    if ((sArr[16] != null)
                            & (!"г.п".equals(sArr[16]))) {
                         if (sArr[18] != null) { // если есть замена
                              s = s + "  " + sArr[18] + " " + sArr[19]; // если есть замена
                         } else {
                              s = s + "  " + sArr[16] + " " + sArr[17]; // если нет замены
                         }
                    }

                    if ((sArr[20] != null)
                            & (!"г.п".equals(sArr[20]))) {
                         if (sArr[22] != null) { // если есть замена
                              s = s + "  " + sArr[22] + " " + sArr[23]; // если есть замена
                         } else {
                              s = s + "  " + sArr[20] + " " + sArr[21]; // если нет замены
                         }
                    }


                    s = s + " <b>" + sArr[24] + " " + sArr[25] + "</b> "/*+ sArr[26]*/; // если есть замена


                    aResult2.add(s);


//        s =   oRowset.getString(1)+" "
//            + oRowset.getString(2)+" "
//            + oRowset.getString(3)+" "


                    sObject = sObject + ("{ \"nID_Polis\": " + sArr[26] + "  ,  \"value\":\" " + aResult2.get(i) + " \"} ,");
                    //aResult3.add(" { \"nID_Polis\":\" "+sArr[26]+" \"  ,   \"value\":\" "+aResult2.get(i) + " \" } , ");   
                    //if  (i != aResult2.size()-1){

               }

               sObject = "[" + sObject.substring(0, sObject.length() - 1) + "]";

               // делаем объект  
// aResult3.add("\"[");
//  for (int i = 0; i <= aResult2.size()-1; i++)  {
//
//  aResult3.add(" { \"nID_Polis\":\" "+aResult2.get(i)+" \"  ,   \"value\":\"22222\" }  ");
//  if  (i != aResult2.size()-1){
//  aResult3.add(" , ");
//}



               //}
               //aResult3.add("\"]");

               //String ss = Arrays.toString(sArr);       
             //------------  System.out.println(sObject);

               //s += (  ",\""+ oRowset.getInt(1) +"\":" + "\"" +oRowset.getString(2) + "\"");
               //  if (i==1000) break; // не загружаем больше 100 записей


               //sReturnLogin = " [{ \"nID_Region\":\"34\"  ,  \"value\":\"Днепропетровская обл.\" }, { \"nID_Region\":38  ,  \"value\":\"Киевская обл. Район Мироновский\"} ] ";
               //sReturnLogin =  " [{ \"nID_Region\":\"34\"  ,  \"value\":\"Днепропетровская обл.\" }, { \"nID_Region\":38  ,  \"value\":\"Киевская обл. Район Мироновский\"} ] ";




          } catch (Exception oException) {
                 oLog.info("[" + sCase + "] (sLogin= " + "): Ошибка Конструктора ", oException);
                 System.out.println(oException);
          } finally {
               AccessDB.close(sCase, oStatement); 
               AccessDB.closeConnectionStatic(sCase, oConnection);  
               return sObject;
          }
     }
    
    
    
    
    
    
//     /**
//      *  Возвращает nID из таблицы Access по Емайлу в этой же таблице
//      * @param sLogin
//      * @return
//      */
//     public String getNID(String sLogin) {
//          String s = "";
//          Connection oConnection = AccessDB.oConnectionStatic("");
//          try {
//               ResultSet oRowset = oConnection.prepareStatement("SELECT TOP 1 nID FROM Access WHERE sLogin='" + sLogin + "'").executeQuery();
//
//               if (oRowset.next()) {
//                    s = oRowset.getString(1);
//               }
//               if (s != null) {    // Если nID есть у данного логина в базе
//                    return s;        // отправляем nID        
//               }
//
//          } catch (Exception _) {
//               String sErr = _.getMessage();
//               System.err.println("ERROR: " + sErr + "_" + " ---- getNID");   //это вывод в лог-файл
//
//          } finally {
//               AccessDB.closeConnectionStatic("", oConnection);
//          }
//          return "";  // "" - такого nID нет у Логина"; 
//     }
    //public boolean save(boolean b) {
    //b = true;
    //if (b) {
    //   for (int i = 1; i <= 10; i++) {
    //System.out.println("гав-гав"); }
    //s = "wsd";
    //return b;
//типа метод записи в базу параметров          
//} 
    //Access oAccess = new Access()._bDisabled(1).save();
    //.save(false);
    //public static void main(String args[]) {
//._sLogin("MyLogin")._sPassword("MyPassword").save();
    //SetnID
    //Access ac = new Access() ;  
    // Access oAccess1 = new Access();//.save(false);
    //  ac.SetnID(10);
    // System.out.println(oAccess1.bDisabled);
    //}
//- проверить соответствие указанного пароля указанному логину,
//     boolean bAuthorized(String sLogin, String sPassword) {
//          //boolean b = false;
//
//          return true;
//     }
    /*
 
     //- отредактировать его права,
     boolean bEdited(String sLogin, String oNewData){
 
     }
     //- удалить его из таблицы,
     boolean bDeleted(String sLogin){
 
     }
     //- проверить - доступна-ли ему определенная функция,
     boolean bAccept(String sLogin, String sDO){}

 
     //- какая у него роль, дата создания и т.д.
     boolean sRole(String sLogin){
     }
 
     boolean sDate(String sLogin){
     }

     */
}
