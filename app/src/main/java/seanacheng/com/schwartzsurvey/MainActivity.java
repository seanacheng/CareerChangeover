package seanacheng.com.schwartzsurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button identify_my_values, identify_employer_values, view_results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        identify_my_values = findViewById(R.id.take_self_survey);
        identify_employer_values = findViewById(R.id.take_employer_survey);
        view_results = findViewById(R.id.view_results);
        identify_my_values.setOnClickListener(this);
        identify_employer_values.setOnClickListener(this);
        view_results.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_self_survey:
                Intent toSurveyMenu = new Intent(MainActivity.this,SurveyActivity.class);
                startActivity(toSurveyMenu);
                break;
            case R.id.take_employer_survey:
                break;
            case R.id.view_results:
                break;
        }
    }

}
