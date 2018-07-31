package seanacheng.com.schwartzsurvey;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    String[] resultTableHeaders;
    String[] dimensionGoals;
    Result[] resultsArray;
    MyDbHandler myDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        myDbHandler = new MyDbHandler(this);
        resultsArray = myDbHandler.getResultsArray();

        resultTableHeaders = getResources().getStringArray(R.array.resultTableHeaders);

        RadarChart chart = findViewById(R.id.radarChart);
        RadarData data = new RadarData(createDataSet());
        data.setValueTextSize(8f);
        data.setDrawValues(false);

//        chart.setWebLineWidth(1f);
        chart.setWebAlpha(100);
        chart.getDescription().setEnabled(false);
        chart.setRotationEnabled(false);
//        chart.setMinimumWidth(1000);
//        chart.setMinimumHeight(1000);
//        chart.setScaleX(2);
//        chart.setScaleY(2);
        chart.setData(data);
        chart.invalidate();

        setChartAxes(chart);
        Legend legend = chart.getLegend();
        legend.setTextSize(15f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(true);
//        legend.setXEntrySpace(5f);
//        legend.setYEntrySpace(5f);

        dimensionGoals = getResources().getStringArray(R.array.dimensionMotivationalGoals);
        fillInTableRows();
    }

    private void setChartAxes(RadarChart chart) {

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(12f);
//        xAxis.setXOffset(0);
//        xAxis.setYOffset(0);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                final String[] qualities = new String[resultsArray.length];
                int i = 0;
                for (Result r:resultsArray) {
                    qualities[i] = r.getValueDimension();
                    i++;
                }
                return qualities[(int) value % qualities.length];
            }
        });
        xAxis.setTextColor(Color.DKGRAY);

        YAxis yAxis = chart.getYAxis();
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(6);
//        yAxis.setLabelCount(5);
//        yAxis.setTextSize(8f);
        yAxis.setDrawLabels(false);
    }
    private RadarDataSet createDataSet() {
        List<RadarEntry> entryArrayList = new ArrayList<>();

        for (int i=0;i<resultsArray.length;i++) {
            RadarEntry entry = new RadarEntry((float) resultsArray[i].getPersonalScore());
            entryArrayList.add(entry);
        }

        RadarDataSet dataSet = new RadarDataSet(entryArrayList,"Personal Score");
        dataSet.setColor(R.color.darkPurple);
        dataSet.setFillColor(R.color.darkPurple);
        dataSet.setDrawFilled(true);
        dataSet.setFillAlpha(180);
//        dataSet.setLineWidth(2f);
        return dataSet;
    }

    private void fillInTableRows() {
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        String score;

        for (int i=0;i<resultsArray.length;i++) {
            TableRow tableRow = new TableRow(this);

            TextView dimension = new TextView(this);
            dimension.setText(resultsArray[i].getValueDimension());
            dimension.setMinHeight(10);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) dimension.getLayoutParams();
            params.setMargins(0,0,3,0);
            tableRow.addView(dimension);

            TextView personalScore = new TextView(this);
            score = decimalFormat.format(resultsArray[i].getPersonalScore());
            personalScore.setText(score);
            tableRow.addView(personalScore);

            TextView employerScore = new TextView(this);
            score = decimalFormat.format(resultsArray[i].getEmployerScore());
            employerScore.setText(score);
            tableRow.addView(employerScore);

//            TextView description = new TextView(this);
//            description.setText(dimensionGoals[i]);
//            tableRow.addView(description);

            tableLayout.addView(tableRow);

        }
    }
}
