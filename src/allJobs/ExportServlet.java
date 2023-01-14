package allJobs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NewServlet
 */
public class ExportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//System.out.println("#############"+request.getSession().getAttribute("jobLists"));
		
		ArrayList list= (ArrayList)request.getSession().getAttribute("jobLists");
		ArrayList columnList=(ArrayList)request.getSession().getAttribute("cList");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline; filename="
					+ "excel.xls");
			StringBuffer sb=new StringBuffer("");
			PrintWriter pw=response.getWriter();
			sb.append("<table border=1 width=100%>");
			sb.append("<tr >");
			for(int j=0;j<columnList.size();j++)
				sb.append("<th bgcolor=#FFA500>"+columnList.get(j) +"</th>");
			sb.append("</tr >");
			for(int i=0;i<list.size();i++){
				String totalString=list.get(i).toString();
				
				String s[]=totalString.split(",");
			if(i%2==0){
			sb.append("<tr >");
			
			for(int j=0;j<s.length;j++){
				if("FINISHED".equals(s[j].trim())){
					//System.out.println("SINSHED"+s[j]);
					sb.append("<td align=left bgcolor=#FFEFD5><font color=green>" + s[j] + "</font></td>");
				}else if("FAILED".equals(s[j].trim())){
						sb.append("<td align=left bgcolor=#FFEFD5><font color=red>" + s[j] + "</font></td>");
				}else
						sb.append("<td align=left bgcolor=#FFEFD5>" + s[j] + "</td>");
			//sb.append("<td align=left bgcolor=#FFEFD5>"+ s[j]+"</td>");
			}
			
			sb.append("</tr>");
			}else{
				sb.append("<tr >");
				
				for(int j=0;j<s.length;j++){
					if("FINISHED".equals(s[j].trim()))
						sb.append("<td align=left bgcolor=#E0FFFF><font color=green>" + s[j] + "</font></td>");
						else if("FAILED".equals(s[j].trim()))
							sb.append("<td align=left bgcolor=#E0FFFF><font color=red>" + s[j] + "</font></td>");
						else
							sb.append("<td align=left bgcolor=#E0FFFF>" + s[j] + "</td>");	
				//sb.append("<td align=left bgcolor=#E0FFFF>"+ s[j]+"</td>");
				}
				
				sb.append("</tr>");
			}
			}
			sb.append("</table>");
			pw.write(sb.toString());
		
		//doGet(request, response);
	}

}
