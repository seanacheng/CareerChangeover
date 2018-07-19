package seanacheng.com.schwartzsurvey;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.util.HashMap;
import java.util.Map;

import static seanacheng.com.schwartzsurvey.MyDbHandler.COLUMN_VALUE;
import static seanacheng.com.schwartzsurvey.MyDbHandler.TABLE_NAME;

public class SurveyActivity extends AppCompatActivity {

    SQLiteOpenHelper SQLiteOpenHelper;
    SQLiteDatabase SQLiteDatabase;
    ItemAdapter ItemAdapter;
    Context context;
    ListView listView;
    Map<String,RadioGroup> valuesMap = new HashMap<String, RadioGroup>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        listView = (ListView) findViewById(R.id.surveyListView);
        context = this;

        ItemAdapter = new ItemAdapter(context,valuesMap);
        listView.setAdapter(ItemAdapter);

        SQLiteOpenHelper = new MyDbHandler(getApplicationContext());

        SQLiteDatabase = SQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = SQLiteDatabase.rawQuery("select "+ COLUMN_VALUE+" from "+ TABLE_NAME,null);

        cursor.close();

    }

}
