/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.model;

/**
 *
 * @author Ser
 */
public class LinkProvider {
 
 
 
  private int nID;
   private int nID_LinkType;
   private int nID_TheSubject;
   private String sLinkProvider;
   
   
// Setters
  public LinkProvider _nID(int i) { nID = i; return this; }
  public LinkProvider _nID_LinkType(int i) { nID_LinkType = i; return this; }
  public LinkProvider _nID_TheSubject(int i) { nID_TheSubject = i; return this; }
  public LinkProvider _sLinkProvider(String s) { sLinkProvider=s; return this;  }
  
  
 // Getters
 public int nID() { return nID; }
 public int nID_LinkType() { return nID_LinkType; }
 public int nID_TheSubject() { return nID_TheSubject; }
 public String sLinkProvider() { return sLinkProvider; }
    
}
