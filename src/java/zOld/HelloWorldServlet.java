package students.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import students.logic.Group;
//import students.logic.ManagementSystem;

public class HelloWorldServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        PrintWriter pw = resp.getWriter();
        
        pw.println("<B>  База данных </B>");
        pw.println("<table border=1>");
        
            
        try {
        
  HttpSession session = req.getSession(true);    
  session.setAttribute("sLogin","text");
  
  String value = session.getAttribute("sLogin").toString();
  //String sLogin = session.getAttribute("sLogin");
  
  pw.println(value);
         /*   List<Group> l = ManagementSystem.getInstance().getGroups();
      
         //   pw.println(ManagementSystem.str);
            //==============
            pw.println("<tr>");
                pw.println("<td>" + "1" + "</td>");
                pw.println("<td>" + "2" + "</td>");
                pw.println("<td>" + "3" + "</td>");
                pw.println("<td>" + "4" + "</td>");
                pw.println("</tr>");
                
            for (Group gr : l) {
                pw.println("<tr>");
                pw.println("<td>" + gr.getGroupId() + "</td>");
                pw.println("<td>" + gr.getNameGroup() + "</td>");
                pw.println("<td>" + gr.getCurator() + "</td>");
                pw.println("<td>" + gr.getSpeciality() + "</td>");

                pw.println("</tr>");
      

                
            } */
        } catch (Exception e) {
            throw new ServletException(e);
        }
        pw.println("</table>");
        //------------------
        
            
        
        
        
        
        
        
    }
}

