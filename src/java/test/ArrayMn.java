/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Sergey
 */
public class ArrayMn {
    
public static void main(String args[]) {
int twoD[] []= new int[4] [5] ;
int i1 = 0;
int i2 = 0; 
int k = 0; 

for(i1=0; i1<4; i1++) 
for(i2=0; i2<5; i2++) { 
    twoD[i1] [i2] = k; k++;
}
    
    
//for(i=0; i<4; i++) ( for(j=0; j<5; j++) 
//System.out.print(twoD[i1][i2] + " "); 

String formatted = String.format("%07d", 1144);
System.out.println(formatted) ; 
String s = "0120481900";
System.out.println(s.substring(3, 5)) ; 

    
}
} 


    
