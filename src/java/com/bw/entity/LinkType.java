/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.entity;

/**
 *
 * @author Ser
 */
public class LinkType {
 
  
     
   private int nID;
   private String sLinkType;
   private String sLinkTypeInfo;
   
   
// Setters
  public LinkType _nID (int i) { nID = i; return this; }
  public LinkType _sLinkType (String s) { sLinkType=s; return this;  }
  public LinkType _sLinkTypeInfo (String s) { sLinkTypeInfo=s; return this;  }
  
  
 // Getters
  public int nID() { return nID; }
  public String sLinkType() { return sLinkType; }
  public String sLinkTypeInfo() { return sLinkTypeInfo; }
  
}
