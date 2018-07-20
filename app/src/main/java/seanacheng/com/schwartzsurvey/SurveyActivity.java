package seanacheng.com.schwartzsurvey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

public class SurveyActivity extends AppCompatActivity{

    MyDbHandler myDbHandler;
    ItemAdapter itemAdapter;
    ListView listView;
    List<Value> valuesList = new LinkedList<>();
    List<String> stringList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        myDbHandler = new MyDbHandler(this);
        valuesList = myDbHandler.getValuesList();
        Log.d("tag", "my list:"+valuesList.toString()+"end list");

        for (Value v:valuesList) {
            String valueStatement = v.getValue();
            stringList.add(valueStatement);
        }

        listView = findViewById(R.id.surveyListView);
        itemAdapter = new ItemAdapter(this,stringList);
        listView.setAdapter(itemAdapter);



    }

}
