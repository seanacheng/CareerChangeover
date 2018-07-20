package seanacheng.com.schwartzsurvey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SurveyActivity extends AppCompatActivity{

    MyDbHandler myDbHandler;
    ItemAdapter itemAdapter;
    ListView listView;
    Map<Integer, String> valueMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        myDbHandler = new MyDbHandler(this);
        List<Value> valuesList = myDbHandler.getValuesList();

        for (Value v:valuesList) {
            int id = v.getID();
            String value = v.getValue();
            valueMap.put(id,value);
        }

        listView = findViewById(R.id.surveyListView);
        itemAdapter = new ItemAdapter(this,valueMap);
        listView.setAdapter(itemAdapter);

    }

}
