package com.theboyz.tablesaw_db_test;
import java.sql.*;
import tech.tablesaw.api.*;
import tech.tablesaw.io.*;

public class deprecateddbInterface
{
    private final String URL;
    private final String user;
    private final String password;
    private final Connection con;

    /**
     *Default constructor used to connect to the database with a read-only account
     */
    public deprecateddbInterface()
    {
        this.URL = "jdbc:postgresql://99.179.141.41/ffdfdub";
        this.user = "ffuser";
        this.password = "dfdub";
        this.con = connectToDB();
    }
    
    /**
     * This constructor is used to connect to the database from a different account (e.g. if one wanted to connect with an account that has elevated permissions)
     * @param user The username for the pSQL database
     * @param pass The corresponding password for pSQL database
     */
    protected deprecateddbInterface(String user, String pass)
    {
        this.user = user;
        this.password = pass;
        this.URL = "jdbc:postgresql://99.179.141.41/ffdfdub";
        this.con = connectToDB();
        
    }

    /**
     * This function is used to execute queries that return a table
     * @param query String that contains the SQL Query
     * @return ResultSet containing the data for what was queried
     * @throws java.sql.SQLException 
     */
    protected ResultSet execute_query(String query) throws java.sql.SQLException
    {
        return this.con.createStatement().executeQuery(query);
    }
    
    /**
     * This function is used to execute queries that have no return
     * @param query String that contains SQL Query
     * @throws java.sql.SQLException Thrown when there is an error in the query
     */
    protected void execute(String query) throws java.sql.SQLException
    {
        this.con.createStatement().execute(query);
    }

    /**
     * This function is used to query the statistics tables
     * @param tableName The name of table that is to be pulled
     * @return A result set containing the data in the requested table
     */
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
    
    /**
     * This function is used to establish a connection to the database
     * @return Connection to the database
     */
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