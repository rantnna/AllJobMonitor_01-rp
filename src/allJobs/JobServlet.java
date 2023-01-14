package allJobs;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JobServlet extends HttpServlet {

	Connection con = null;
	ResultSet rs = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String jobName = request.getParameter("organizationUnit");
		System.out.println(request.getParameter("myDate"));
		String businessDate = request.getParameter("myDate");
		jobName = jobName + "%";
		
		SimpleDateFormat sourceDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		JobVO jobVO = new JobVO();

	    try {
	    	Date parse = sourceDateFormat.parse(businessDate);
	    	businessDate =  new SimpleDateFormat("dd-MMM-yy").format(parse);
	    	jobVO.setBuisnessDate(businessDate.toUpperCase());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		jobVO.setJobName(jobName);
		

		HashMap rs1 = getJobDetails(jobVO, response);
		
		request.getSession().setAttribute("jobLists", rs1.get("joblist"));
		request.getSession().setAttribute("cList", rs1.get("columnList"));

		/*
		 * RequestDispatcher rd =
		 * request.getRequestDispatcher("/showResult.jsp"); rd.forward(request,
		 * response);
		 */

		/*
		 * out.print("<table width=50% border=1>");
		 * out.print("<caption>Result:</caption>");
		 * 
		 * Printing column names try {
		 * 
		 * Printing result
		 * 
		 * if (rs1 != null) { ResultSetMetaData rsmd = rs1.getMetaData(); int
		 * total = rsmd.getColumnCount(); out.print("<tr>"); for (int i = 1; i
		 * <= total; i++) { out.print("<th>" + rsmd.getColumnName(i) + "</th>");
		 * } out.print("</tr>"); while (rs1.next()) { out.print("<tr><td>" +
		 * rs1.getString(1) + "</td><td>" + rs1.getString(2) + " </td><td>" +
		 * rs1.getString(3) + "</td><td>" + rs1.getString(4) + "</td><td>" +
		 * rs1.getString(5) + "</td><td>" + rs1.getString(6) + "</td></tr>"); }
		 * } } catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } finally { try { rs1.close(); if (con != null)
		 * { con.close(); } } catch (SQLException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } }
		 * 
		 * out.print("</table>");
		 */

		System.out.println(rs1.get("columnList")+"################"+ rs1.get("joblist"));

		request.setAttribute("jobdetails", rs1);
		request.getRequestDispatcher("jobDetails.jsp").forward(request, response);
		out.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	public HashMap getJobDetails(JobVO jobVO, HttpServletResponse response) {

		Connection con = null;
		ResultSet rs = null;

		ArrayList jobList = new ArrayList();
		HashMap jobMap = new HashMap();
		try {
			PrintWriter out = response.getWriter();

			Class.forName("oracle.jdbc.driver.OracleDriver");

			con = DriverManager
					.getConnection(
							"jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = 130.175.68.208)(PORT = 1521)))(CONNECT_DATA =(SERVICE_NAME = pcard)))",
							"FMGR_USER", "NewU53r");

			// Statement stmt=con.createStatement();

			//String sql = "select a.JOBNAME, a.REQUESTDATE as STARTDATE,to_char(a.PROCESSDATE,'DD-MON-YYYY') as BUSINESSDATE,b.STATUS,b.statusdate as ENDDATE,substr ( to_char(b.statusdate - a.REQUESTDATE, 'HH:MI:SS') , 12, 8) as ElapsedTime FROM JOBS a, JOB_STATUS b where a.STATUSID = b.STATUSID and a.JOBID in (select * from (select JOBID from JOBS where jobname like ? order by JOBID desc) where rownum <= 17 )order by a.REQUESTDATE";
			//String sql = "select a.JOBNAME, to_char(a.PROCESSDATE,'DD-MON-YYYY') as BUSINESSDATE, a.REQUESTDATE as STARTDATE,b.STATUS,b.statusdate as ENDDATE,substr ( to_char(b.statusdate - a.REQUESTDATE, 'HH:MI:SS') , 12, 8) as ElapsedTime FROM JOBS a, JOB_STATUS b where a.STATUSID = b.STATUSID and a.JOBID in (select * from (select JOBID from JOBS where jobname like ? order by JOBID desc) where rownum <= 17 )order by a.REQUESTDATE";
			String sql = null;
			if(jobVO.getBuisnessDate() != null){
				sql = "select a.JOBNAME, to_char(a.PROCESSDATE,'DD-MON-YYYY') as BUSINESSDATE, to_char(a.REQUESTDATE,'HH24:MI:SS') as STARTTIME, b.STATUS,to_char(b.statusdate,'HH24:MI:SS') as ENDTIME, substr ( to_char(b.statusdate - a.REQUESTDATE, 'HH:MI:SS') , 12, 8) as ElapsedTime FROM JOBS a, JOB_STATUS b where a.STATUSID = b.STATUSID and a.JOBID in (select * from (select JOBID from JOBS where jobname like ? and a.PROCESSDATE like ? order by JOBID desc) )order by a.REQUESTDATE";
			}else{
				sql = "select a.JOBNAME, to_char(a.PROCESSDATE,'DD-MON-YYYY') as BUSINESSDATE, to_char(a.REQUESTDATE,'HH24:MI:SS') as STARTTIME, b.STATUS,to_char(b.statusdate,'HH24:MI:SS') as ENDTIME, substr ( to_char(b.statusdate - a.REQUESTDATE, 'HH:MI:SS') , 12, 8) as ElapsedTime FROM JOBS a, JOB_STATUS b where a.STATUSID = b.STATUSID and a.JOBID in (select * from (select JOBID from JOBS where jobname like ? order by JOBID desc) where rownum <= 19 )order by a.REQUESTDATE";
			}
			PreparedStatement prepareStatement = con.prepareStatement(sql);

			prepareStatement.setString(1, jobVO.getJobName());
			
			if (jobVO.getBuisnessDate() != null) {
				prepareStatement.setString(2, jobVO.getBuisnessDate());
			}
			rs = prepareStatement.executeQuery();
			
			ArrayList rsmdList = new ArrayList();

			/*
			 * ResultSet rs=stmt.executeQuery("select a.JOBNAME, a.REQUESTDATE,
			 * to_char(a.PROCESSDATE,'DD-MON-YYYY') as BUSINESSDATE, b.STATUS,
			 * b.statusdate, substr ( to_char(b.statusdate - a.REQUESTDATE,
			 * 'HH:MI:SS') , 12, 8) as DiffTime FROM JOBS a, JOB_STATUS b where
			 * a.STATUSID = b.STATUSID and a.JOBID in (select * from (select
			 * JOBID from JOBS where jobname like 'MO%'order by JOBID desc)
			 * where rownum <= 10 ) order by a.REQUESTDATE ");
			 *//*while (rs.next()) {
				 
				 arrayList.add(rs.getString(1));
				System.out.println(rs.getString(1) + "\t" + rs.getString(2)
						+ "  " + rs.getString(3) + "\t" + rs.getString(4)
						+ "\t" + rs.getString(5) + "\t" + rs.getString(6));
			}*/

			//out.print("<table width=50% border=1>");
			//out.print("<caption>Result:</caption>");

			/* Printing column names */

			/* Printing result */

			ResultSetMetaData rsmd = rs.getMetaData();
			int total = rsmd.getColumnCount();
			out.print("<tr>");
			for (int i = 1; i <= total; i++) {
			//	out.print("<th>" + rsmd.getColumnName(i) + "</th>");
				rsmdList.add(rsmd.getColumnName(i));
			}
			System.out.println(rsmdList);
			out.print("</tr>");
			/*while (rs.next()) {
				out.print("<tr><td>" + rs.getString(1) + "</td><td>"
						+ rs.getString(2) + " </td><td>" + rs.getString(3)
						+ "</td><td>" + rs.getString(4) + "</td><td>"
						+ rs.getString(5) + "</td><td>" + rs.getString(6)
						+ "</td></tr>");
			}*/
			int k=0;
			
			while (rs.next()) {
				
				ArrayList rowList = new ArrayList();
				for (int j = 1; j <= total; j++) {
					rowList.add(rs.getString(j));
					System.out.println(rowList);
				}
				jobList.add(k, rowList);
				System.out.println(jobList);
				k++;
			}
			jobMap.put("columnList", rsmdList);
			jobMap.put("joblist", jobList);
			// con.close();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			

			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jobMap;

	}

}
