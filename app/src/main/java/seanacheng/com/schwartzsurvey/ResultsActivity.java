package seanacheng.com.schwartzsurvey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    String[] resultTableHeaders;
    String[] dimensionGoals;
    Result[] resultsArray;
    MyDbHandler myDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        myDbHandler = new MyDbHandler(this);
        resultsArray = myDbHandler.getResultsArray();

        resultTableHeaders = getResources().getStringArray(R.array.resultTableHeaders);
        dimensionGoals = getResources().getStringArray(R.array.dimensionMotivationalGoals);

        for (int i=0;i<resultsArray.length;i++) {
            TableRow tableRow = new TableRow(this);

            TextView dimension = new TextView(this);
            dimension.setText(resultsArray[i].getValueDimension());
            dimension.setMinHeight(10);
            tableRow.addView(dimension);

            TextView personalScore = new TextView(this);
            personalScore.setText(Double.toString(resultsArray[i].getPersonalScore()));
            tableRow.addView(personalScore);

            TextView employerScore = new TextView(this);
            employerScore.setText(Double.toString(resultsArray[i].getEmployerScore()));
            tableRow.addView(employerScore);

//            TextView description = new TextView(this);
//            description.setText(dimensionGoals[i]);
//            tableRow.addView(description);

            tableLayout.addView(tableRow);

        }

    }
}
