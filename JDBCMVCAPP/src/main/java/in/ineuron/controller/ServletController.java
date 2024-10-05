package in.ineuron.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.ineuron.dto.Student;
import in.ineuron.factory.StudentServiceFactory;
import in.ineuron.service.IStudentService;

@WebServlet("/controller/*")
public class ServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestUri = request.getRequestURI();
		System.out.println(requestUri);
		
		IStudentService studentService = StudentServiceFactory.getStudentService();
		
		RequestDispatcher rd=null;
		
		//request for starting the application...
		if(requestUri.endsWith("layout")) {
			rd = request.getRequestDispatcher("../layout.html");
			rd.forward(request, response);
		}
		
		//request for adding student...
		if(requestUri.endsWith("addform")) {
			Student student = new Student();
			student.setSname(request.getParameter("sname"));
			student.setSage(Integer.parseInt(request.getParameter("sage")));
			student.setSaddr(request.getParameter("saddr"));
			
			String status = studentService.save(student);
			
			if(status.equalsIgnoreCase("success")) {
				rd = request.getRequestDispatcher("../success.html");
				rd.forward(request, response);
			}
			else {
				rd = request.getRequestDispatcher("../failure.html");
				rd.forward(request, response);
			}	
		}
		
		
		//request for getting student....
		if(requestUri.endsWith("searchform")) {
			Integer sid = Integer.parseInt(request.getParameter("sid"));
			Student student = studentService.findById(sid);
			
			if(student!=null) {
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				
				out.println("<html><head><title>StudentData</title></head>");
				out.println("<body bgcolor='lightblue'><br/><br/>");
				out.println("<h1 align='center'>Student Data</h1><br/>");
				out.println("<table align='center' border='1'>");
				out.println("<tr><th>Name:: </th><td>"+student.getSname()+"</td></tr>");
				out.println("<tr><th>Age:: </th><td>"+student.getSage()+"</td></tr>");
				out.println("<tr><th>Address:: </th><td>"+student.getSaddr()+"</td></tr>");
				out.println("</table></body></html>");
				out.close();
				
			}
			else {
				rd = request.getRequestDispatcher("../notfound.html");
				rd.forward(request, response);
			}
		}
		
		//request for updating student data
		if(requestUri.endsWith("updateform")) {
			Integer sid = Integer.parseInt(request.getParameter("sid"));
			Student student = studentService.findById(sid);
			if(student!=null) {
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				
				out.println("<html><head><title>Edit Form </title></head>");
				out.println("<body bgcolor='lightblue' align='center'>");
				out.println("<br/><br/><br/>");
				out.println("<form action='./edit' method='post'>");
				out.println("<table align='center'>");
				out.println("<tr><th>SId::</th><td>"+student.getSid()+"</td><tr>");
				out.println("<input type='hidden' name='sid' value='"+student.getSid()+"'>");
				out.println("<tr><th>SName::</th><td><input type='text' name='sname' value='"+student.getSname()+"'></td></tr>");
				out.println("<tr><th>SAge::</th><td><input type='text' name='sage' value='"+student.getSage()+"'></td></tr>");
				out.println("<tr><th>SAddr::</th><td><input type='text' name='saddr' value='"+student.getSaddr()+"'></td></tr>");
				out.println("<tr><th></th><td><input type='submit' value='Update'></td></tr>");
				out.println("</table>");
				out.println("</form>");
				out.println("</body>");
				out.println("</html>");
			}
			else {
				rd=request.getRequestDispatcher("../notfound.html");
				rd.forward(request, response);
			}
		}
		
		//request for update the details provided in the edited form
		if(requestUri.endsWith("edit")) {
			Student student = new Student();
			student.setSid(Integer.parseInt(request.getParameter("sid")));
			student.setSname(request.getParameter("sname"));
			student.setSage(Integer.parseInt(request.getParameter("sage")));
			student.setSaddr(request.getParameter("saddr"));
			
			String status = studentService.updateById(student);
			if(status.equalsIgnoreCase("success")) {
				rd=request.getRequestDispatcher("../success.html");
				rd.forward(request, response);
			}
			else {
				rd=request.getRequestDispatcher("../failure.html");
				rd.forward(request, response);
			}
		}
		
		//request for deleting student data
		if(requestUri.endsWith("deleteform")) {
			Integer sid = Integer.parseInt(request.getParameter("sid"));
			String status = studentService.deleteById(sid);
			
			if(status.equalsIgnoreCase("success")) {
				rd=request.getRequestDispatcher("../success.html");
				rd.forward(request, response);
			}
			else if(status.equalsIgnoreCase("failure")) {
				rd=request.getRequestDispatcher("../failure.html");
				rd.forward(request, response);
			}
			else {
				rd=request.getRequestDispatcher("../notfound.html");
				rd.forward(request, response);
			}
		}
		
	}
}
