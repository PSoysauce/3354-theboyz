package com.example.homepage;
import java.sql.*;
import tech.tablesaw.api.*;
import tech.tablesaw.io.*;

public class dbInterface
{
    private final String URL;
    private final String user;
    private final String password;
    private Connection con;

    public dbInterface()
    {
        this.URL = "jdbc:postgresql://99.179.141.41/ffdfdub?sslfactory=org.postgresql.ssl.NonValidatingFactory&ssl=true";
        this.user = "ffuser";
        this.password = "dfdub";
        this.con = connectToDB();
    }

    protected dbInterface(String user, String pass)
    {
        this.user = user;
        this.password = pass;
        this.URL = "jdbc:postgresql://99.179.141.41/ffdfdub?sslfactory=org.postgresql.ssl.NonValidatingFactory&ssl=true";
        this.con = connectToDB();

    }

    protected ResultSet execute_query(String query) throws SQLException
    {
        return this.con.createStatement().executeQuery(query);
    }

    public ResultSet pull_from_table(String tableName)
    {
        String query = "SELECT * FROM public." + tableName;
        try
        {
           return this.execute_query(query);
        } catch(Exception e)
        {
            System.out.print(e.getMessage());
            return null;
        }
    }

    public void print_table(ResultSet rs) throws SQLException
    {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next())
        {
            for (int i = 1; i <= columnsNumber; i++)
            {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }
        return;
    }

    private Connection connectToDB()
    {
        Connection con;
        try
        {
           con = DriverManager.getConnection(this.URL, this.user, this.password);
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }

        return con;
    }   
}