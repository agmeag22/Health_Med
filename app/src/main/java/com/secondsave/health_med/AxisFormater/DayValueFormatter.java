package com.secondsave.health_med.AxisFormater;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by philipp on 02/06/16.
 */
public class DayValueFormatter implements IAxisValueFormatter
{

    protected String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    private BarLineChartBase<?> chart;

    public DayValueFormatter(BarLineChartBase<?> chart) {
        this.chart = chart;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        Date date = new Date();
        date.setTime((long)value);
        SimpleDateFormat df;
        if (chart.getVisibleXRange() > 30 * 6) {
            df = new SimpleDateFormat("MMM yyyy");
            String monthYear = df.format(date);
            return monthYear;
        } else {

            df = new SimpleDateFormat("d MM");
            String monthYear = df.format(date);
            return monthYear;
        }
    }

}