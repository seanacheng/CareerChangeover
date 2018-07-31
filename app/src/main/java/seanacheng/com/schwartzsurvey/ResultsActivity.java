package seanacheng.com.schwartzsurvey;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
//
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultsActivity extends AppCompatActivity {

    String[] resultTableHeaders;
    String[] dimensionGoals;
    Result[] resultsArray;
    MyDbHandler myDbHandler;
    RadarData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        myDbHandler = new MyDbHandler(this);
        resultsArray = myDbHandler.getResultsArray();

        resultTableHeaders = getResources().getStringArray(R.array.resultTableHeaders);

        RadarChart chart = findViewById(R.id.radarChart);
        createDataSet();

        chart.setWebAlpha(100);
        chart.getDescription().setEnabled(false);
        chart.setRotationEnabled(false);
        chart.setMinimumWidth(getScreenResolution(this).get("width"));
        chart.setMinimumHeight(getScreenResolution(this).get("width"));
        chart.setData(data);
        chart.invalidate();

        setChartAxes(chart);
        Legend legend = chart.getLegend();
        legend.setTextSize(15f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(true);

        dimensionGoals = getResources().getStringArray(R.array.dimensionMotivationalGoals);
        fillInTableRows();
    }

    private void setChartAxes(RadarChart chart) {

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(14);
        xAxis.setXOffset(0);
        xAxis.setYOffset(0);
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
    private void createDataSet() {
        List<RadarEntry> personalEntryArrayList = new ArrayList<>();
        List<RadarEntry> employerEntryArrayList = new ArrayList<>();

        for (int i=0;i<resultsArray.length;i++) {
            RadarEntry personalEntry = new RadarEntry((float) resultsArray[i].getPersonalScore());
            personalEntryArrayList.add(personalEntry);
            RadarEntry employerEntry = new RadarEntry((float) resultsArray[i].getEmployerScore());
            employerEntryArrayList.add(employerEntry);
        }

        RadarDataSet dataSet1 = new RadarDataSet(personalEntryArrayList,"Personal Score");
        dataSet1.setColor(Color.GREEN);
        dataSet1.setFillColor(Color.GREEN);
        dataSet1.setDrawFilled(true);
        dataSet1.setFillAlpha(180);
        dataSet1.setDrawHighlightCircleEnabled(true);

        RadarDataSet dataSet2 = new RadarDataSet(employerEntryArrayList,"Employer Score");
        dataSet2.setColor(Color.RED);
        dataSet2.setFillColor(Color.RED);
        dataSet2.setDrawFilled(true);
        dataSet2.setFillAlpha(180);
        dataSet2.setDrawHighlightCircleEnabled(true);

        ArrayList<IRadarDataSet> list = new ArrayList<>();
        list.add(dataSet1);
        list.add(dataSet2);

        data = new RadarData(list);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
    }

    private void fillInTableRows() {
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        String score;

        for (int i=0;i<resultsArray.length;i++) {
            TableRow tableRow = new TableRow(this);

            TextView dimension = new TextView(this);
            dimension.setText(resultsArray[i].getValueDimension());
            dimension.setTextSize(16);
            dimension.setMinHeight(10);
            tableRow.addView(dimension);

            TextView personalScore = new TextView(this);
            score = decimalFormat.format(resultsArray[i].getPersonalScore());
            personalScore.setText(score);
            personalScore.setTextSize(16);
            personalScore.setGravity(Gravity.RIGHT);
            tableRow.addView(personalScore);

            TextView employerScore = new TextView(this);
            score = decimalFormat.format(resultsArray[i].getEmployerScore());
            employerScore.setText(score);
            employerScore.setTextSize(16);
            employerScore.setGravity(Gravity.RIGHT);
            tableRow.addView(employerScore);

            tableLayout.addView(tableRow);

        }
    }

    private static Map<String, Integer> getScreenResolution(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        HashMap<String, Integer> map = new HashMap<>();
        map.put("width",width);
        map.put("height",height);
        return map;
    }
}