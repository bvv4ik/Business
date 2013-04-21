/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.converter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sergey
 */
public class LoadTextFile {
    
    public List ListFromFile (String path ) throws SQLException{
      //int iDatch = 0;
      //String s = "";  
      List list = new ArrayList(); 
      BufferedReader br = null;

        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));
            while ((sCurrentLine = br.readLine()) != null) {
                list.add(sCurrentLine);       
            }
                
            System.out.println("Всего элементов в массиве 11111111222 "+list.size());
           // System.out.println(list.get(5));
          //      System.out.println(s);
        
    
         
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                
                
                 
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            return list;
        }
  
  }  
    
}
    

