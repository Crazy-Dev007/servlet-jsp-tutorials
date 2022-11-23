import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayPhotoServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8849177639000163485L;
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "C##shriramwar", "Admin");
            PreparedStatement ps = con.prepareStatement("select photo from photos where id = ?");
            String id = request.getParameter("id");
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Blob  b = rs.getBlob("photo");
            response.setContentType("image/jpeg");
            response.setContentLength( (int) b.length());
            InputStream is = b.getBinaryStream();
            OutputStream os = response.getOutputStream();
            byte buf[] = new byte[(int) b.length()];
            is.read(buf);
            os.write(buf);
            os.close();
        }
        catch(Exception ex) {
             System.out.println(ex.getMessage());
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