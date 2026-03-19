import java.io.IOException;//used to handle the input and output errors
import java.io.PrintWriter;//used to send the output to the browser
import java.sql.Connection;//used to connect to the database
import java.sql.DriverManager;//used to load the JDBC driver into the memory
import java.sql.PreparedStatement;//it is used to prevent sql injection
import java.sql.ResultSet;//it is used to stores the data return from the select query

import jakarta.servlet.http.HttpServlet;//it is a baseclass for creating http servlet
import jakarta.servlet.http.HttpServletRequest;//request to get data from the client
import jakarta.servlet.http.HttpServletResponse;//send the data to the client


public class BookServlet extends HttpServlet {

    // Handles GET request
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        // Set response type
        res.setContentType("text/html");//it tells the browser that the response is html
        PrintWriter out = res.getWriter();//it creates an output string to write the html to the browser

        // Get search keyword from HTML form
        String key = req.getParameter("key");//It reads such keyword send from the html form input named key

        try {
      Class.forName("org.postgresql.Driver");

Connection con = DriverManager.getConnection(
    "jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:5432/postgres?sslmode=require&connectTimeout=10",
    "postgres.vvbihjeejyxtrjwrgvrb",
    "SonaliSasmal@2026"
);


      String sql = "SELECT * FROM public.books WHERE title ILIKE ? OR author ILIKE ?";//it selects all the records from the books table

           PreparedStatement ps = con.prepareStatement(sql);//they are used for partial matching
                 ps.setString(1, "%" + key + "%");//they are used for partial matching
          ps.setString(2, "%" + key + "%");//they are used for partial matching

            // Execute query
            ResultSet rs = ps.executeQuery();

            // Display result in HTML table
            out.println("<h2>Book List</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>Title</th><th>Author</th><th>Price</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("title") + "</td>");
                out.println("<td>" + rs.getString("author") + "</td>");
                out.println("<td>" + rs.getInt("price") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            // Close connection
            con.close();

        } catch (Exception e) {
            out.println("<p>Error: " + e + "</p>");
        }
    }
}
