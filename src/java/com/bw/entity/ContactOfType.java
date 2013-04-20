/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.entity;

/**
 *
 * @author Ser
 */
public class ContactOfType {
   
    
   private int nID;
   private String sContactOfType;
   private String sContactOfTypeInfo;
  
// Setters
  public ContactOfType _nID(int i) { nID = i; return this; }
  public ContactOfType _sContactOfType(String s) { sContactOfType=s; return this;  }
  public ContactOfType _sContactOfTypeInfo(String s) { sContactOfTypeInfo=s; return this;  }
  
  
 // Getters
 public int nID() { return nID; }
 public String sContactOfType() { return sContactOfType; }
 public String sContactOfTypeInfo() { return sContactOfTypeInfo; }
    
}