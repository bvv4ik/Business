package zOld;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet{

protected void processRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
try {
request.setCharacterEncoding("UTF-8");
response.setContentType("text/html;charset=UTF-8");
String sDO=request.getParameter("sDO"); //аналогично остальные параметры вытянуть
String sName="";
		/*
			//Тут код работы с базой (я описал все с фиксированными параметрами, а ты можеш поподвязывать их к входящим параметрам сервлета)
Connection oDC = myclass.DatabaseConnect.getConnectByName("MyBase");//вставь свой коннекшин, и не забывай в финале закрывать его.

            if(“add”.equals(sDO)){//добавить юзера в базу
                               	oDC.prepareStatement("INSERT INTO MyTable (nID,sName,sInfo) VALUES (1,’Vasya’,’durak’)").executeUpdate();
            }else if(“edit”.equals(sDO)){//отредактировать данные юзера в базе
oDC.prepareStatement("UPDATE MyTable SET sInfo=’sovsem durak’,sName=’Vasya Pupkin’ WHERE nID=1").executeUpdate();
            }else if(“delete”.equals(sDO)){//удалить юзера из базы
                    oDC.prepareStatement("DELETE FROM MyTable WHERE nID=1").executeUpdate();
            }else if(“select”.equals(sDO)){//выбрать данные юзера из базы
ResultSet oSet=oDC.prepareStatement("SELECT nID, sName, sInfo FROM MyTable WHERE nID=1").executeQuery();
if(oSet.next()){
sName=oSet.getString(2);
//можно и так: sName=oSet.getString(“sName”);
}
            }
		*/

//sRes="{\"sReturn\":\”Ok\",\"sDO\":\""+sDO+"\"}"
}catch(Exception ex){
ex.printStackTrace();
}
//String sErr=_.getMessage();System.err.println("ERROR: "+sErr+"_"+sRes);//это вывод в лог-файл
//sRes="{\"sReturn\":\"Error\",\"sDO\":\""+sDO+"\"}" 
//throw new RuntimeException(_); раскомментировав эту строку можно прерывать выполнение класса при этой ошибке
finally{//этот код выполнится даже если произойдет ошибка (иногда это очень важно, чтоб, например - закрыть соединение
//response.getWriter().write(sRes);
}
}

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}

public String getServletInfo() {return "Short description";}
}
