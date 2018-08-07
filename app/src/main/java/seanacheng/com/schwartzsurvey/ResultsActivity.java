package seanacheng.com.schwartzsurvey;

import android.content.Context;
import android.graphics.Color;
import android.print.PrintAttributes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
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
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultsActivity extends AppCompatActivity {

    Result[] resultsArray;
    MyDbHandler myDbHandler;
    RadarData data;
    RadarChart chart;
    Legend legend;

    LinearLayout linearLayout;
    TableLayout table;
    TextView warning;

    String both;
    String personalOnly;
    String employerOnly;
    String resultsToView;

    String personalScore;
    String employerScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Initializes db handler class
        myDbHandler = new MyDbHandler(this);
        resultsArray = myDbHandler.getResultsArray();

        // Initializes String resources
        both = getResources().getString(R.string.both);
        personalOnly = getResources().getString(R.string.personal_only);
        employerOnly = getResources().getString(R.string.employer_only);
        resultsToView = both;
        personalScore = getResources().getString(R.string.table_header_personal_score);
        employerScore = getResources().getString(R.string.table_header_employer_score);

        // Displays chart and table
        chart = findViewById(R.id.radarChart);
        linearLayout = findViewById(R.id.linearLayout);
        table = findViewById(R.id.tableLayout);
        displayChartAndTable(createDataSet());

        // Chart settings
        chart.setWebAlpha(100);
        chart.getDescription().setEnabled(false);
        chart.setRotationEnabled(false);
        chart.setMinimumWidth(getScreenResolution(this).get("width"));
        chart.setMinimumHeight(getScreenResolution(this).get("width"));
        chart.setData(data);
        chart.invalidate();

        setChartAxes(chart);

        // Creates a chart legend
        legend = chart.getLegend();
        legend.setTextSize(15f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflates chart view menu
        getMenuInflater().inflate(R.menu.radar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.both:
                // Shows results of both personal and employer surveys
                resultsToView = both;
                displayChartAndTable(createDataSet());
                break;
            case R.id.personalOnly:
                // Shows personal survey results only
                resultsToView = personalOnly;
                displayChartAndTable(createDataSet());
                break;
            case R.id.employerOnly:
                // Shows employer survey results only
                resultsToView = employerOnly;
                displayChartAndTable(createDataSet());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setChartAxes(RadarChart chart) {
        // Chart axes settings
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

    private boolean checkResultsExist(String column) {
        // Checks that result array is not empty
        for (Result result: resultsArray) {
            if (result.getScore(column) == -1) return false;
        }
        return true;
    }

    private ArrayList<IRadarDataSet> createDataSet() {
        // Returns list of type data set to display on the chart
        List<RadarEntry> personalEntryArrayList = new ArrayList<>();
        List<RadarEntry> employerEntryArrayList = new ArrayList<>();

        // Reads scores from results array
        for (int i = 0; i < resultsArray.length; i++) {
            RadarEntry personalEntry = new RadarEntry((float) resultsArray[i].getPersonalScore());
            personalEntryArrayList.add(personalEntry);
            RadarEntry employerEntry = new RadarEntry((float) resultsArray[i].getEmployerScore());
            employerEntryArrayList.add(employerEntry);
        }

        ArrayList<IRadarDataSet> list = new ArrayList<>();

        if (!resultsToView.equals(employerOnly) && checkResultsExist(personalScore)) {
            // Fills type data set with data and settings
            RadarDataSet dataSet1 = new RadarDataSet(personalEntryArrayList, personalScore);
            dataSet1.setColor(Color.GREEN);
            dataSet1.setFillColor(Color.GREEN);
            dataSet1.setDrawFilled(true);
            dataSet1.setFillAlpha(180);
//            dataSet1.setDrawHighlightCircleEnabled(true);
            list.add(dataSet1);
        }

        if (!resultsToView.equals(personalOnly) && checkResultsExist(employerScore)) {
            // Fills type data set with data and settings
            RadarDataSet dataSet2 = new RadarDataSet(employerEntryArrayList, employerScore);
            dataSet2.setColor(Color.RED);
            dataSet2.setFillColor(Color.RED);
            dataSet2.setDrawFilled(true);
            dataSet2.setFillAlpha(180);
//            dataSet2.setDrawHighlightCircleEnabled(true);
            list.add(dataSet2);
        }
        return list;
    }

    private void displayChartAndTable(ArrayList<IRadarDataSet> list) {
        // Displays chart and table data if given
        // Erases chart and table and shows warning if no data is available
        if (!list.isEmpty()) {
            data = new RadarData(list);
            data.setValueTextSize(8f);
            data.setDrawValues(false);

            chart.setData(data);
            chart.invalidate();

            fillInTableRows();

            linearLayout.removeView(warning);
            chart.setVisibility(View.VISIBLE);
        } else {
            linearLayout.removeView(warning);
            chart.clear();
            chart.setVisibility(View.GONE);
            table.removeAllViews();
            showWarning();
        }

    }

    private void fillInTableRows() {
        // Refreshes table data
        table.removeAllViews();
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        String score;

        // Sets table margins
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        int columnSpacing = (int) getResources().getDimension(R.dimen.column_spacing_margin);
        params.setMargins(columnSpacing,0,0,0);

        // Creates header row
        TableRow headerRow = new TableRow(this);
        TextView dimensionHeader = new TextView(this);
        headerRow.addView(dimensionHeader);

        if (!resultsToView.equals(employerOnly) && checkResultsExist(personalScore)) {
            TextView personalScoreHeader = new TextView(this);
            personalScoreHeader.setText(personalScore);
            personalScoreHeader.setTextSize(16);
            personalScoreHeader.setGravity(Gravity.CENTER);
            personalScoreHeader.setLayoutParams(params);
            headerRow.addView(personalScoreHeader);
        }

        if (!resultsToView.equals(personalOnly) && checkResultsExist(employerScore)) {
            TextView employerScoreHeader = new TextView(this);
            employerScoreHeader.setText(employerScore);
            employerScoreHeader.setTextSize(16);
            employerScoreHeader.setGravity(Gravity.CENTER);
            employerScoreHeader.setLayoutParams(params);
            headerRow.addView(employerScoreHeader);
        }

        table.addView(headerRow);

        // Creates data rows
        for (int i=0;i<resultsArray.length;i++) {
            TableRow tableRow = new TableRow(this);

            TextView dimension = new TextView(this);
            dimension.setText(resultsArray[i].getValueDimension());
            dimension.setTextSize(16);
            dimension.setMinHeight(10);
            tableRow.addView(dimension);

            if (!resultsToView.equals(employerOnly) && checkResultsExist(personalScore)) {
                TextView personalScore = new TextView(this);
                score = decimalFormat.format(resultsArray[i].getPersonalScore());
                personalScore.setText(score);
                personalScore.setTextSize(16);
                personalScore.setGravity(Gravity.RIGHT);
                tableRow.addView(personalScore);
            }

            if (!resultsToView.equals(personalOnly) && checkResultsExist(employerScore)) {
                TextView employerScore = new TextView(this);
                score = decimalFormat.format(resultsArray[i].getEmployerScore());
                employerScore.setText(score);
                employerScore.setTextSize(16);
                employerScore.setGravity(Gravity.RIGHT);
                tableRow.addView(employerScore);
            }

            table.addView(tableRow);

        }
    }

    private void showWarning() {
        // Creates and displays warning text if no data is available
        warning = new TextView(this);
        warning.setGravity(Gravity.CENTER);
        String blank = "";
        if (resultsToView.equals(both)) {
            blank = "both";
        }
        else if (resultsToView.equals(personalOnly)) {
            blank = "the personal";
        }
        else if (resultsToView.equals(employerOnly)) {
            blank = "the employer";
        }
        warning.setText("Warning:\n No results found. Please complete "+blank+" survey(s) to view the results.");
        warning.setTextSize(28);
        linearLayout.addView(warning);

    }

    private static Map<String, Integer> getScreenResolution(Context context) {
        // Returns map of width and height of screen
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