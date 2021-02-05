package careerchangeover.com.valuesvisualizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyDbHandler dbHandler;
    SharedPreferences mPref;
    String seeTutorialPref = "openTutorial";
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        // default open tutorial flag set to true
        boolean goToTutorial = mPref.getBoolean(seeTutorialPref,true);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // opens tutorial is flag is set to true
        if (goToTutorial) {
            Intent openTutorial = new Intent(MainActivity.this,TutorialActivity.class);
            startActivity(openTutorial);
        }

        // Sets button on click listeners
        findViewById(R.id.takeSelfSurveyButton).setOnClickListener(this);
        findViewById(R.id.takeEmployerSurveyButton).setOnClickListener(this);
        findViewById(R.id.viewResultsButton).setOnClickListener(this);
        findViewById(R.id.crashButton).setOnClickListener(this);
        findViewById(R.id.testButton).setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main activity menu
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        switch (item.getItemId()) {
            case R.id.viewTutorial:
                Intent goToTutorial = new Intent(MainActivity.this,TutorialActivity.class);
                goToTutorial.putExtra("allow",true);
                startActivity(goToTutorial);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takeSelfSurveyButton:
                // Switches to personal survey page with extra
                Intent toSelfEval = new Intent(MainActivity.this,SurveyActivity.class);
                toSelfEval.putExtra("column_name", dbHandler.COLUMN_SELF_EVAL);
                startActivity(toSelfEval);
                break;
            case R.id.takeEmployerSurveyButton:
                // Switches to employer survey page with extra
                Intent toEmployerEval = new Intent(MainActivity.this,SurveyActivity.class);
                toEmployerEval.putExtra("column_name", dbHandler.COLUMN_EMPLOYER_EVAL);
                startActivity(toEmployerEval);
                break;
            case R.id.viewResultsButton:
                // Switches to results page
                Intent toResults = new Intent(MainActivity.this,ResultsActivity.class);
                startActivity(toResults);
                break;
            case R.id.crashButton:
                throw new RuntimeException("Test Crash"); // Force a crash

            case R.id.testButton:
                //Test screen for testing
                WebView myWebView = new WebView(this);
                setContentView(myWebView);
                myWebView.loadUrl("https://my.spline.design/iconcloud-0890b2a73879f5f716c0730e27801018/");
                WebSettings webSettings = myWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
        }
    }
}
