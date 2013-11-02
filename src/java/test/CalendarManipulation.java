/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarManipulation {

    public static void main(String s[]) throws ParseException {
        /*Calendar cal = Calendar.getInstance();
         //DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL,  DateFormat.MEDIUM);
         DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
         System.out.println(df.format(cal.getTime()));

         cal.add(Calendar.MINUTE, +5);
         //cal.add(Calendar.HOUR, -4);  // минус 4 часа
         System.out.println(df.format(cal.getTime()));
    
         */
        String sDate1 = "2013.10.23 12:07:55";
        String sDate2 = "2013.10.23 11:07:55";

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date date1 = df1.parse(sDate1);
        Date date2 = df1.parse(sDate2);
        System.out.println(date1.getTime());
        System.out.println(date2.getTime());

        if (date1.getTime() > date2.getTime()) {
            System.out.println("БОльше");
        }

        Date d = new Date();            // узнаем текущее время

        System.out.println(d.after(date1));
        System.out.println(d.getTime());

        String sCurrentTime = df1.format(d);
        Date oTimeCurrent = df1.parse(sCurrentTime);


        //cal1.setTime(date1);
        //cal2.setTime(date2);


//                                 if (date2.getTime() > date2.getTime() ) {
//                                 }
//                                 cal.get(Calendar.DAY_OF_YEAR) == cal_1.get(Calendar.DAY_OF_YEAR)
//                                 
//                                 cal1.add(Calendar.MINUTE, +5); // смотрим сколько будет время через 5 минут
//                                 String s = df1.format(cal.getTime())
//       
//    Date d = new Date();       // узнаем текущее время
//    
//    DateFormat df1 = new SimpleDateFormat("2013.10.23 11:07:55");
//    DateFormat df2 = new SimpleDateFormat("2013.10.23 11:07:55");
//    //DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
//    String sTimeLogin = df1.format(d);
//    
//    if (df1.format(d) > df2.format(d)) {
//    System.out.println(1);
//    
//    }

    }
}