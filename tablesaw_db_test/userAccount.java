package com.theboyz.tablesaw_db_test;

import java.util.ArrayList;
import tech.tablesaw.api.Table;

/**
 *
 * @author chaseuphaus
 */
public class userAccount 
{
    private final int userID;
    private String name;
    private String[] playerIDS;
    private String[] scoredStats;
    private double[] statWeights;
    private boolean loggedIn;
    public fftools userStats;
    
    public userAccount()
    {
        userID = -1;
        name = "null";
        loggedIn = false;
        this.userStats = new fftools();
    }//end default constructor
    
    public userAccount(int id)
    {
        loggedIn = true;
        this.userID = id;
        this.getItems();
        this.userStats = new fftools(this);
    }
    
    public String[] getStats() { return this.scoredStats; }
    public double[] getWeights() { return this.statWeights; }
    
    private void getItems()
    {
        if (this.userID == -1)
            return;
        userDB helper = new userDB();
        try
        {
            Table items = Table.read().db(helper.pull_from_accounts(this.userID));
            parseInfo(items);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
    }
    
    private void parseInfo(Table df)
    {
        try
        {
            //Get data from columns
            String stats = df.stringColumn("scoredStats").get(0);
            String weights = df.stringColumn("statWeights").get(0);

            //Parse stats string and store in member variable
            this.scoredStats = this.parseStr(stats);

            //Parse weights into double array
            String [] temp = this.parseStr(weights);
            ArrayList<Double> holder = new ArrayList<>();
            for (String val: temp)
                holder.add(Double.valueOf(val));

            //Convery ArrayList to primitive double array
            this.statWeights = new double[holder.size()];
            for (int i = 0; i < holder.size(); i++)
                this.statWeights[i] = holder.get(i);
        } catch (Exception E)
        {
            System.out.println("Account not setup. Will use default scoring info...");
        }
    }
    
    private String[] parseStr(String in)
    {
        in = in.replaceAll("'", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "");
        return in.split(",");
    }
    
    
    //This will eventually return a val that is send to db class for updating team info...
    private void prepInfo()
    {
        //Set stats up to go into the db
        String stats = "[";
        for (int i = 0; i < this.scoredStats.length; i++)
        {
            stats += "'" + this.scoredStats[i] + "'";
            if (i != this.scoredStats.length-1)
                stats += ", ";
        }
        stats += "]";
        
        //Set up weights to go into db
        String weights = "[";
        for (int i = 0; i < this.statWeights.length; i++)
        {
            weights += Double.toString(this.statWeights[i]);
            if (i != this.statWeights.length-1)
                weights += ", ";
        }
        weights += "]";
        weights = weights.replaceAll(".0,", ",").replaceAll(".0\\]", "]");
        System.out.println(weights);
        
    }
    
    
}
