package seanacheng.com.schwartzsurvey;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static seanacheng.com.schwartzsurvey.MyDbHandler.COLUMN_VALUE;
import static seanacheng.com.schwartzsurvey.MyDbHandler.TABLE_NAME;

public class SurveyActivity extends AppCompatActivity {

    SQLiteOpenHelper SQLiteOpenHelper;
    SQLiteDatabase SQLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        SQLiteOpenHelper = new MyDbHandler(getApplicationContext());

        SQLiteDatabase = SQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = SQLiteDatabase.rawQuery("select "+ COLUMN_VALUE+" from "+ TABLE_NAME,null);

        cursor.close();

    }

}
