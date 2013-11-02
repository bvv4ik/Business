package business;

import java.util.LinkedList;
import java.util.List;

/**
 * Формирует и валидирует SQL
 * @author Belyavtsev Vladimir Vladimirovich (BW)
 */
public class ManagerSQL {

    List<String> aPair = new LinkedList();

    public ManagerSQL _Pair(String sPair) {
        return _Pair(sPair, true);
    }

    public ManagerSQL _Pair(String sPair, boolean bCondition) {
        if (bCondition) {
            aPair.add(sPair);
        }
        return this;
    }

    private String saPair() {
        String saPair = "";
        int n = 0;
        for (String sPair : aPair) {
            n++;
            saPair += (n > 1 ? ", " : "") + sPair;
        }
        return saPair;
    }

    public String sSQL(String sPrefix, String sPostfix) {
        String sSQL = null;
        if (aPair.size() > 0) {
            String saPair = saPair();
            if (saPair.length() > 0) {
            }
            sSQL = sPrefix + " " + saPair + " " + sPostfix;
        }
        return sSQL;
    }

}
