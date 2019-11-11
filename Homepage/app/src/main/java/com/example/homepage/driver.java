package com.example.homepage;
import tech.tablesaw.api.Table;

public class driver 
{
    public static void main(String args[]) throws java.sql.SQLException
    {
        userDB dbHelper = new userDB();
        dbInterface statDB = new dbInterface();
        
        
//        boolean addStatus = dbHelper.addUser("nflfan101@email.com", "cowboyssuck666");
        boolean loginStatus = dbHelper.authenticate("nflfan101@email.com", "cowboyssuck666");
        
        
        if (loginStatus)
        {
           Table stats_2019 = Table.read().db(statDB.pull_from_table("player_stats_2019"));
           System.out.println(stats_2019.first(10));
        }
        else
            System.out.println("Login Unsuccessful");
        
    }
    
    
    //Apply Scoring function still WIP...
    public static void apply_scoring_and_print() throws java.sql.SQLException
    {
        dbInterface dbHelper = new dbInterface();
        fftools statInterface = new fftools();
        Table stats = Table.read().db(dbHelper.pull_from_table("player_stats_2019"));
        stats = statInterface.deriveStats(stats);
        statInterface.applyScoring(stats);
        
        //Print out the first 5 lines of the table
        System.out.println(stats.first(5));
    }
    
}
