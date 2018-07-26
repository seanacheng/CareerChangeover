package seanacheng.com.schwartzsurvey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

public class DisclaimerPopUp extends AppCompatActivity implements View.OnClickListener {

    private CheckBox checkBox;
    SharedPreferences mPref;
    String disclaimerAcceptedPref = "disclaimerAccepted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer_pop_up);
        mPref = PreferenceManager.getDefaultSharedPreferences(this);

        checkBox = findViewById(R.id.disclaimerCheckBox);

        findViewById(R.id.acceptButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.acceptButton:
                if (checkBox.isChecked()) {
                    SharedPreferences.Editor editor = mPref.edit();
                    editor.putBoolean(disclaimerAcceptedPref, true);
                    editor.apply();
                }
                Intent accept = new Intent(DisclaimerPopUp.this,MainActivity.class);
                startActivity(accept);
                break;
        }
    }

}
