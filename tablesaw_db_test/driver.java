package com.theboyz.tablesaw_db_test;
import java.util.ArrayList;
import java.util.List;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumericColumn;
import tech.tablesaw.api.Table;
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
        userDB dbHelper = new userDB();
        dbInterface statDB = new dbInterface();
        Table stats_2019 = Table.read().db(statDB.pull_from_table("player_stats_2019"));
        userAccount user = dbHelper.authenticate("nflfan101@email.com", "cowboyssuck666");
        System.out.println(user.userStats.applyScoring(stats_2019));

//        NumericColumn<?> x = stats.nCol("Touches");
//        NumericColumn<?> y = stats.nCol("Score");
//
//        Layout layout = Layout.builder().title("FFS").hoverMode(Layout.HoverMode.FALSE).showLegend(true).build();
//        ScatterTrace trace = ScatterTrace.builder(x, y).mode(ScatterTrace.Mode.MARKERS).build();
//        Plot.show(new Figure(layout, trace));
        
    }
    
    
}
