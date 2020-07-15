package careerchangeover.com.valuesvisualizer;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView image = findViewById(R.id.splashGifImageView);
        image.setMinimumHeight(getScreenResolution(this).get("height")/4);
        image.setMinimumWidth(getScreenResolution(this).get("height")/4);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent quitSplashScreen = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(quitSplashScreen);
                finish();
            }
        },SPLASH_TIME_OUT);
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
