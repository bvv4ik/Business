/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.entity;

/**
 *
 * @author Ser
 */
public class LinkCountry {
    
 
 
   private int nID_LinkProvider;
   private int nID_PlaceCountry;
   private String sLinkCountryPrefix;
   private String sLinkCountryPostfix;
   
   
// Setters
  public LinkCountry _nID_LinkProvider (int i) { nID_LinkProvider = i; return this; }
  public LinkCountry _nID_PlaceCountry (int i) { nID_PlaceCountry = i; return this; }
  public LinkCountry _sLinkCountryPrefix (String s) { sLinkCountryPrefix=s; return this;  }
  public LinkCountry _sLinkCountryPostfix (String s) { sLinkCountryPostfix=s; return this;  }
  
  
 // Getters
 public int nID_LinkProvider() { return nID_LinkProvider; }
 public int nID_PlaceCountry() { return nID_PlaceCountry; }
 public String sLinkCountryPrefix() { return sLinkCountryPrefix; }
  public String sLinkCountryPostfix() { return sLinkCountryPostfix; }
 
}
