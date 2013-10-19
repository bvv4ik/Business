/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.model;

/**
 *
 * @author Ser
 */
public class TheSubjectEmploy {
    
  
 
private int nID;
private int nID_TheSubject;
private String sTheSubjectEmploy;
private String sTheSubjectEmployFull; 
private String sTheSubjectEmployInfo;
private String sDTopen;
private String sDTclose;

  
// Setters
 public TheSubjectEmploy _nID(int i) { nID = i; return this; }
 public TheSubjectEmploy _nID_TheSubject(int i) { nID_TheSubject=i; return this; }
 public TheSubjectEmploy _sTheSubjectEmploy(String s) { sTheSubjectEmploy=s; return this;  }
 public TheSubjectEmploy _sTheSubjectEmployFull(String s) { sTheSubjectEmployFull=s; return this;  }
 public TheSubjectEmploy _sTheSubjectEmployInfo(String s) { sTheSubjectEmployInfo=s; return this;  }
 public TheSubjectEmploy _sDTopen(String s) { sDTopen=s; return this;  }
 public TheSubjectEmploy _sDTclose(String s) { sDTclose=s; return this;  }

 
 // Getters
 public int nID() { return nID; }
 public int nID_TheSubject() { return nID_TheSubject; }
 public String sTheSubjectEmploy() { return sTheSubjectEmploy; }
 public String sTheSubjectEmployFull() { return sTheSubjectEmployFull; }
 public String sTheSubjectEmployInfo() { return sTheSubjectEmployInfo; }
 public String sDTopen() { return sDTopen; }
 public String sDTclose() { return sDTclose; }

    
    
    
}
