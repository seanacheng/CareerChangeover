package seanacheng.com.schwartzsurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener{

    MyDbHandler myDbHandler;
    ItemAdapter itemAdapter;
    ListView listView;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        myDbHandler = new MyDbHandler(this);
        List<Value> valuesList = myDbHandler.getValuesList();

        listView = findViewById(R.id.surveyListView);
        itemAdapter = new ItemAdapter(this,valuesList);
        listView.setAdapter(itemAdapter);
        Log.d("tag", "onCreate: setAdapter");

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        Log.d("tag", "onCreate: done");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveButton:

        }
        Intent save = new Intent();
        startActivity(save);
    }

    public void onRadioClick(View view) {

    }
}
