/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Sergey
 */
import javax.xml.bind.DatatypeConverter;
//import org.apache.commons.codec.binary.Base64;
import java.io.IOException;

public class AeSha1_2 {

    private static volatile String save = null;

    public static void main(String argv[]) throws InterruptedException {
        String teststr = "john:password1";
        String b64 = DatatypeConverter.printBase64Binary(teststr.getBytes());

        long start;
        long stop;

        start = System.currentTimeMillis();
        System.out.println(start);

        // Thread.sleep(1000);
        System.out.println(teststr + " = " + b64);

        stop = System.currentTimeMillis();
        System.out.println(stop);

//        try {
//            final int COUNT = 1000000;
//            long start;
//            start = System.currentTimeMillis();
//            
//            for (int i=0; i<COUNT; ++i) {
//                save = new String(DatatypeConverter.parseBase64Binary(b64));
//            }
//            System.out.println("javax.xml took "+(System.currentTimeMillis()-start)+": "+save);
//            start = System.currentTimeMillis();
//            
////            for (int i=0; i<COUNT; ++i) {
////                save = new String(Base64.decodeBase64(b64));
////            }
//            System.out.println("apache took    "+(System.currentTimeMillis()-start)+": "+save);
//            sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
//            start = System.currentTimeMillis();
//            
//            for (int i=0; i<COUNT; ++i) {
//                save = new String(dec.decodeBuffer(b64));
//            }
//            
//            System.out.println("sun took       "+(System.currentTimeMillis()-start)+": "+save);
//        } catch (Exception oException) {
//            System.out.println(oException);
//        }
    }
}
