/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.pkgdataBaseAdapter;

/**
 *
 * @author Conors PC
 */
import java.sql.*;

public class Example
{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/DB-here";

    static String USER;
    static String PASS;

    public static void main(String [] args)
    {
        
    }
    
    public Example(String user, String password)
    {
        USER = user;
        PASS = password;
        Connection conn = null;
        Statement stmt = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            System.out.print("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "STATEMENT";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {

            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch(SQLException se)
        {
            se.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(stmt!=null)
                        stmt.close();
            }
            catch(SQLException se2)
            {
                System.err.println(se2);
            }
            try
            {
                if(conn!=null)
                        conn.close();
            }
            catch(SQLException se)
            {
                    se.printStackTrace();
            }
        }
    }
}
