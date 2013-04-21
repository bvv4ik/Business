/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Sergey
 */
public class StringArr {
    public static void main(String args[]) {
    
//    String[][] arr = new String[100][5];
//    arr[1][1] = "dsf1";
//    arr[1][2] = "dsf2";
//    //arr[2] = "dsf2222";
//    
//    arr[3] = arr[1];
//    //System.out.println(lastIndexOf(sCurrentRow1,1)); 
//    for (int i = 0; i <= 99; i++) {
//      System.out.println(arr[i][1]) ; 
//    }
//    
    
        String s[] = {
        "2 0705479240577Кольцо 2.46гр 1624.02",
        "2 0705551560032Кольцо 7.03г 5662.71",
        "2 0705806340118Подвеска 3.76гр (брил.) 6471.19",
        "2 0706123020011Часы 6.40г 8200.85",
        "2 0706851460154Часы 6.09г 8974.58",
        "2 0706873820103Серьги 4.58г 3268.10",
        "2 0710000000004Подвеска 3.61гр бриллиант 4483.05",
        "2 0856000000009Браслет 20.67гр. бриллиант 26637.29",
        "2 0961000000000Подвеска 3.32гр бриллиант 4461.86",
        "2 1069000000002Подвеска 2.14гр бриллиант 4483.05"};
         for (int i = 0; i < s.length; i++) {
             String str = s[i];
             System.out.println(str.substring(0,1));
             System.out.println(str.substring(2,15));
             str = str.substring(15);
             int lastIndex = str.lastIndexOf(' ');
             System.out.println(str.substring(0,lastIndex));
             System.out.println(str.substring(lastIndex+ 1));
 
 
      
    }
    
    
    }
    
}
