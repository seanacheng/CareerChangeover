package seanacheng.com.schwartzsurvey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

public class IntroTutorialActivity extends FragmentActivity {
    SharedPreferences mPref;
    String tutorialViewedPref = "tutorialViewed";
    FragmentPagerAdapter pagerAdapter;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_tutorial);

        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean skipTutorial = mPref.getBoolean(tutorialViewedPref,false);
        final Intent exitToMain = new Intent(IntroTutorialActivity.this,MainActivity.class);

        if (!skipTutorial) {
            startActivity(exitToMain);
        } else {
            pagerAdapter = new PagerAdapter(getSupportFragmentManager());
            pager = findViewById(R.id.viewPager);
            pager.setAdapter(pagerAdapter);
        }

        findViewById(R.id.exitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = mPref.edit();
                editor.putBoolean(tutorialViewedPref, true);
                editor.apply();
                startActivity(exitToMain);
            }
        });
    }
}
