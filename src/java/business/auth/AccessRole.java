/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.auth;

/**
 *
 * @author Ser
 */
public class AccessRole {
    
     
private int nID;
private String  sAccessRole;

// Setters
public AccessRole _nID(int i) { nID = i; return this; }
public AccessRole _sAccessRole(String s) { sAccessRole=s; return this; }
 
 // Getters
 public int nID() { return nID; }
 public String sAccessRole() { return sAccessRole; }
}
