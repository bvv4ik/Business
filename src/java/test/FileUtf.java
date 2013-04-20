/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author Sergey
 */
public class FileUtf {
    
    
    static String readInput1(String path ) {

    StringBuffer buffer = new StringBuffer();
    try {
	FileInputStream fis = new FileInputStream("c:/3.txt");
	InputStreamReader isr = new InputStreamReader(fis,
						      "UTF-8");
	Reader in = new BufferedReader(isr);
	int ch;
	while ((ch = in.read()) > -1) {
		buffer.append((char)ch);
	}
        
             System.out.println(in.read());
        
	in.close();
	return buffer.toString();
    } catch (IOException e) {
	e.printStackTrace();
	return null;
    }
    
    }
    
    
    
    static String readInput() {

    StringBuffer buffer = new StringBuffer();
    try {
	FileInputStream fis = new FileInputStream("c:/3.txt");
	InputStreamReader isr = new InputStreamReader(fis,
						      "UTF-8");
	Reader in = new BufferedReader(isr);
	int ch;
	while ((ch = in.read()) > -1) {
		buffer.append((char)ch);
	}
        
        in.close();
	return buffer.toString();
    } catch (IOException e) {
	e.printStackTrace();
	return null;
    }
    
    }

    
    
     public static void main(String[] args) {
         
         System.out.println(readInput1("ва"));
         
}
    
}
