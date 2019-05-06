import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.Date;

public class Main extends HttpServlet {

    private PrintWriter out;

    public void init() {
        ServletConfig conf = getServletConfig();
    }

    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        out.println("\nDate: " + new Date());

        super.service(req, resp);
        out.close();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse  resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String arg1=req.getParameter("empno");
        String arg2=req.getParameter("ename");
        String arg3=req.getParameter("job");
        String arg4=req.getParameter("mgr");
        String arg5=req.getParameter("salOD");
        String arg6=req.getParameter("salDO");
        String arg7=req.getParameter("deptno");
        String arg8=req.getParameter("dname");
        String arg9=req.getParameter("loc");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@db-oracle:1521:baza"
                    , "s16964", "oracle12");
            Statement st = con.createStatement();

            String sql="Select * FROM emp , dept WHERE emp.deptno = dept.deptno ";

            try { sql += getwhere(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9); }
            catch (Exception e) {}
            ResultSet rs = st.executeQuery(sql);

            resp.setContentType("text/html;charset=UTF-8");
            htmlstart(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>"+rs.getString(1)+"</td> ");
                out.println("<td>"+rs.getString(2)+"</td> ");
                out.println("<td>"+rs.getString(3)+"</td> ");
                out.println("<td>"+rs.getString(4)+"</td> ");
                out.println("<td>"+rs.getString(6)+"</td> ");
                out.println("<td>"+rs.getString(8)+"</td> ");
                out.println("<td>"+rs.getString(10)+"</td> ");
                out.println("<td>"+rs.getString(11)+"</td> ");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
            con.close();
            st.close();
            rs.close();
        } catch (ClassNotFoundException | SQLException ex) {
            out.println(ex);
        }



    }

    private String getwhere(String arg1,String arg2,String arg3,String arg4,String arg5,String arg6,String arg7,String arg8,String arg9){
        String sql="";
        if(!arg1.equals("")){
            sql+=" AND empno LIKE '"+arg1+"%'";
        }
        if(!arg2.equals("")){
            sql+=" AND ename LIKE '"+arg2+"%'";
        }
        if(!arg3.equals("")){
            sql+=" AND job LIKE '"+arg3+"%'";
        }
        if(!arg4.equals("")){
            sql+=" AND mgr LIKE '"+arg4+"%'";
        }
        if(!arg5.equals("")){
            sql+=" AND sal >"+arg5;
        }
        if(!arg6.equals("")){
            sql+=" AND sal <"+arg6;
        }
        if(!arg7.equals("")){
            sql+=" AND deptno LIKE '"+arg7+"%'";
        }
        if(!arg8.equals("")){
            sql+=" AND dname LIKE '"+arg8+"%'";
        }
        if(!arg9.equals("")){
            sql+=" AND loc LIKE '"+arg9+"%'";
        }
        return sql;
    }

    private void htmlstart(String arg1,String arg2,String arg3,String arg4,String arg5,String arg6,String arg7,String arg8,String arg9){
        if(arg2 == null){
            arg2="";
        }
        if(arg3 == null){
            arg3="";
        }
        if(arg8 == null){
            arg8="";
        }
        if(arg9 == null){
            arg9="";
        }

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<style>\n" +
                "table, th, td {\n" +
                "  border: 1px solid black;\n" +
                "  border-collapse: collapse;\n" +
                "}\n" +
                "th{\n" +
                "  padding: 10px;\n" +
                "}\n" +
                "input {\n" +
                "width: 100%; \n" +
                "}\n"+
                "</style>");
        out.println("<title>TPO4</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>EMP AND DEPT TABLE </h1>");

        out.println("<form action=\"/TPO4\">\n");
        out.println("<table style=\"width:100%\">");
        out.println("<tr>");
        out.println("<th>EMPNO</th> ");
        out.println("<th>ENAME</th> ");
        out.println("<th>JOB</th> ");
        out.println("<th>MGR</th> ");
        out.println("<th>SAL OD</th> ");
        out.println("<th>SAL DO</th> ");
        out.println("<th>DEPTNO</th> ");
        out.println("<th>DNAME</th> ");
        out.println("<th>LOC</th> ");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>");
        out.println("<input type=\"number\" name=\"empno\" value=\""+arg1+"\">");
        out.println("</td>");

        out.println("<td>");
        out.println("<input type=\"text\" name=\"ename\" value=\""+arg2+"\">");
        out.println("</td>");

        out.println("<td>");
        out.println("<input type=\"text\" name=\"job\" value=\""+arg3+"\">");
        out.println("</th>");

        out.println("<td>");
        out.println("<input type=\"number\" name=\"mgr\" value=\""+arg4+"\">");
        out.println("</td>");

        out.println("<td>");
        out.println("<input type=\"number\" name=\"salOD\" value=\""+arg5+"\">");
        out.println("</td>");

        out.println("<td>");
        out.println("<input type=\"number\" name=\"salDO\" value=\""+arg6+"\">");
        out.println("</td>");

        out.println("<td>");
        out.println("<input type=\"number\" name=\"deptno\" value=\""+arg7+"\">");
        out.println("</td>");

        out.println("<td>");
        out.println("<input type=\"text\" name=\"dname\" value=\""+arg8+"\">");
        out.println("</td>");

        out.println("<td>");
        out.println("<input type=\"text\" name=\"loc\" value=\""+arg9+"\">");
        out.println("</td>");
        out.println("</tr>");

        out.println("<tr>");
        out.println("<td colspan=\"9\">");
        out.println("<input type=\"submit\" value=\"Submit form\">");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</form>");

        out.println("<br/>");

        out.println("<table style=\"width:100%\">");
        out.println("<tr>");
        out.println("<th>EMPNO</th> ");
        out.println("<th>ENAME</th> ");
        out.println("<th>JOB</th> ");
        out.println("<th>MGR</th> ");
        out.println("<th>SAL</th> ");
        out.println("<th>DEPTNO</th> ");
        out.println("<th>DNAME</th> ");
        out.println("<th>LOC</th> ");
        out.println("</tr>");
    }
}
