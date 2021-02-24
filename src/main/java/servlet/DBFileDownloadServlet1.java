/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author santosh
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
 
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * A servlet that retrieves a file from MySQL database and lets the client
 * downloads the file.
 * @author www.codejava.net
 */
@WebServlet("/downloadFileServlet1")
public class DBFileDownloadServlet1 extends HttpServlet {
    
    @Resource(lookup = "java:/MySqlHPD")
    private javax.sql.DataSource ds;
 
    // size of byte buffer to send file
    private static final int BUFFER_SIZE = 4096;   
    /* 
    // database connection settings
    private String dbURL = "jdbc:mysql://rcadev.hcd.ca.gov:3306/hpd";
    private String dbUser = "santosh";
    private String dbPass = "Atrocious12";
    */
    @Override 
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // get upload id from URL's parameters
        //int uploadId = Integer.parseInt(request.getParameter("id"));
        String fname=request.getParameter("file_name");
         
        Connection conn = null; // connection to the database
         
        try {
            // connects to the database
            //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
            conn = ds.getConnection();
            
            // queries the database
            //String sql = "SELECT * FROM files_upload WHERE upload_id = ?";
            //String sql = "SELECT * FROM files_upload WHERE upload_id = ?";
            //String sql = "SELECT * FROM housing_elements_with_blobs WHERE filename = ?";
            String sql = "SELECT * FROM housing_elements WHERE filename = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            //statement.setInt(1, uploadId);
            statement.setString(1, fname);
 
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                // gets file name and file blob data
                String fileName = result.getString("filename");
                Blob blob = result.getBlob("fileblob");
                InputStream inputStream = blob.getBinaryStream();
                int fileLength = inputStream.available();
                 
                System.out.println("fileLength = " + fileLength);
 
                ServletContext context = getServletContext();
 
                // sets MIME type for the file download
                String mimeType = context.getMimeType(fileName);
                if (mimeType == null) {        
                    mimeType = "application/octet-stream";
                }              
                 
                // set content properties and header attributes for the response
                response.setContentType(mimeType);
                response.setContentLength(fileLength);
                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", fileName);
                response.setHeader(headerKey, headerValue);
 
                // writes the file to the client
                OutputStream outStream = response.getOutputStream();
                 
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;
                 
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
                 
                inputStream.close();
                outStream.close();             
            } else {
                // no file found
                //response.getWriter().print("File not found for the id: " + uploadId);  
                response.getWriter().print("File not found for the id: " + fname);  
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            response.getWriter().print("SQL Error: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            response.getWriter().print("IO Error: " + ex.getMessage());
        } finally {
            if (conn != null) {
                // closes the database connection
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }          
        }
    }
}