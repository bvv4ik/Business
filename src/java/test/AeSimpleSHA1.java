/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import sun.misc.BASE64Encoder;
 
import java.io.UnsupportedEncodingException; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 
 
public class AeSimpleSHA1 { 
 
    public String str1 = "";
    
    private static String convertToHex(byte[] data) { 
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    } 
 
    public static String SHA1(String text) 
    throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
    MessageDigest md;
    md = MessageDigest.getInstance("SHA-1");
    //byte[] sha1hash = new byte[40];
    md.update(text.getBytes("iso-8859-1"), 0, text.length());
    //sha1hash = md.digest();
    //sha1hash = (new BASE64Encoder()).encode(md.digest());
    //sha1hash = convertToHex(sha1hash);
    String sEncrypted = "1  " + (new BASE64Encoder()).encode(md.digest());
    

    
        //System.out.println("*******************") +(new BASE64Encoder()).encode(md.digest());
    //return convertToHex(sha1hash);
    return sEncrypted;
    } 
} 

//http://www.online-convert.com/result/15302686ecf7d6319556dd827299a487

//http://www.anyexample.com/programming/java/java_simple_class_to_compute_sha_1_hash.xml

//String sha1_ad1 = AeSimpleSHA1.SHA1("1");   
//System.out.println(sha1_ad1);

//"1"
//hex: 356a192b7913b04c54574d18c28d46e6395428ab
//     356a192b7913b04c54574d18c28d46e6395428ab
//HEX: 356A192B7913B04C54574D18C28D46E6395428AB
//h:e:x: 35:6a:19:2b:79:13:b0:4c:54:57:4d:18:c2:8d:46:e6:39:54:28:ab
//base64: NWoZK3kTsExUV00Ywo1G5jlUKKs=
        
//Вызов делать так public static void main(String[] args) throws Exception {
