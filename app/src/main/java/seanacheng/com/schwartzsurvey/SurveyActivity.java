package seanacheng.com.schwartzsurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener{

    MyDbHandler myDbHandler;
    ItemAdapter itemAdapter;
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

                if (column.startsWith("personal")) {
                    Intent disclaimerPopUp = new Intent(SurveyActivity.this,DisclaimerPopUp.class);
                    startActivity(disclaimerPopUp);
                } else {
                    Intent save = new Intent(SurveyActivity.this,MainActivity.class);
                    startActivity(save);
                }
                break;
        }
    }
}
