package seanacheng.com.schwartzsurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener{

    MyDbHandler myDbHandler;
    ItemAdapter itemAdapter;
    TextView footnote;
    ListView listView;
    List<Value> valuesList;
    String column;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        column = getIntent().getStringExtra("column_name");

        setFootnoteText();

        myDbHandler = new MyDbHandler(this);
        valuesList = myDbHandler.getValuesList();

        itemAdapter = new ItemAdapter(this,valuesList,column);
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
                valuesList = itemAdapter.getValuesList();
                myDbHandler.updateRank(valuesList,column);
                Intent save = new Intent(SurveyActivity.this,MainActivity.class);
                startActivity(save);
                break;
        }
    }
}
