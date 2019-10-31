package com.theboyz.tablesaw_db_test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.LongColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.NumericColumn;

/**
 *
 * @author chaseuphaus
 */
public class fftools 
{
    private final String[] scoredStats;
    private final double[] statWeights;
    private final String[] index = {"playerID", "name", "Team", "game.id", "date", "Season"};
    
    public fftools()
    {
        //Example set of scored items
        String[] items = {"pass.comp", "passyds", "pass.tds", "pass.ints", "rush.att",
                          "rushyds", "rushtds", "recept", "recyds", "rec.tds", "st_ret_yds", 
                          "kickret.tds", "puntret.tds", "fumbslost", "two_pts", "xpa", "fgs"};
        
        //Example set of item weights
        double[] weights = {(double) .1, (double) 1/25, (double) 6, (double) -2, (double) .1,
                            (double) 1/10, (double) 6, (double) .4, (double) 1/10, (double) 6, 
                            (double) 1/20, (double) 6, (double) 6, (double) -2, (double) 2,
                            (double) 1, (double) 1};
        
        this.scoredStats = items;
        this.statWeights = weights;
    }
    
    public fftools(userAccount acc)
    {
        this.scoredStats = acc.getStats();
        this.statWeights = acc.getWeights();
    }
    
    public Table applyScoring(Table df) throws Exception
    {
        df = this.deriveStats(df);
        //Declare dict with weights for scored stats in league
        Table scored = Table.create();
        Table touches = Table.create();
        
        HashMap<String, Double> scoring = this.scoredDict();
        String current;
        for (int i = 0; i < scoring.keySet().size(); i++)
        {
            current = scoring.keySet().toArray()[i].toString();
            if (current.equals("two_pts") || current.equals("st_ret_yds"))
                scored.addColumns(df.doubleColumn(current).multiply(scoring.get(current)).setName(current));
            else
                scored.addColumns(df.longColumn(current).multiply(scoring.get(current)).setName(current));
        }    
    
        
        touches.addColumns(df.longColumn("rush.att")).addColumns(df.longColumn("recept")).addColumns(df.longColumn("xpa")).addColumns(df.longColumn("fga"));
        
        DoubleColumn lp = DoubleColumn.create("Score", this.summarizeByRow(scored));
        DoubleColumn tc = DoubleColumn.create("Touches", this.summarizeByRow(touches));
        scored = Table.create("Player, Points, and Touches");   
        scored.addColumns(df.stringColumn("playerID")).addColumns(df.stringColumn("name")).addColumns(df.longColumn("season")).addColumns(df.longColumn("game.id")).addColumns(df.stringColumn("date")).addColumns(df.stringColumn("Team")).addColumns(lp).addColumns(tc);
        return scored.sortAscendingOn("playerID", "name", "season", "game.id", "date", "Team");
    }
    
    private HashMap<String, Double> scoredDict() throws Exception
    {
        HashMap <String, Double> scoring = new HashMap();
        
        //Make sure we have valid data to score on
        if (this.scoredStats.length != this.statWeights.length)
            throw new Exception("Mismatch in size between scored categories and scored weights");
        
        else
        {
            //If data is valid, generate dictionary based on scored stats
            for (int i = 0; i < this.scoredStats.length; i++)
            {
                scoring.put(this.scoredStats[i], this.statWeights[i]);
            }
        }
        return scoring;
    }
    
    private static long[] toPrimitives(Long[] objects) 
    {
        long[] primitives = new long[objects.length];
        for (int i = 0; i < objects.length; i++)
             primitives[i] = objects[i];

        return primitives;
    }
    
    //Only pass a table with valid numeric cols (e.g. don't table with pass game.id or season)
    private double[] summarizeByRow(Table df)
    {
//        df.
        List<NumericColumn<?>> cols = df.numericColumns();
        
        int x = cols.size();
        int y = cols.get(0).size();
        
        Object[][] data = new Object[x][y];
        
        ///Read table into 2D object array
        for (int i = 0; i < x; i++)
        {
            for (int j = 0; j < y; j++)
            {
                data[i][j] = cols.get(i).get(j);
            }
        }
        
        //Sum across x-axis
        try
        {
            double [] summ = new double [y];
            for (int j = 0; j < y; j++)
            {
                for (int i = 0; i < x; i++)
                {
                    summ[j] += (double)data[i][j];
                }
            }
            return summ;
        } catch (Exception e)
        {
            double [] summ = new double [y];
            for (int j = 0; j < y; j++)
            {
                for (int i = 0; i < x; i++)
                {
                    summ[j] += (long)data[i][j];
                }
            }
            return summ;
        }
    }//end summarizeByRow
    
    private Table deriveStats(Table df)
    {
        DoubleColumn st_ret_yds = df.longColumn("kickret.avg").multiply(df.longColumn("kick.rets")).add(
                    df.longColumn("puntret.avg").multiply(df.longColumn("punt.rets"))).setName("st_ret_yds");
        DoubleColumn two_pts = df.longColumn("pass.twoptm").add(df.longColumn("pass.twoptm")).add(df.longColumn("rec.twoptm")).setName("two_pts");
        DoubleColumn avg = df.longColumn("fgyds").divide(df.longColumn("fgm"));
        ArrayList<Long> fg_s = new ArrayList();
        LongColumn fga = df.longColumn("fga");
        for (int i = 0; i < avg.size(); i++)
        {
            if (avg.get(i) < 40)
                fg_s.add((fga.get(i) * 3));
            else if (avg.get(i) < 50)
                fg_s.add((fga.get(i) * 4));
            else
                fg_s.add((fga.get(i) * 5));
        }
        LongColumn fgs = LongColumn.create("fgs", toPrimitives(fg_s.toArray(new Long[fg_s.size()])));
        df = df.insertColumn(4, fgs).insertColumn(4, st_ret_yds).insertColumn(4, two_pts);
       
//                df.fgyds / df.fgm
        return df;
    }
}//end class
