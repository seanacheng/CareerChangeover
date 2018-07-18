package seanacheng.com.schwartzsurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SurveyMenuActivity extends AppCompatActivity {

    Button begin_survey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_menu);

        begin_survey = findViewById(R.id.begin_survey);
        begin_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent begin_survey = new Intent(SurveyMenuActivity.this,SurveyActivity.class);
                startActivity(begin_survey);

            }
        });

    }

}
