package seanacheng.com.schwartzsurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button identify_values, view_results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        identify_values = findViewById(R.id.take_survey);
        view_results = findViewById(R.id.view_results);
        identify_values.setOnClickListener(this);
        view_results.setOnClickListener(this);
        Log.d("tag", "onCreate");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_survey:
                Intent toSurveyMenu = new Intent(MainActivity.this,SurveyMenuActivity.class);
                startActivity(toSurveyMenu);
                Log.d("tag", "onClick: identify values");
                break;
            case R.id.view_results:
                Log.d("tag","onClick: view results");
                break;
        }
    }

}
