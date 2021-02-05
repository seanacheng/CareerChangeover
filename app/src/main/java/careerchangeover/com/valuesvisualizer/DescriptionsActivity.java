package careerchangeover.com.valuesvisualizer;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class DescriptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descriptions);
        TableLayout descriptionsTable = findViewById(R.id.descriptionsGrid);
        String[] descriptions = getResources().getStringArray(R.array.value_groupings);

        for (int i=0;i<descriptions.length;i=i+2) {
            LinearLayout horizontalRow = new LinearLayout(this);
            horizontalRow.setOrientation(LinearLayout.HORIZONTAL);

            TextView name = new TextView(this);
            name.setText(descriptions[i]);
            name.setTextColor(getResources().getColor(R.color.navy));
            name.setWidth(getScreenResolution(this).get("width")*3/10);
            name.setBackground(getResources().getDrawable(R.drawable.cell_border));
            horizontalRow.addView(name);

            TextView description = new TextView(this);
            description.setText(descriptions[i+1]);
            description.setTextColor(getResources().getColor(R.color.navy));
            description.setWidth(getScreenResolution(this).get("width")*13/20);
            description.setBackground(getResources().getDrawable(R.drawable.cell_border));
            horizontalRow.addView(description);
            descriptionsTable.addView(horizontalRow);

            description.measure(0,0);
            int rowHeight = description.getMeasuredHeight();
            name.setHeight(rowHeight);

        }

    }

    private static Map<String, Integer> getScreenResolution(Context context) {
        // Returns map of width and height of screen
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        int density = metrics.densityDpi;

        HashMap<String, Integer> map = new HashMap<>();
        map.put("width",width);
        map.put("height",height);
        map.put("density",density);
        return map;
    }
}
