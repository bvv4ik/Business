/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

//import com.bw.entity.TheSubjectHuman;
import business.model.Place;
import business.model.TheSubjectHuman;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//import com.bw.entity.access.Ser.*;
/**
 *
 * @author Ser
 */
public class Ser {

    public static void main(String[] args) throws Exception {


        //       Connection oConnection = AccessDB.oConnectionStatic("");    
        //  oConnection.prepareStatement("DROP table PlaceRegion1").executeUpdate();
        // AccessDB.closeConnectionStatic("", oConnection);    

        //PlaceRegion PR = new PlaceRegion();
        //PR.setRegion(1, "Николаевская область");

        //   PlaceBuild PB = new PlaceBuild();
        //   int nID_PlaceBuild = PB.getID("8а");

        //      System.out.println(nID_PlaceBuild); 
        //PlaceBuild PB = new PlaceBuild();
        //String s = PB.getAllBuild(2);
        //System.out.println(s); 

        //String sReturnLogin = "{\"sReturnLogin\":\"Branch\""+ s +" }";
        //System.out.println(sReturnLogin); 

        //Boolean b = ConnectLdap.isLoginExists("Vasiliy");        
        //if (ConnectLdap.isLoginExists("Vasiliy")) 
        //System.out.println(b); 

        //  System.out.println(Access.bValidMail ("sfg@fa.aafdgsfgsdfgdf"));

        Place P = new Place();

        //P.setPlace("aaa@aa.aa", "Украина", null);
        P.setPlace1("1");

        //setPlace (String sLogin, String sCountry, String sRegion) 

        System.out.println(System.getProperty("user.dir"));

        System.out.println(System.getProperty("user.dir"));
        System.out.println(System.getProperty("user.home"));
        System.out.println(System.getProperty("user.name"));
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("java.vm.name"));
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("os.arch"));
        System.out.println(System.getProperty("os.version"));

        System.out.println(System.getProperty("line.separator"));
        System.out.println(System.getProperty("java.vendor"));
        System.out.println(System.getProperty("java.vendor.url"));
        System.out.println(System.getProperty("java.vendor"));
        System.out.println(System.getProperty("java.vendor"));
        System.out.println(System.getProperty("java.vendor"));



        System.out.println("------------------------------------------");

        TheSubjectHuman TSH = new TheSubjectHuman();
        TSH.getTableData("111");
        //String s = TSH.sFirstName()+sLastName().;
        System.out.println(TSH.sFirstName());
        System.out.println(TSH.sLastName());
        System.out.println(TSH.sSurName());

        //System.out.println(TheSubjectHuman.getLastName("ddd"));
        //System.out.println(Access.bLoginExists("Vasya"));
        System.out.println("------------------------------------------");
        //if (bLoginExists(sLogin)

        //System.out.println(Authorization.getFirstLastSureName("sLastName", "ddd"));



        //Registration R = new Registration();

        //    String s = (Registration.sUserRegistration("2221", "", "", ""));
        //   System.out.println(s); 


        //String s = (R.sUserRegistration(sLoginReg, sPasswordReg1, sPasswordReg2, sEmailReg));

        //System.out.println("Current Time");
//        long tmp = 1018021774496l;
        //      Date d = new Date(tmp);  
        //     System.out.println(d); 
        //    System.out.println(System.currentTimeMillis());



        /*String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
         // if no ids were returned, something is wrong. get out.
         if (ids.length == 0)
         System.exit(0);

         // begin output
         System.out.println("Current Time");
         SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);
         pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
         pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
         Calendar calendar = new GregorianCalendar(pdt);
         Date trialTime = new Date();
         calendar.setTime(trialTime);
 
         System.out.println("ERA: " + calendar.get(Calendar.ERA));
         System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
         System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
         System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
         System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
         System.out.println("DATE: " + calendar.get(Calendar.DATE));
         System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
         System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
         System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
         System.out.println("DAY_OF_WEEK_IN_MONTH: "
         + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
         System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
         System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
         System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
         System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
         System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
         System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND)); */
//GregorianCalendar today = new GregorianCalendar();
        //int result = today.get(Calendar.YEAR);
//int result1 = today.get(Calendar.MONTH);
//int result2 = today.get(Calendar.DAY_OF_MONTH);

//System.out.println(result);
//System.out.println(result1);
//System.out.println(result2);
        Date d = new Date();
        //DateFormat df = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd");
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        System.out.println(df.format(d));

//SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        System.out.println(formatter.format(new Date()));
//System.out.println(new Date("yyyy.MM.dd hh:mm:ss"));



//VerifyLogin ("sVerify"); 
        //System.out.println(VerifyMail("a9@dg.fg"));

        //Dog d = new Dog();
        //d.age = 2 ;
        //d.voice();

        // Dog dog1 = new Dog("Тузик", 2);
        // dog1.voice();
        // Dog dog2 = new Dog("Тузик",2,2);
        // dog2.voice();
        //  BigDog D = new BigDog();
        // D.voice();
        Dog dog2 = new Dog("dfs", 4);
        dog2.voice();

        Dog dog3 = new Dog(2);
        dog3.voice();

        Dog dog4 = new Dog(5);
        dog4.voice();

        BigDog B = new BigDog();
        B.voice();
        //{ super(s, i); }

        System.out.println("Всего было создано собак: " + Dog.count);

        Dog d_ = new Dog();
        System.out.println("Курс " + d_.toUSD(5, 5.7));

    }
}

class Dog {

    private int age;  // возраст 
    private String name;  // кличка 
    public static int count = 0;

    public Dog() {
        name = "Незнакомец";
    }

    public Dog(String s, int i, int i2) {
        name = s; //+ " dsf"
        age = i;
        count++;

    }

    public Dog(String s, int i) {
        name = s;
        age = i;
        count++;
    }
////*  

    public Dog(int i) {
        age = i;
        count++;
    }

    long squearSum(int x, int y) {
        return x * x + y * y;
    }
    private double result = 0;

    public double toUSD(int rub, double course) {
        result = rub * course;
        return (result);
    }

    //*/
    public void voice() {
        for (int i = 1; i <= age; i++) {
            // System.out.println(name); 
            // System.out.println("гав-гав"); 
        }
    }
}

class BigDog extends Dog {

    public void voice() {
        for (int i = 1; i <= 30; i++) {
            System.out.print("ГАВ-");
        }
    }
}
