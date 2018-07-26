package seanacheng.com.schwartzsurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DisclaimerPopUp extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer_pop_up);

        findViewById(R.id.acceptButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.acceptButton:
                Intent accept = new Intent(DisclaimerPopUp.this,MainActivity.class);
                startActivity(accept);
                break;
        }
    }
}
