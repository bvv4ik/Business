/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.model;

/**
 *
 * @author Ser
 */
public class AccessMethodTree {

    private int nID_Access;
    private int nID_AccessMethod;

// Setters
    public AccessMethodTree _nID_Access(int i) {
        nID_Access = i;
        return this;
    }

    public AccessMethodTree _nID_AccessMethod(int i) {
        nID_AccessMethod = i;
        return this;
    }

    // Getters
    public int nID_Access() {
        return nID_Access;
    }

    public int nID_AccessMethod() {
        return nID_AccessMethod;
    }
}
