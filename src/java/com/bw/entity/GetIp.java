/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.entity;

/**
 *
 * @author Sergey
 */
import java.net.*; 
 
public class GetIp { 
 
    public static void main( String argv[]) throws Exception { 
 
InetAddress host = null; 
host = InetAddress.getLocalHost(); 
byte ip[] = host.getAddress(); 
 
System.out.println(host.getHostName()); 
 
for (int i=0; i<ip.length; i++){ 
 
    if(i>0) System.out.print ("."); 
    System.out.print(ip[i] & 0xff); 
 
} 
 
System.out.println(); 
    } 
} 