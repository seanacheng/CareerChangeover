package seanacheng.com.schwartzsurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyDbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.takeSelfSurveyButton).setOnClickListener(this);
        findViewById(R.id.takeEmployerSurveyButton).setOnClickListener(this);
        findViewById(R.id.viewResultsButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takeSelfSurveyButton:
                Intent toSelfEval = new Intent(MainActivity.this,SurveyActivity.class);
                toSelfEval.putExtra("column_name", dbHandler.COLUMN_SELF_EVAL);
                startActivity(toSelfEval);
                break;
            case R.id.takeEmployerSurveyButton:
                Intent toEmployerEval = new Intent(MainActivity.this,SurveyActivity.class);
                toEmployerEval.putExtra("column_name", dbHandler.COLUMN_EMPLOYER_EVAL);
                startActivity(toEmployerEval);
                break;
            case R.id.viewResultsButton:
                break;
        }
    }

}
