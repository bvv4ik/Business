/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.entity;

/**
 *
 * @author Ser
 */
public class LinkPlace {

    
   private int nID_LinkProvider;
   private int nID_PlacePolis;
   private String sLinkPlacePrefix;
   private String sLinkPlacePostfix;
   
   
// Setters
  public LinkPlace _nID_LinkProvider (int i) { nID_LinkProvider = i; return this; }
  public LinkPlace _nID_PlacePolis (int i) { nID_PlacePolis = i; return this; }
  public LinkPlace _sLinkPlacePrefix (String s) { sLinkPlacePrefix=s; return this;  }
  public LinkPlace _sLinkPlacePostfix (String s) { sLinkPlacePostfix=s; return this;  }
  
  
 // Getters
 public int nID_LinkProvider() { return nID_LinkProvider; }
 public int nID_PlacePolis() { return nID_PlacePolis; }
 public String sLinkPlacePrefix() { return sLinkPlacePrefix; }
  public String sLinkPlacePostfix() { return sLinkPlacePostfix; }
 
 
}
