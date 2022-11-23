import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class AddPhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			// Apache Commons-Fileupload library classes
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload sfu = new ServletFileUpload(factory);

			if (!ServletFileUpload.isMultipartContent(request)) {
				System.out.println("sorry. No file uploaded");
				return;
			}

			// parse request
			List<FileItem> items = sfu.parseRequest(request);
			FileItem id = (FileItem) items.get(0);
			String photoid = id.getString();

			FileItem title = (FileItem) items.get(1);
			String phototitle = title.getString();

			// get uploaded file
			FileItem file = (FileItem) items.get(2);

			// Connect to Oracle
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "C##shriramwar",
					"Admin");

			if (con != null) {
				System.out.println("Connected ");
			} else {
				System.out.println("Not Connected");
			}
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("insert into photos values(?,?,?)");
			ps.setString(1, photoid);
			ps.setString(2, phototitle);
			// size must be converted to int otherwise it results in error
			ps.setBinaryStream(3, file.getInputStream(), (int) file.getSize());
			ps.executeUpdate();
			con.commit();
			con.close();
			out.println("Photo Added Successfully. <p> <a href='listphotos'>List Photos </a>");
		} catch (Exception ex) {
			out.println("Error --> " + ex.getMessage());
			out.println("<br>");
			out.println(ex.toString());
		}
	}
}