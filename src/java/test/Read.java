/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author Sergey
 */
public class Read {

    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {

//    File f = new File("C:/111.txt");
//char[] CB = new char[(int) f.length()];
//Reader reader = new InputStreamReader(new FileInputStream(f), "UTF-8");
//reader.read(CB);
        //System.out.println(CB.));

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream("c:/111.txt")));
        } catch (FileNotFoundException e1) {
            System.out.println("Файл не найден");
            System.exit(0);
        }

    }
}
