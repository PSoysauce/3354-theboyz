package com.theboyz.tablesaw_db_test;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONObject;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumericColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.json.JsonReadOptions;
import tech.tablesaw.io.DataFrameReader;
import tech.tablesaw.columns.Column;

import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Layout;
import tech.tablesaw.plotly.traces.ScatterTrace;
import tech.tablesaw.plotly.components.HoverLabel;

public class driver 
{
    public static void main(String args[]) throws Exception
    {
        statDB stats = new statDB();
        userDB users = new userDB();
        userAccount session;
        if ((session = users.authenticate("test@example.com", "password")) != null)
        {
            System.out.println(session.userStats.applyScoring(stats.queryTable()));
        }//End if
        
        else
        {
            System.out.println("Authentication Failed");
        }//End else
        
//        Table statTable = user.userStats.applyScoring(stats.queryTable());
        
//        NumericColumn<?> x = statTable.nCol("Touches");
//        NumericColumn<?> y = statTable.nCol("Score");
          
//        Layout layout = Layout.builder().title("FFS").hoverMode(Layout.HoverMode.FALSE).showLegend(true).build();
//        ScatterTrace trace = ScatterTrace.builder(x, y).mode(ScatterTrace.Mode.MARKERS).build();
//        Plot.show(new Figure(layout, trace));
//        System.out.println(statTable);
        
        
    }
    
    
    
}
