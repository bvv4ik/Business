/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.auth;

/**
 *
 * @author Ser
 */
public class AccessTrue {

    private int nID;
    private int nID_AccessRole;
    private int nID_AccessMethod;
// Setters

    public AccessTrue _nID(int i) {
        nID = i;
        return this;
    }

    public AccessTrue _nID_AccessRole(int i) {
        nID_AccessRole = i;
        return this;
    }

    public AccessTrue _nID_AccessMethod(int i) {
        nID_AccessMethod = i;
        return this;
    }

    // Getters
    public int nID() {
        return nID;
    }

    public int nID_AccessRole() {
        return nID_AccessRole;
    }

    public int nID_AccessMethod() {
        return nID_AccessMethod;
    }
}
