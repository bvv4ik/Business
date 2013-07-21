
package com.bw.service;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import javax.servlet.annotation.WebServlet;


@WebServlet(name = "Login", urlPatterns = {"/SessionTracker"})


public class SessionTracker extends HttpServlet 
{
  public void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException 
  {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    HttpSession session = req.getSession(true);

    Integer count = (Integer) session.getAttribute("count");

    if (count == null) {
      count = new Integer(1);
    } else {
      count = new Integer(count.intValue() + 1);
    }

    session.setAttribute("count", count);
    out.println("<html><head><title>SessionSnoop</title></head>");
    out.println("<body><h1>Session Details</h1>");
    out.println("You've visited this page " + count + ((count.intValue()== 1) ? " time." : " times.") + "<br/>");
    out.println("<h3>Details of this session:</h3>");
    out.println("Session id: " + session.getId() + "<br/>");
    out.println("New session: " + session.isNew() + "<br/>");
    out.println("Timeout: " + session.getMaxInactiveInterval() + "<br/>");
    out.println("Creation time: " + new Date(session.getCreationTime()) + "<br/>");
    out.println("Last access time: " + new Date(session.getLastAccessedTime()) + "<br/>");
    out.println("</body></html>");
  }
}