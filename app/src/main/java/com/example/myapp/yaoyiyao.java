package com.example.myapp;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class yaoyiyao extends AppCompatActivity {

    private SensorManager sensorManager;
    private Vibrator vibrator;
    private TextView textView;
    private ImageView imageView;
    private static String strs[]={"石头","剪刀","布"};
    private static int pics[]={R.mipmap.p1,R.mipmap.p2,R.mipmap.p3};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yaoyiyao);
        textView=findViewById(R.id.txttable);
        imageView=findViewById(R.id.imageView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);


    }
}
