/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Sergey
 */
public class SortArr {

    private static final String IPADDRESS_PATTERN = "([01]?\\d\\d?|2[0-4]\\d|25[0-5])"
            + "\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])"
            + "\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])"
            + "\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
    private static Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
    private static Matcher matcher;

    public static void main(String[] args) {

        String domain = "google.com";

        Process p;
        try {
            //p = Runtime.getRuntime().exec("host -t a " + domain);
            p = Runtime.getRuntime().exec("msconfig");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));

            StringBuffer sb = new StringBuffer();

            String line = reader.readLine();
            sb.append(line);
            while (line != null) {
                line = reader.readLine();
                sb.append(line);
            }

            List<String> list = getIpAddress(sb.toString());

            if (list.size() > 0) {
                System.out.println(domain + " has address : ");
                for (String ip : list) {
                    System.out.println(ip);
                }
            } else {
                System.out.println(domain + " has NO address.");
            }

        } catch (Exception oException) {
            oException.printStackTrace();
        }

    }

    public static List<String> getIpAddress(String msg) {

        List<String> ipList = new ArrayList<String>();

        if (msg == null || msg.equals("")) {
            return ipList;
        }

        matcher = pattern.matcher(msg);
        while (matcher.find()) {
            ipList.add(matcher.group(0));
        }

        return ipList;
    }
}
