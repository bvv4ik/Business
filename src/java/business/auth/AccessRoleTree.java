/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.auth;

/**
 *
 * @author Ser
 */
public class AccessRoleTree {
    
          
private int nID_Access;
private int  nID_AccessRole;

// Setters
public AccessRoleTree _nID_Access(int i) { nID_Access = i; return this; }
public AccessRoleTree _nID_AccessRole(int i) { nID_AccessRole=i; return this; }
 
 // Getters
 public int nID_Access() { return nID_Access; }
 public int nID_AccessRole() { return nID_AccessRole; }  
 
}
