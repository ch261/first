package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG ="Main";
    TextView out;
    EditText inp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        out = findViewById(R.id.out);

        inp = findViewById(R.id.inp);
        String  str =inp.getText().toString();
        inp.setText("Please input");

        Button btn =findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String  str1 =inp.getText().toString();
        float r =Integer.parseInt(str1);
        float a=r*1.8f+32;
        out.setText("result:"+String.format("%.2f",a));

    }
    
    public void abc(View b){
        Log.i(TAG, "abc: 1242");
    }


}
