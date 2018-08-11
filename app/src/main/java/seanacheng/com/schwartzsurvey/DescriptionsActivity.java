package seanacheng.com.schwartzsurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DescriptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descriptions);
        TableLayout descriptionsTable = findViewById(R.id.descriptionsTable);
        String[] descriptions = getResources().getStringArray(R.array.value_groupings);

        int i = 1;
        for (String desc:descriptions) {
            String numbered = i+") "+desc;
            i++;

            TextView description = new TextView(this);
            description.setText(numbered);
            description.setTextColor(getResources().getColor(R.color.darkgray));
            description.setPadding(0,0,0,10);

            descriptionsTable.addView(description);
        }

    }
}
