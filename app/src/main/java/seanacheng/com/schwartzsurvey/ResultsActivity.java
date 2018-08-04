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

        myDbHandler = new MyDbHandler(this);
        resultsArray = myDbHandler.getResultsArray();

        both = getResources().getString(R.string.both);
        personalOnly = getResources().getString(R.string.personal_only);
        employerOnly = getResources().getString(R.string.employer_only);
        resultsToView = both;
        personalScore = getResources().getString(R.string.table_header_personal_score);
        employerScore = getResources().getString(R.string.table_header_employer_score);

        chart = findViewById(R.id.radarChart);
        linearLayout = findViewById(R.id.linearLayout);
        table = findViewById(R.id.tableLayout);
        displayChartAndTable(createDataSet());

        chart.setWebAlpha(100);
        chart.getDescription().setEnabled(false);
        chart.setRotationEnabled(false);
        chart.setMinimumWidth(getScreenResolution(this).get("width"));
        chart.setMinimumHeight(getScreenResolution(this).get("width"));
        chart.setData(data);
        chart.invalidate();

        setChartAxes(chart);

        legend = chart.getLegend();
        legend.setTextSize(15f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.radar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.both:
                resultsToView = both;
                displayChartAndTable(createDataSet());
                break;
            case R.id.personalOnly:
                resultsToView = personalOnly;
                displayChartAndTable(createDataSet());
                break;
            case R.id.employerOnly:
                resultsToView = employerOnly;
                displayChartAndTable(createDataSet());
                break;
        }
        return super.onOptionsItemSelected(item);
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

    private boolean checkResultsExist(String column) {
        for (Result result: resultsArray) {
            if (result.getScore(column) == -1) return false;
        }
        return true;
    }

    private ArrayList<IRadarDataSet> createDataSet() {
        List<RadarEntry> personalEntryArrayList = new ArrayList<>();
        List<RadarEntry> employerEntryArrayList = new ArrayList<>();

        for (int i = 0; i < resultsArray.length; i++) {
            RadarEntry personalEntry = new RadarEntry((float) resultsArray[i].getPersonalScore());
            personalEntryArrayList.add(personalEntry);
            RadarEntry employerEntry = new RadarEntry((float) resultsArray[i].getEmployerScore());
            employerEntryArrayList.add(employerEntry);
        }

        ArrayList<IRadarDataSet> list = new ArrayList<>();

        if (!resultsToView.equals(employerOnly) && checkResultsExist(personalScore)) {

            RadarDataSet dataSet1 = new RadarDataSet(personalEntryArrayList, personalScore);
            dataSet1.setColor(Color.GREEN);
            dataSet1.setFillColor(Color.GREEN);
            dataSet1.setDrawFilled(true);
            dataSet1.setFillAlpha(180);
//            dataSet1.setDrawHighlightCircleEnabled(true);
            list.add(dataSet1);
        }

        if (!resultsToView.equals(personalOnly) && checkResultsExist(employerScore)) {

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
        table.removeAllViews();
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        String score;

        TableRow.LayoutParams params = new TableRow.LayoutParams();
        int columnSpacing = (int) getResources().getDimension(R.dimen.column_spacing_margin);
        params.setMargins(columnSpacing,0,0,0);

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