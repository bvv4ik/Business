
package business.auth;

//import com.sun.org.apache.xpath.internal.operations.Equals;

import javax.naming.*;
import javax.naming.directory.*;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
	import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
	import javax.naming.directory.InitialDirContext;
	import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
	import javax.naming.directory.SearchResult;

//import ru.auditory.ldap.LdapConnection;
//import com.novell.ldap.*;

/**
 *   Тестовая версия, не используется.
 * @author Sergey
 */
public class AccessLDAP {
    
    public String sReturnPasswordUser = "";
    //public Boolean bFindLogin = false;
    
    
    
    
    public DirContext getConnectLdap (String name) throws Exception {
      
       // try {
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);      
	// Set up environment for creating initial context
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL, "ldap://192.168.3.2:389");
	env.put(Context.SECURITY_AUTHENTICATION, "simple");
	env.put(Context.SECURITY_PRINCIPAL, "cn=Manager4, dc=my-domain4, dc=com");
	env.put(Context.SECURITY_CREDENTIALS, "secret");
        DirContext ctx = new InitialDirContext(env);
      
        //DirContext ctx = getConnectLdap("Test");  
        return ctx; 
                           
       } 
            
        //ctx.close();
    
    
    public void closeConnectLdap(String sName, DirContext ctx) {
           try{
            if(ctx!=null){ctx.close();}
        }catch(Exception _){
            System.err.println("ERROR[closeConnectLdap](sName="+sName+"):"+_.getMessage());
        }
    }
    
    
     
     public boolean bWrite (String sUserLogin, String sUserPassword) throws Exception{
         
         
         DirContext ctx = getConnectLdap("Test");  
                      
         try {
             
             if (ctx == null)
             return false; //"ошибка подключения к Лдап";
             
	    // Create initial context
            Attributes matchAttrs = new BasicAttributes(true);
	    //matchAttrs.put(new BasicAttribute("objectclass", "organizationalRole"));
            matchAttrs.put(new BasicAttribute("objectclass", "organizationalRole"));
            matchAttrs.put(new BasicAttribute("description", sUserPassword));
          //  matchAttrs.put(new BasicAttribute("userPassword", sUserPassword));
            String name = "cn="+sUserLogin+", dc=my-domain4, dc=com";
	   // InitialDirContext iniDirContext = (InitialDirContext) ctx;
	    ctx.bind(name, ctx, matchAttrs);
            
            
            return true;//""; //Записано в Лдап!
            
            } catch (Exception _) {
                //_.printStackTrace();
                    String sErr=_.getMessage();
                    System.err.println("ERROR!: "+sErr+"_"+"metod ---- bWrite");
                    
                    return false ;//"Ошибка приложения"+"---- WriteLoginAndPasswordInLdap";
            }
         finally{
            ctx.close();    
                }
     }
    
     
     
     
     
     public String sGetPassword (String sLogin) throws Exception{
          String Password = "";
                  
          if ((getConnectLdap("Test")) == null)
          {
          return ""; // ошибка подключения к Лдап
          }
          
          
          DirContext ctx = getConnectLdap("Test");  
            try
          {
              
             
        //  Attributes answer = 
	//  ctx.getAttributes("ou="+sUserLogin+", dc=my-domain4, dc=com");
        //Password = answer.get("userPassword").toString(); //
        Attributes answer = ctx.getAttributes("cn="+sLogin+", dc=my-domain4, dc=com");
        //Attribute pwd = answer.get("description");
        //System.out.println("=> userPassword : " + new String((byte[])pwd.get()));
        //Password = ("=> userPassword : " + new String((byte[])pwd.get()));
        Password =  answer.get("description").toString();
        
        ctx.close(); 
        
        return Password ;      
         
       } catch (Exception _) {
                   String sErr=_.getMessage();
                    System.err.println("ERROR!: "+sErr+"_"+"metod ---- sGetPassword");
                  
                   // ctx.close(); - не требуется т.к соединение и небыло создано 
                    return ""; // Неверный логин ---- или ошибка подключения;
            }
            
     
     }
     
     
     
     
     	    public boolean isLoginExists(String sLogin) {
                    //throws Exception {
	      // Boolean bResult = false;
              //  if (sLogin == "") {sLogin = "123";}
                try {
                    DirContext ctx = getConnectLdap("Test");
     	            SearchControls sc = new SearchControls();
	            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        
                        NamingEnumeration<?> namingEnum = ctx.search("cn="+sLogin+",dc=my-domain4,dc=com", "(objectclass=organizationalRole)", sc); //getSimpleSearchControls()
                        while (namingEnum.hasMore ()) {
                            SearchResult result = (SearchResult) namingEnum.next ();    
                          //  Attributes attrs = result.getAttributes ();
                                        //if (!attrs.equals(null))
                                        //   bResult = true;
                       //  System.out.println(attrs.get("cn")); // вывести результат
                        } 
        
         namingEnum.close();
         ctx.close();
         
         return true;
     } 
       catch (Exception e) {
       // e.printStackTrace();
           
    }
                //finally {return false;}
                
       return false;
}

           
   //=====================================================
     /*       
      public static void verifyPass() {
           try {
            LdapConnection ldap = new LdapConnection(ldapURL, dn, passwd, base);
            if (ldap.ckPasswd("Логин пользователя", "fего пароль")) {
                //Авторизация успешна
            } else {
                //Логин/пароль не верен
            }
        } catch (Exception e) {
            //Не удалось подключиться к лдапу под логином ресурса
        }
}
*/
     
     
    // old 
public void getPasswordUser(String sUserLogin) throws Exception{
 
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);    
	// Set up environment for creating initial context
        env.put(Context.INITIAL_CONTEXT_FACTORY, 
	    "com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL, "ldap://192.168.3.2:389");
	env.put(Context.SECURITY_AUTHENTICATION, "simple");
	env.put(Context.SECURITY_PRINCIPAL, "cn=Manager4, dc=my-domain4, dc=com");
	env.put(Context.SECURITY_CREDENTIALS, "secret");
 
 
       try {
	    // Create initial context
            DirContext ctx = new InitialDirContext(env);
	    // do something useful with ctx
          //======================================
              // Specify the ids of the attributes to return
	    //String[] attrIDs = {"sn", "telephonenumber", "golfhandicap", "mail"};
            String[] attrIDs = {"sn", "userPassword", "golfhandicap", "mail"};
	    // Get the attributes requested
	    Attributes answer = 
		//ctx.getAttributes("cn=Barbara J Jensen, dc=my-domain4, dc=com", attrIDs);
            ctx.getAttributes("ou="+sUserLogin+", dc=my-domain4, dc=com");
                    //("ou="+sUserLogin+", dc=my-domain4, dc=com");
            	//ctx.getAttributes("ou="+sUserLogin+", dc=my-domain4, dc=com", attrIDs);
            //neworganizationalUnit2
            sReturnPasswordUser = answer.get("userPassword").toString(); //answer.toString(); 
        
            
            //answer.ge
          //ctx.getAttributes("ou=userPassword");
          
         //System.out.println("=> userPassword : " + new String((byte[])answer.get()));
           //System.out.println("=> userPassword : " + new String((byte[]). answer.get("userPassword")));
            //==========================
      
            //DirContext dirContext = null;
      
            
        Attributes attributes = ctx.getAttributes("ou="+sUserLogin+", dc=my-domain4, dc=com", attrIDs);
        
      

        //Password string depends on LDAP password policy
        Attribute pwd = attributes.get("userPassword");
        System.out.println("=> userPassword : " + new String((byte[])pwd.get()));
        System.out.println();   
            
            
            //= answer.get("userPassword");
            ctx.close();
            } catch (NamingException e) {
                e.printStackTrace();
            }        
      
}
     //old
public void setPasswordUser(/*String sUserPassword*/){

      Hashtable<String, Object> env = new Hashtable<String, Object>(11);    
	// Set up environment for creating initial context
        env.put(Context.INITIAL_CONTEXT_FACTORY, 
	    "com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL, "ldap://192.168.3.2:389");
	//env.put(Context.SECURITY_AUTHENTICATION, "simple");
	env.put(Context.SECURITY_PRINCIPAL, "cn=Manager4, dc=my-domain4, dc=com");
	env.put(Context.SECURITY_CREDENTIALS, "secret");

       try {
	    // Create initial context
            DirContext ctx = new InitialDirContext(env);
        
            Attributes matchAttrs = new BasicAttributes(true);
	    matchAttrs.put(new BasicAttribute("objectclass", "organizationalRole"));
            matchAttrs.put(new BasicAttribute("cn", "Ser5"));
      
            String name = "cn=Ser5, dc=my-domain4, dc=com";
	    InitialDirContext iniDirContext = (InitialDirContext) ctx;
	    iniDirContext.bind(name, ctx, matchAttrs);
            
            ctx.close();
            } catch (NamingException e) {
                e.printStackTrace();
            }

}
     
public void modfyPasswordUser(/*String sUserPassword*/){
    
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);    
	// Set up environment for creating initial context
        env.put(Context.INITIAL_CONTEXT_FACTORY, 
	    "com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL, "ldap://192.168.3.2:389");
	//env.put(Context.SECURITY_AUTHENTICATION, "simple");
	env.put(Context.SECURITY_PRINCIPAL, "cn=Manager4, dc=my-domain4, dc=com");
	env.put(Context.SECURITY_CREDENTIALS, "secret");

       try {
	    // Create initial context
            DirContext ctx = new InitialDirContext(env);

     String name = "ou=neworganizationalUnit2,dc=my-domain4,dc=com";
               // Save original attributes
           //Attributes orig = ctx.getAttributes(name);

            // Specify the changes to make
            ModificationItem[] mods = new ModificationItem[1];

            
            //mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
              //                             new BasicAttribute("cn", "organizationalRole"));
 
            //mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                                      //     new BasicAttribute("userPassword", "333"));
            //mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE,
            //                              new BasicAttribute("telephonenumber", "+1 555 555 5555"));
            mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE,
                                          new BasicAttribute("userPassword", "777"));
            
            
            ctx.modifyAttributes(name, mods);
            
            
            
            ctx.close();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
     
     //      public static void main(String[] args) {     }
}

    
