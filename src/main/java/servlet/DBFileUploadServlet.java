
package servlet;
 
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.annotation.Resource;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
 
@WebServlet("/uploadServlet")
//@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
@MultipartConfig(maxFileSize = 204800000)   // upload file's size up to 200MB
public class DBFileUploadServlet extends HttpServlet {
    
    @Resource(lookup = "java:/MySqlHPD")
    private javax.sql.DataSource ds;
     
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // gets values of text fields
        String jurisdiction = request.getParameter("jurisdiction");
        String county = request.getParameter("county");
        String doctype = request.getParameter("doctype");
        String planperiod = request.getParameter("planperiod");
        String datercv = request.getParameter("datercv");
        String trackingdt = request.getParameter("trackingdt");
        
        InputStream inputStream = null; // input stream of the upload file
         
        // obtains the upload file part in this multipart request
        Part filePart = request.getPart("uploadedFile");
        String fileName = filePart.getSubmittedFileName();

        if (filePart != null) {
            // prints out some information for debugging
            System.out.println(filePart.getName());
            System.out.println(filePart.getSubmittedFileName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
             
            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }
         
        Connection conn = null; // connection to the database
        String message = null;  // message will be sent back to client
         
        try {
            conn = ds.getConnection();
            // constructs SQL statement
            String sql = "INSERT INTO housing_elements (jurisdiction, county, document_type, planning_period, date_received, tracking_date, filename, fileblob) values (?, ?, ?,?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, jurisdiction);
            statement.setString(2, county);
            statement.setString(3, doctype);
            statement.setString(4, planperiod);
            statement.setString(5, datercv);
            statement.setString(6, trackingdt);
            statement.setString(7, fileName);
            
             
            if (inputStream != null) {
                // fetches input stream of the upload file for the blob column
                statement.setBlob(8, inputStream);
            }
 
            // sends the statement to the database server
            int row = statement.executeUpdate();
            if (row > 0) {
                message = "File Uploaded Successfully and saved into database";
            }
        } catch (SQLException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                // closes the database connection
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            // sets the message in request scope
            request.setAttribute("Message", message);
             
            // forwards to the message page
            getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
        }
    }
}
