/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.model;

/**
 *
 * @author User
 */
public class Localize {
    public enum Text{
        My("Это мое")
        ,NotMy("Это (dsd) не мое")
        ;
        private String s = "";

        private Text(String s) {
            this.s = s;
        }
        public String s() {
            s=s.replaceAll("(dsd)", "Vasya");
            return s.trim();
        }
        @Override
        public String toString(){
            return s();
        };
    }
    
}
