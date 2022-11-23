import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListPhotosServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "C##shriramwar",
					"Admin");
			PreparedStatement ps = con.prepareStatement("select * from photos");
			ResultSet rs = ps.executeQuery();
			out.println("<link rel=\"stylesheet\"\r\n"
					+ "	href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css\"\r\n"
					+ "	integrity=\"sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N\"\r\n"
					+ "	crossorigin=\"anonymous\">");
			
			out.println("<h1 class=\"m-3 bg-dark text-white \" >Photos</h1> <br> ");
			out.println("<div class=\"container d-flex \" >");
			while (rs.next()) {

				out.println("<div class=\"card\" style=\"width: 18rem;\">");
				out.println("<img  class=\"card-img-top\" alt=\"Error in Image\" src=displayphoto?id="
						+ rs.getString("id") + "></img>");
				out.println("<div class=\"card-body\">");
				out.println("<h5 class=\"card-title\">" + rs.getString("title") + "</h5>");
				out.println(
						"<p class=\"card-text\">Some quick example text to build on the card title and make up the bulk of the card's content.</p>");
				out.println("<a href=\"#\" class=\"btn btn-primary\">Go somewhere</a>");
				out.println("</div></div>");
			}
			out.println("<div>");
			con.close();
		} catch (Exception ex) {

		} finally {
			out.close();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
}