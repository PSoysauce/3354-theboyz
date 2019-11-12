package com.theboyz.tablesaw_db_test;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import org.json.*;
import tech.tablesaw.api.Table;

public class userDB 
{
    private final String baseURL;
    private final String tableName;
    private final String configTable;
    
    public userDB()
    {
        this.baseURL = "http://99.179.141.41:25565/";
        this.tableName = "users";
        this.configTable = "accInfo";
    }//End constructor
    
    /**
     * This function authenticates the entered and returns the userAccount object upon successful login
     * @param email The email of the user trying to log in
     * @param password The password of the user trying to log in
     * @return userAccount containing the data of the user who just logged in
     */
    public userAccount authenticate(String email, String password)
    {
        String query = "?and=(email.eq." + email + ",password.eq." + crypt(password) + ")";
        Table tableOut = null;
        userAccount session = null;
        try
        {
            //Open URL and read data into StringBuffer
            Table res = this.execQuery(this.baseURL + this.tableName + query);            
            int id = res.intColumn("id").get(0);
            session = new userAccount(id);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return session;
    }//End authenticate
    
    /**
     * This function is used to pull the table containing scoredStats, statWeights, and playerIDS
     * @param usr The account for which to the information for
     * @return A table containing the accounts fantasy data
     */
    public Table pullFromAccounts(userAccount usr)
    {
        if (!usr.loggedIn())
            return null;
        String query = this.baseURL + this.configTable + "?id=eq." + Integer.toString(usr.getID());
        Table items = null;
        try
        {
            items = this.execQuery(query);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return items;
    }//End pullFromAccounts
    
    /**
     * This function is used to add a new user to the database
     * @param email The email of the user creating an account
     * @param password The pass of the user creating an account
     * @return boolean based on the upload status
     */
    public boolean addUser(String email, String password)
    {
        JSONObject newUser = new JSONObject();
        newUser.put("email", email);
        newUser.put("password", this.crypt(password));
        String query = this.baseURL + this.tableName + "?columns=email,password";
        return this.writeToDB(query, newUser, "POST");
    }//End addUser

    public void updateUserConfig(userAccount usr)
    {
        if (!usr.loggedIn())
            return;
        String query = this.baseURL + this.configTable + "?id=eq." + Integer.toString(usr.getID());
        this.writeToDB(query, usr.genAccJson(), "DELETE");
        this.writeToDB(query, usr.genAccJson(), "POST");
        usr.reload();
    }//End updateUserConfig
    
    /**
     * This function takes in the url, json data, and mode of HTTP request to generate a call to the database API
     * 
     * @param query The url for to tell the API which table / set of data to interact with
     * @param data The data to construct the api content with
     * @param mode The method of HTTP request
     * 
     * @return Boolean based on the success of the operation
     */
    private boolean writeToDB(String query, JSONObject data, String mode)
    {
        boolean status = false;
        try
        {
            
            URL conStr = new URL(query);
            HttpURLConnection con = (HttpURLConnection) conStr.openConnection();
            con.setDoOutput(true);
            con.setInstanceFollowRedirects(false);
            con.setRequestMethod(mode);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setUseCaches(false);
            if (!mode.equals("DELETE"))
            {
                String postData = this.makeData(data);
                con.getOutputStream().write(postData.getBytes("UTF-8"));
                con.getOutputStream().close();            
            }
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while ((line = reader.readLine()) != null)
                System.out.println(line);

                        status = true;
            
        }//End try
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            status = false;
        }//End catch
        finally
        {
            return status;
        }//End finally
    }//End writeToDB
    
    /**
     * Encodes json --> URL
     * 
     * @param in JSONObject to encode
     * @return String representation of the JSON as a URL
     * @throws Exception 
     */
    private String makeData(JSONObject in) throws Exception
    {
        Map<String, Object> params = in.toMap();
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        return postData.toString();
    }//End makeData
    
    /**
     * 'Encrypts' a plain text password so that you cannot see passwords in plain text in the database
     * 
     * @param pw String of plain text to encrypt
     * @return String containing the encrypted password
     */
    private String crypt(String pw)
    {
        int seed = 13469823;
        return Integer.toHexString(pw.hashCode() * seed);
    }//End crypt
    
    /**
     * Returns a table based on the table requested in the query
     * 
     * @param query URL query to the API
     * @return TABLE object from tablesaw containing the data from the query
     * @throws Exception 
     */
    private Table execQuery(String query) throws Exception
    {
        URL conStr = new URL(query);
        HttpURLConnection con = (HttpURLConnection) conStr.openConnection();  
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
            content.append(inputLine + "\n");
        in.close();
        con.disconnect();

        //Get ID from users table
        return Table.read().string(content.toString(), "json");
    }//End execQuery
}
