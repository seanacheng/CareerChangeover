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
        column = getIntent().getStringExtra("column_name");

        setSurveyStatement();

        myDbHandler = new MyDbHandler(this);
        valuesArray = myDbHandler.getValuesArray();

        itemAdapter = new ItemAdapter(this,valuesArray,column);
        listView = findViewById(R.id.surveyListView);
        listView.setAdapter(itemAdapter);

        findViewById(R.id.saveButton).setOnClickListener(this);

    }

    public void setSurveyStatement() {

        surveyStatement = findViewById(R.id.surveyStatementTextView);

        if (column.startsWith("personal")) {
            surveyStatement.setText(getResources().getString(R.string.personal_survey_statement));
            surveyStatement.setTextSize(getResources().getDimension(R.dimen.survey_statement_text_size));
        } else if (column.startsWith("employer")) {
            surveyStatement.setText(getResources().getString(R.string.employer_survey_statement));
            surveyStatement.setTextSize(getResources().getDimension(R.dimen.survey_statement_text_size));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveButton:

                valuesArray = itemAdapter.getValuesArray();
                myDbHandler.updateRank(valuesArray,column);

                mPref = PreferenceManager.getDefaultSharedPreferences(this);
                boolean doNotShowAgain = mPref.getBoolean(disclaimerAcceptedPref,false);

                if (column.startsWith("employer") || doNotShowAgain) {
                    Intent save = new Intent(SurveyActivity.this,MainActivity.class);
                    startActivity(save);
                } else {
                    Intent disclaimerPopUp = new Intent(SurveyActivity.this,DisclaimerPopUp.class);
                    startActivity(disclaimerPopUp);
                }
                if (checkSurveyComplete()) {
                    myDbHandler.calculateScore(valuesArray,column);
                }

                break;
        }
    }

    private boolean checkSurveyComplete() {
        for (Value value: valuesArray) {
            int rank = value.getRank(column);
            if (rank == -1) return false;
        }
        return true;
    }
}
