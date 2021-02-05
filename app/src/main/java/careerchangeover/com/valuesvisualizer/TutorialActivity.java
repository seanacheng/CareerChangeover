package careerchangeover.com.valuesvisualizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;

public class TutorialActivity extends AppCompatActivity {

    String openTutorialPref = "openTutorial";
    SharedPreferences mPref;
    FragmentPagerAdapter pagerAdapter;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        mPref = PreferenceManager.getDefaultSharedPreferences(this);

        // View Pager Adapter
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pager = findViewById(R.id.viewPager);
        pager.setAdapter(pagerAdapter);

        findViewById(R.id.exitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sets open tutorial flag to false on click
                SharedPreferences.Editor editor = mPref.edit();
                editor.putBoolean(openTutorialPref, false);
                editor.apply();
                Intent exitToMain = new Intent(TutorialActivity.this,MainActivity.class);
                startActivity(exitToMain);
            }
        });

    }
}
