package seanacheng.com.schwartzsurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.List;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener{

    MyDbHandler myDbHandler;
    ItemAdapter itemAdapter;
    ListView listView;
    Button saveButton;
    List<Value> valuesList;
    String column;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        column = getIntent().getStringExtra("column_name");

        myDbHandler = new MyDbHandler(this);
        valuesList = myDbHandler.getValuesList();

        listView = findViewById(R.id.surveyListView);
        itemAdapter = new ItemAdapter(this,valuesList,column);
        listView.setAdapter(itemAdapter);

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);
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
