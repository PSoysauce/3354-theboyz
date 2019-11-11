package com.example.homepage;
import tech.tablesaw.api.Table;
import java.sql.ResultSet;

/**
 *
 * @author chaseuphaus
 */
public class userDB extends dbInterface
{
    private final String tableName;
    
    public userDB()
    {
        super("ffadmin", "3354admin");
        this.tableName = "info.users";
    }//end constructor
    
    public boolean addUser(String email, String password)
    {
        String query = "INSERT INTO " + this.tableName + " (email, password) VALUES ( '" + email + "', crypt('" + password + "', gen_salt('bf')))";
        try
        {
           //This should always throw an error as inserting does not return anything.
           super.execute_query(query);
           return true;
        } catch(Exception e)
        {
           if (e.getMessage().equals("No results were returned by the query."))
               return true;
           else
           {
               System.out.println(e.getMessage());
               return false;
           }//end else
        }//end catch
    }//end addUser
    
    public boolean authenticate(String email, String password)
    {
        try
        {
            Table login = Table.read().db(this.pull_from_table(email, password));
            return true;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }//end authenticate
    
    
    private ResultSet pull_from_table(String email, String password)
    {
        String query = "SELECT id FROM " + this.tableName;
        String where = " WHERE email = '" + email + "' AND password = crypt('" + password + "', password)";
        query += where;
        try
        {
           return super.execute_query(query);
        } catch(Exception e)
        {
            System.out.print(e.getMessage());
            return null;
        }//end catch
    }//end pull from table
    
    public ResultSet pull_emails()
    {
        String query = "SELECT email FROM " + this.tableName;
        try
        {
           return super.execute_query(query);
        } catch(Exception e)
        {
            System.out.print(e.getMessage());
            return null;
        }//end catch
    }//end pull_emails()
}//end class userDB
