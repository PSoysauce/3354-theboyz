package com.theboyz.tablesaw_db_test;
import tech.tablesaw.api.Table;
import java.sql.ResultSet;

/**
 *This class is used to manage anything related to user accounts on the database
 * @author chaseuphaus
 */
public class deprecateduserDB extends deprecateddbInterface
{
    
    private final String userTable;
    private final String dataTable;
    
    public deprecateduserDB()
    {
        super("ffadmin", "3354admin");
        this.userTable = "info.users";
        this.dataTable = "\"info\".\"accInfo\"";
    }//end constructor
    
    public void addUser(String email, String password) throws Exception
    {
        String query = "INSERT INTO " + this.userTable + " (email, password) VALUES ( '" + email + "', crypt('" + password + "', gen_salt('bf')))";
        this.execute(query);
    }//end addUser
    
    
    public userAccount authenticate(String email, String password) throws Exception
    {
        try
        {
            Table login = Table.read().db(this.pull_from_table(email, password));
            int id = (int) login.intColumn("id").asObjectArray()[0];
            return new userAccount(id);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new Exception("Invalid Credentials");
        }
    }//end authenticate
    
    /**
     * This function is part of the authentication process, returns a ResultSet containing one row if login was successful
     * @param email The email of the user trying to log in
     * @param password The password for the user trying to log in
     * @return ResultSet containing the single row for the user logging in
     */
    private ResultSet pull_from_table(String email, String password)
    {
        String query = "SELECT id FROM " + this.userTable;
        String where = " WHERE email = '" + email + "' AND password = crypt('" + password + "', password)";
        query += where;
        try
        {
           return this.execute_query(query);
        } catch(Exception e)
        {
            System.out.print(e.getMessage());
            return null;
        }//end catch
    }//end pull from table
    
      
    
    protected ResultSet pull_from_accounts(userAccount acc) throws java.sql.SQLException
    {
        String query = "SELECT * FROM " + this.dataTable + " WHERE id = '" + acc.getID() + "'";
        ResultSet accInfo = this.execute_query(query);
        return accInfo;             
    }
    
    /**
     * This account updates the values for scoredStats, statWeights, and playerIDS in the database with those in the object
     * @param acc The account for which data needs to be written to the database
     * @return returns false if the account is not logged in.
     * @throws java.sql.SQLException Exception is thrown when there is a communication error with the database
     */
    protected boolean update_league_rules(userAccount acc) throws java.sql.SQLException
    {
        boolean status = false;
        
        if (!acc.loggedIn())
            return false;
        
        String [] update = acc.prepInfo();
        
        String query = "UPDATE " + this.dataTable + " SET \"scoredStats\" = '" + update[0] + "', \"statWeights\" = '" + 
                update[1] + "', \"playerIDS\" = '" + update[2] + "'  WHERE id = " + acc.getID();
        
        this.execute(query);
        return true;
            
        
    }
}//end class userDB
