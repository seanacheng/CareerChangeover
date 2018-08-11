package seanacheng.com.schwartzsurvey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener{

    MyDbHandler myDbHandler;
    ItemAdapter itemAdapter;
    SharedPreferences mPref;
    String disclaimerAcceptedPref = "disclaimerAccepted";
    TextView surveyStatement;
    ListView listView;
    Value[] valuesArray;
    String column;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        // contains extra about which survey is being taken
        column = getIntent().getStringExtra("column_name");

        // Finds which survey question to ask
        setSurveyStatement();

        // Initializes db handler class
        myDbHandler = new MyDbHandler(this);
        valuesArray = myDbHandler.getValuesArray();

        // List View Adapter
        itemAdapter = new ItemAdapter(this,valuesArray,column);
        listView = findViewById(R.id.surveyListView);
        listView.setAdapter(itemAdapter);

        findViewById(R.id.saveButton).setOnClickListener(this);

    }

    public void setSurveyStatement() {
        // Displays correct survey question
        surveyStatement = findViewById(R.id.surveyStatementTextView);

        if (column.startsWith("personal")) {
            surveyStatement.setText(getResources().getString(R.string.personal_survey_statement));
            surveyStatement.setAllCaps(true);
        } else if (column.startsWith("employer")) {
            surveyStatement.setText(getResources().getString(R.string.employer_survey_statement));
            surveyStatement.setAllCaps(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveButton:
                // Db updated with rank entries
                valuesArray = itemAdapter.getValuesArray();
                myDbHandler.updateRank(valuesArray,column);

                // Default disclaimer accepted flag set to false
                mPref = PreferenceManager.getDefaultSharedPreferences(this);
                boolean doNotShowAgain = mPref.getBoolean(disclaimerAcceptedPref,false);

                // Decides whether or not to view disclaimer
                if (column.startsWith("employer") || doNotShowAgain) {
                    Intent save = new Intent(SurveyActivity.this,MainActivity.class);
                    startActivity(save);
                } else {
                    Intent disclaimerPopUp = new Intent(SurveyActivity.this,DisclaimerPopUp.class);
                    startActivity(disclaimerPopUp);
                }
                // Calculates results if survey is completed
                if (checkSurveyComplete()) {
                    myDbHandler.calculateScore(valuesArray,column);
                }

                break;
        }
    }

    private boolean checkSurveyComplete() {
        // Checks if survey is complete
        for (Value value: valuesArray) {
            int rank = value.getRank(column);
            if (rank == -1) return false;
        }
        return true;
    }
}
