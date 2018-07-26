package seanacheng.com.schwartzsurvey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener{

    MyDbHandler myDbHandler;
    ItemAdapter itemAdapter;
    SharedPreferences mPref;
    String disclaimerAcceptedPref = "disclaimerAccepted";
    TextView footnote;
    ListView listView;
    Value[] valuesArray;
    String column;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        column = getIntent().getStringExtra("column_name");

        setFootnoteText();

        myDbHandler = new MyDbHandler(this);
        valuesArray = myDbHandler.getValuesArray();

        itemAdapter = new ItemAdapter(this,valuesArray,column);
        listView = findViewById(R.id.surveyListView);
        listView.setAdapter(itemAdapter);

        findViewById(R.id.saveButton).setOnClickListener(this);

    }

    public void setFootnoteText() {

        footnote = findViewById(R.id.surveyStatementTextView);

        if (column.startsWith("personal")) {
            footnote.setText(getResources().getString(R.string.personal_survey_statement));
        } else if (column.startsWith("employer")) {
            footnote.setText(getResources().getString(R.string.employer_survey_statement));
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
                break;
        }
    }
}
