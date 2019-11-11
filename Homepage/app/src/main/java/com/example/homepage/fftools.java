package com.example.homepage;

import java.util.ArrayList;
import java.util.HashMap;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.LongColumn;
import tech.tablesaw.api.Table;

/**
 *
 * @author chaseuphaus
 */
public class fftools 
{
    public Table deriveStats(Table df)
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
    
    public Table applyScoring(Table df)
    {
        //Declare dict with weights for scored stats in league
        HashMap <String, Double> scoring = new HashMap();
        scoring.put("pass.comp", .1);
        scoring.put("passyds", (double) 1 / 25);
        scoring.put("pass.tds", (double) 6);
        scoring.put("pass.ints", (double) -2);
        scoring.put("rush.att", .1);
        scoring.put("rushyds", (double) 1 / 10);
        scoring.put("rushtds", (double) 6);
        scoring.put("recept", .4);
        scoring.put("recyds", (double) 1 / 10);
        scoring.put("rec.tds", (double) 6);
        scoring.put("st_ret_yds", (double) 1 / 20);
        scoring.put("kickret.tds", (double) 6);
        scoring.put("puntret.tds", (double) 6);
        scoring.put("fumbslost", (double) -2);
        scoring.put("two_pts", (double) 2);
        scoring.put("xpa", (double) 1);
        scoring.put("fgs", (double) 1);
        String current;
        for (int i = 0; i < scoring.keySet().size(); i++)
        {
            current = scoring.keySet().toArray()[i].toString();
            if (current == "two_pts" || current == "st_ret_yds")
                System.out.println(df.doubleColumn(current).multiply(scoring.get(current)));
            else
                df.longColumn(current).multiply(scoring.get(current));
        }
//        'rush.att', 'recept', 'fga', 'xpa'
//        LongColumn touches = LongColumn.create("Total_Touches", );
        System.out.println(df);
        return null;
    }
    
    public static long[] toPrimitives(Long[] objects) 
    {
        long[] primitives = new long[objects.length];
        for (int i = 0; i < objects.length; i++)
             primitives[i] = objects[i];

        return primitives;
    }
}
