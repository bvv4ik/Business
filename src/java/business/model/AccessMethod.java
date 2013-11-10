/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.model;

public class AccessMethod {

    private int nID;
    private String sAccessMethod;

// Setters
    public AccessMethod _nID(int i) {
        nID = i;
        return this;
    }

    public AccessMethod _sAccessMethod(String s) {
        sAccessMethod = s;
        return this;
    }

    // Getters
    public int nID() {
        return nID;
    }

    public String sAccessMethod() {
        return sAccessMethod;
    }
}
