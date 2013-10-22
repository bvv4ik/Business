/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.model;

/**
 *
 * @author Ser
 */
public class ContactOf {

     private int nID_Contact;
     private int nID_TheSubject;
     private int nID_ContactOfType;

// Setters
     public ContactOf _nID_Contact(int i) {
          nID_Contact = i;
          return this;
     }

     public ContactOf _nID_TheSubject(int i) {
          nID_TheSubject = i;
          return this;
     }

     public ContactOf _nID_ContactOfType(int i) {
          nID_ContactOfType = i;
          return this;
     }

     // Getters
     public int nID_Contact() {
          return nID_Contact;
     }

     public int nID_TheSubject() {
          return nID_TheSubject;
     }

     public int nID_ContactOfType() {
          return nID_ContactOfType;
     }
}
